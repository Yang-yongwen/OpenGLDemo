package com.yangyongwen.chapter12

import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.GLES20
import com.yangyongwen.common.data.IndexBuffer
import com.yangyongwen.common.data.VertexBuffer

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/09/14
 */
class HeightMap(bitmap: Bitmap) {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    private val width = bitmap.width
    private val height = bitmap.height
    private val numElements = calculateElements()
    private val vertexBuffer = VertexBuffer(loadBitmapData(bitmap))
    private val indexBuffer = IndexBuffer(createIndexBuffer())

    init {
        check(width * height <= 65536) { "HeightMap is too large for index buffer" }
    }

    private fun calculateElements() = (width - 1) * (height - 1) * 2 * 3

    private fun loadBitmapData(bitmap: Bitmap): FloatArray {
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        bitmap.recycle()

        return FloatArray(width * height * POSITION_COMPONENT_COUNT).apply {
            var offset = 0
            for (row in 0 until height) {
                for (col in 0 until width) {
                    val xPos = col.toFloat() / (width - 1).toFloat() - 0.5f
                    val yPos = Color.red(pixels[row * height + col]).toFloat() / 255f
                    val zPos = row.toFloat() / (height - 1).toFloat() - 0.5f
                    this[offset++] = xPos
                    this[offset++] = yPos
                    this[offset++] = zPos
                }
            }
        }
    }

    private fun createIndexBuffer(): ShortArray {
        val indexData = ShortArray(numElements)
        var offset = 0
        for (row in 0 until height - 1) {
            for (col in 0 until width - 1) {
                val topLeftIndexNum = (row * width + col).toShort()
                val topRightIndexNum = (row * width + col + 1).toShort()
                val bottomLeftIndexNum = ((row + 1) * width + col).toShort()
                val bottomRightIndexNum = ((row + 1) * width + col + 1).toShort()

                indexData[offset++] = topLeftIndexNum
                indexData[offset++] = bottomLeftIndexNum
                indexData[offset++] = topRightIndexNum

                indexData[offset++] = topRightIndexNum
                indexData[offset++] = bottomLeftIndexNum
                indexData[offset++] = bottomRightIndexNum
            }
        }
        return indexData
    }

    fun bindData(program: HeightMapShaderProgram) {
        vertexBuffer.setVertexAtrribPointer(0, program.aPositionLocation, POSITION_COMPONENT_COUNT, 0)
    }

    fun draw() {
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer.bufferId)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, 0)
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
    }

}