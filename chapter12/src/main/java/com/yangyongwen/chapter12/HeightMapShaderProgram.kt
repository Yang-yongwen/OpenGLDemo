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
class HeightMapShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.heightmap_vertext_shader, R.raw.heightmap_fragment_shader) {

    private val uMatrixLocation = glGetUniformLocation(U_MATRIX)

    val aPositionLocation = glGetAttribLocation(A_POSITION)

    fun setUniforms(matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

}