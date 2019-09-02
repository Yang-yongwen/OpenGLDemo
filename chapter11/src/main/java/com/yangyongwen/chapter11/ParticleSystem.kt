package com.yangyongwen.chapter11

import android.graphics.Color
import android.opengl.GLES20
import com.yangyongwen.common.data.Constants
import com.yangyongwen.common.data.VertexArray
import com.yangyongwen.common.utils.Point
import com.yangyongwen.common.utils.Vector

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/27
 */

@Suppress("NOTHING_TO_INLINE")
class ParticleSystem(private val maxParticleCount: Int) {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
        private const val COLOR_COMPONENT_COUNT = 3
        private const val VECTOR_COMPONENT_COUNT = 3
        private const val PARTICLE_START_TIME_COMPONENT_COUNT = 1

        private const val TOTAL_COMPONENT_TIME =
            POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT + VECTOR_COMPONENT_COUNT + PARTICLE_START_TIME_COMPONENT_COUNT

        private const val STRIDE = TOTAL_COMPONENT_TIME * Constants.BYTES_PER_FLOAT
    }

    private val particles = FloatArray(maxParticleCount * TOTAL_COMPONENT_TIME)
    private var currentOffset = 0
    private val vertexArray = VertexArray(particles)
    private var currentParticleCount = 0
    private var nextParticle = 0

    fun addParticle(position: Point, color: Int, direction: Vector, startTime: Float) {
        val particleOffset = nextParticle++ * TOTAL_COMPONENT_TIME
        currentOffset = particleOffset
        if (nextParticle == maxParticleCount) {
            nextParticle = 0
        }
        if (currentParticleCount < maxParticleCount) {
            currentParticleCount++
        }

        putData(position.x)
        putData(position.y)
        putData(position.z)

        putData(Color.red(color) / 255f)
        putData(Color.green(color) / 255f)
        putData(Color.blue(color) / 255f)

        putData(direction.x)
        putData(direction.y)
        putData(direction.z)

        putData(startTime)

        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_TIME)
    }

    private inline fun putData(data: Float) {
        particles[currentOffset++] = data
    }

    fun bindData(program: ParticleShaderProgram) {
        var dataOffset = 0

        vertexArray.setVertexAttributePointer(
            dataOffset, program.aPositionLocation,
            POSITION_COMPONENT_COUNT, STRIDE
        )
        dataOffset += POSITION_COMPONENT_COUNT

        vertexArray.setVertexAttributePointer(
            dataOffset, program.aColorLocation,
            COLOR_COMPONENT_COUNT, STRIDE
        )
        dataOffset += COLOR_COMPONENT_COUNT

        vertexArray.setVertexAttributePointer(
            dataOffset, program.aDirectionVectorLocation,
            VECTOR_COMPONENT_COUNT, STRIDE
        )
        dataOffset += VECTOR_COMPONENT_COUNT

        vertexArray.setVertexAttributePointer(
            dataOffset, program.aParticlesStartTimeLocation,
            PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount)
    }



}