package com.yangyongwen.chapter7

import android.content.Context
import android.opengl.GLES20
import com.yangyongwen.common.programs.ShaderProgram

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/20
 */

class ColorShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.simple_vertex_shader_chapter7, R.raw.simple_fragment_shader_chapter7) {

    private val uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)

    val aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
    val aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR)

    fun setUniforms(matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

}