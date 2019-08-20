package com.yangyongwen.chapter7

import android.opengl.GLES20
import com.yangyongwen.common.data.Constants
import com.yangyongwen.common.data.VertexArray

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/20
 */
class Table {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 2
        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2
        private const val STRIDE =
            (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT
    }

    private val vertexData = floatArrayOf(
        0f, 0f, 0.5f, 0.5f,
        -0.5f, -0.8f, 0f, 0.9f,
        0.5f, -0.8f, 1f, 0.9f,
        0.5f, 0.8f, 1f, 0.1f,
        -0.5f, 0.8f, 0f, 0.1f,
        -0.5f, -0.8f, 0f, 0.9f
    )

    private val vertexArray = VertexArray(vertexData)

    fun bindData(textureProgram: TextureShaderProgram) {
        vertexArray.setVertexAttributePointer(0, textureProgram.aPositionLocation, POSITION_COMPONENT_COUNT, STRIDE)
        vertexArray.setVertexAttributePointer(
            POSITION_COMPONENT_COUNT, textureProgram.aTextureCoordinateLocation,
            TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE
        )
    }

    fun draw() {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)
    }

}