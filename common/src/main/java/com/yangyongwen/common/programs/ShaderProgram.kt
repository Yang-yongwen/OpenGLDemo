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
class ShaderProgram(context: Context, vertexShaderResId: Int, fragmentShaderResId: Int) {

    companion object {
        // Uniform constants
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"

        // Attribute constants
        const val A_POSITION = "a_Position"
        const val A_COLOR = "a_Color"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    }

    private val program = ShaderHelper.buildProgram(
        ResourceUtils.readTextFromResource(context, vertexShaderResId),
        ResourceUtils.readTextFromResource(context, fragmentShaderResId)
    )

    fun userProgram() {
        GLES20.glUseProgram(program)
    }

}