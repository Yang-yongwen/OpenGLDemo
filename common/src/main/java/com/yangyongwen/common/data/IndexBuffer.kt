package com.yangyongwen.common.data

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/09/14
 */
class IndexBuffer(data: ShortArray) {

    val bufferId: Int

    init {
        val buffers = IntArray(1)
        GLES20.glGenBuffers(buffers.size, buffers, 0)
        check(buffers[0] != 0) { "Could not create a new vertex buffer object" }
        bufferId = buffers[0]

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferId)
        val vertexArray = ByteBuffer.allocateDirect(data.size * Constants.BYTES_PER_SHORT)
            .order(ByteOrder.nativeOrder())
            .asShortBuffer()
            .put(data)
        vertexArray.position(0)

        GLES20.glBufferData(
            GLES20.GL_ELEMENT_ARRAY_BUFFER,
            vertexArray.capacity() * Constants.BYTES_PER_SHORT,
            vertexArray,
            GLES20.GL_STATIC_DRAW
        )
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    fun setVertexAtrrPointer(dataOffset: Int, attributeLocation: Int, componentCount: Int, stride: Int) {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferId)
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_SHORT, false, stride, dataOffset)
        GLES20.glEnableVertexAttribArray(attributeLocation)
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

}