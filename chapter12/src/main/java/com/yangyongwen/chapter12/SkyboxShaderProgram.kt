package com.yangyongwen.chapter12

import android.content.Context
import android.opengl.GLES20
import com.yangyongwen.common.programs.ShaderProgram

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/09/14
 */

class SkyboxShaderProgram(context: Context) : ShaderProgram(context, R.raw.skybox_vertex_shader, R.raw.skybox_fragment_shader) {

    private val uMatrixLocation = glGetUniformLocation(U_MATRIX)
    private val uTextureUnitLocation = glGetUniformLocation(U_TEXTURE_UNIT)

    val aPositionLocation = glGetAttribLocation(A_POSITION)

    fun setUniforms(matrix: FloatArray, textureId: Int) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId)
        GLES20.glUniform1i(uTextureUnitLocation, 0)
    }

}