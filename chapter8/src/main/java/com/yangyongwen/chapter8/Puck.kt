package com.yangyongwen.chapter8

import com.yangyongwen.common.data.VertexArray
import com.yangyongwen.common.utils.Cylinder
import com.yangyongwen.common.utils.DrawCommand
import com.yangyongwen.common.utils.ObjectBuilder
import com.yangyongwen.common.utils.Point

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/25
 */
class Puck(radius: Float,val height: Float, numPointsAroundPuck: Int) {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    private val vertexArray: VertexArray
    private val drawList = mutableListOf<DrawCommand>()

    init {
        val generateData = ObjectBuilder.createPuck(Cylinder(Point(0f, 0f, 0f), radius, height), numPointsAroundPuck)
        vertexArray = VertexArray(generateData.vertexData)
        drawList.addAll(generateData.drawList)
    }

    fun bindData(colorShaderProgram: ColorShaderProgram) {
        vertexArray.setVertexAttributePointer(0, colorShaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT, 0)
    }

    fun draw() {
        drawList.forEach {
            it.draw()
        }
    }

}