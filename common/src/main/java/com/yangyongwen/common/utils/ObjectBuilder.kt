package com.yangyongwen.common.utils

import android.opengl.GLES20
import kotlin.math.cos
import kotlin.math.sin

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/23
 */
@Suppress("NOTHING_TO_INLINE")
class ObjectBuilder(sizeInVertices: Int) {

    companion object {
        private const val FLOAT_PER_VERTEX = 3

        private fun sizeOfCircleInVertices(numPoints: Int) = numPoints + 2

        private fun sizeOfOpenCylinderInVertices(numPoints: Int) = (numPoints + 1) * 2

        fun createPuck(puck: Cylinder, numPoints: Int): GenerateData {
            val size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints)
            val builder = ObjectBuilder(size)
            val puckTop = Circle(puck.center.translateY(puck.height / 2), puck.radius)
            builder.appendCircle(puckTop, numPoints)
            builder.appendOpenCylinder(puck, numPoints)
            return builder.build()
        }

        fun createMallet(center: Point, radius: Float, height: Float, numPoints: Int): GenerateData {
            val size = sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2

            val builder = ObjectBuilder(size)
            val baseHeight = height / 4f
            val baseCircle = Circle(center.translateY(-baseHeight), radius)
            val baseCylinder = Cylinder(baseCircle.center.translateY(-baseHeight / 2f), radius, baseHeight)
            builder.appendCircle(baseCircle, numPoints)
            builder.appendOpenCylinder(baseCylinder, numPoints)


            val handleHeight = height * 0.75f
            val handleRadius = radius / 3f
            val handleCircle = Circle(center.translateY(height * 0.5f), handleRadius)
            val handleCylinder = Cylinder(handleCircle.center.translateY(-handleHeight / 2f), handleRadius, handleHeight)
            builder.appendCircle(handleCircle, numPoints)
            builder.appendOpenCylinder(handleCylinder, numPoints)

            return builder.build()
        }

    }

    private val vertexData = FloatArray(sizeInVertices * FLOAT_PER_VERTEX)
    private var offset = 0
    private val drawCommands = mutableListOf<DrawCommand>()

    fun appendCircle(circle: Circle, numPoints: Int) {
        val startVertex = offset / FLOAT_PER_VERTEX
        val numVertices = sizeOfCircleInVertices(numPoints)
        putData(circle.center.x)
        putData(circle.center.y)
        putData(circle.center.z)

        for (i in 0..numPoints) {
            val angleInRadians = i.toFloat() * Math.PI * 2f / numPoints.toFloat()
            putData(circle.center.x + circle.radius * cos(angleInRadians).toFloat())
            putData(circle.center.y)
            putData(circle.center.z + circle.radius * sin(angleInRadians).toFloat())
        }
        drawCommands.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, startVertex, numVertices)
            }
        })
    }

    fun appendOpenCylinder(cylinder: Cylinder, numPoints: Int) {
        val startVertex = offset / FLOAT_PER_VERTEX
        val numVertices = sizeOfOpenCylinderInVertices(numPoints)

        val yStart = cylinder.center.y - cylinder.height / 2
        val yEnd = cylinder.center.y + cylinder.height / 2

        for (i in 0..numPoints) {
            val angleInRadians = i.toFloat() * Math.PI * 2f / numPoints.toFloat()
            val xPosition = cylinder.center.x + cylinder.radius * cos(angleInRadians).toFloat()
            val zPosition = cylinder.center.y + cylinder.radius * sin(angleInRadians).toFloat()

            putData(xPosition)
            putData(yStart)
            putData(zPosition)

            putData(xPosition)
            putData(yEnd)
            putData(zPosition)
        }

        drawCommands.add(object : DrawCommand {
            override fun draw() {
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, startVertex, numVertices)
            }
        })
    }

    fun build(): GenerateData {
        return GenerateData(vertexData, drawCommands)
    }

    private inline fun putData(data: Float) {
        vertexData[offset++] = data
    }

}

interface DrawCommand {
    fun draw()
}

data class GenerateData(val vertexData: FloatArray, val drawList: List<DrawCommand>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenerateData

        if (!vertexData.contentEquals(other.vertexData)) return false
        if (drawList != other.drawList) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vertexData.contentHashCode()
        result = 31 * result + drawList.hashCode()
        return result
    }

}
