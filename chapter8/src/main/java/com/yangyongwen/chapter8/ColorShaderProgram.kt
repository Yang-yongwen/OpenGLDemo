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

class ColorShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.simple_vertex_shader_chapter8, R.raw.simple_fragment_shader_chapter8) {

    private val uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)
    private val uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR)

    val aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)

    fun setUniforms(matrix: FloatArray, r: Float, g: Float, b: Float) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        GLES20.glUniform4f(uColorLocation, r, g, b, 1f)
    }

}