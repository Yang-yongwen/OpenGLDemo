package com.yangyongwen.common.utils

import android.opengl.GLES20
import android.util.Log

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/17
 */
object ShaderHelper {

    private const val TAG = "ShaderHelper"

    fun compileVertexShader(shaderCode: String) = compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)

    fun compileFragmentShader(shaderCode: String) = compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)

    private fun compileShader(type: Int, shaderCode: String): Int {
        val shaderObjectId = GLES20.glCreateShader(type)
        if (shaderObjectId == 0) {
            return 0
        }
        GLES20.glShaderSource(shaderObjectId, shaderCode)
        GLES20.glCompileShader(shaderObjectId)
        val compileStatus = intArrayOf(0)
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            Log.e(TAG, "result of compiling source code: \n " +
                    "$shaderCode \n " +
                    "status: ${compileStatus[0]}" +
                    "log:  ${GLES20.glGetShaderInfoLog(shaderObjectId)}")
            GLES20.glDeleteShader(shaderObjectId)
            return 0
        }
        return shaderObjectId
    }

    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programObjectId = GLES20.glCreateProgram()
        if (programObjectId == 0) {
            Log.e(TAG, "glCreateProgram failed")
            return 0
        }
        GLES20.glAttachShader(programObjectId, vertexShaderId)
        GLES20.glAttachShader(programObjectId, fragmentShaderId)

        GLES20.glLinkProgram(programObjectId)
        val linkStatus = intArrayOf(0)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            Log.e(TAG, "link program failed")
            GLES20.glDeleteProgram(programObjectId)
            return 0
        }
        validateProgram(programObjectId)
        return programObjectId
    }

    private fun validateProgram(programObjectId: Int): Boolean {
        GLES20.glValidateProgram(programObjectId)

        val validateStatus = intArrayOf(0)
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)
        Log.i(TAG, "result of validate program: ${validateStatus[0]}\n log: ${GLES20.glGetProgramInfoLog(programObjectId)}")
        return validateStatus[0] != 0
    }

}