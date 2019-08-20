package com.yangyongwen.chapter7

import android.opengl.GLES20
import com.yangyongwen.common.data.Constants
import com.yangyongwen.common.data.VertexArray

/**
 * Description:
 * @author YangYongwen
 * Created on 2019-08-20
 */
class Mallet {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 2
        private const val COLOR_COMPONENT_COUNT = 3
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT
    }

    private val vertexData = floatArrayOf(
        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    private val vertexArray = VertexArray(vertexData)

    fun bindData(colorProgram: ColorShaderProgram) {
        vertexArray.setVertexAttributePointer(0, colorProgram.aPositionLocation, POSITION_COMPONENT_COUNT, STRIDE)
        vertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT,
            colorProgram.aColorLocation,
            COLOR_COMPONENT_COUNT,
            STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 2)
    }

}