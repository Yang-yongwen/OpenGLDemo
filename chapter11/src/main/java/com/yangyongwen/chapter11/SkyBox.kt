package com.yangyongwen.chapter11

import android.opengl.GLES20
import com.yangyongwen.common.data.VertexArray
import java.nio.ByteBuffer

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/09/04
 */
class SkyBox {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    private val vertexArray = VertexArray(floatArrayOf(
        -1f, 1f, 1f,
        1f, 1f, 1f,
        -1f, -1f, 1f,
        1f, -1f, 1f,
        -1f, 1f, -1f,
        1f, 1f, -1f,
        -1f, -1f, -1f,
        1f, -1f, -1f
    ))

    private val indexArray = ByteBuffer.allocateDirect(36).apply {
        put(byteArrayOf(
            1, 3, 0, 0, 3, 2,
            4, 6, 5, 5, 6, 7,
            0, 2, 4, 4, 2, 6,
            5, 7, 1, 1, 7, 3,
            5, 1, 4, 4, 1, 0,
            6, 2, 7, 7, 2, 3
        ))
        position(0)
    }

    fun bindData(program: SkyboxShaderProgram) {
        vertexArray.setVertexAttributePointer(0, program.aPositionLocation, POSITION_COMPONENT_COUNT, 0)
    }

    fun draw() {
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_BYTE, indexArray)
    }

}