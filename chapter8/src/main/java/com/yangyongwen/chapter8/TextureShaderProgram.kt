package com.yangyongwen.chapter8

import android.content.Context
import android.opengl.GLES20
import com.yangyongwen.common.programs.ShaderProgram

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/20
 */
class TextureShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.texture_vertex_shader_chapter8, R.raw.texture_fragment_shader_chapter8) {

    private val uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)
    private val uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT)

    val aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
    val aTextureCoordinateLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES)

    fun setUniforms(matrix: FloatArray, textureId: Int) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glUniform1i(uTextureUnitLocation, 0)
    }

}