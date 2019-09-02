package com.yangyongwen.common.programs

import android.content.Context
import android.opengl.GLES20
import com.yangyongwen.common.utils.ResourceUtils
import com.yangyongwen.common.utils.ShaderHelper

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/19
 */
@Suppress("NOTHING_TO_INLINE")
open class ShaderProgram(context: Context, vertexShaderResId: Int, fragmentShaderResId: Int) {

    companion object {
        // Uniform constants
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"
        const val U_COLOR = "u_Color"
        const val U_TIME = "u_Time"

        // Attribute constants
        const val A_POSITION = "a_Position"
        const val A_COLOR = "a_Color"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
        const val A_PARTICLE_START_TIME = "a_ParticlesStartTime"
        const val A_DIRECTION_VECTOR = "a_DirectionVector"
    }

    protected val program = ShaderHelper.buildProgram(
        ResourceUtils.readTextFromResource(context, vertexShaderResId),
        ResourceUtils.readTextFromResource(context, fragmentShaderResId)
    )

    protected inline fun glGetAttrLocation(name: String): Int {
        return GLES20.glGetAttribLocation(program, name)
    }

    protected inline fun glGetUniformLocation(name: String): Int {
        return GLES20.glGetUniformLocation(program, name)
    }

    fun userProgram() {
        GLES20.glUseProgram(program)
    }

}