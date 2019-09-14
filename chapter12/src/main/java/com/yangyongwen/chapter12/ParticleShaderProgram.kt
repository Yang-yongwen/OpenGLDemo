package com.yangyongwen.chapter12

import android.content.Context
import android.opengl.GLES20
import com.yangyongwen.common.programs.ShaderProgram

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/27
 */
class ParticleShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader) {

    private val uMatrixLocation = glGetUniformLocation(U_MATRIX)
    private val uTimeLocation = glGetUniformLocation(U_TIME)
    private val uTextureLocation = glGetUniformLocation(U_TEXTURE_UNIT)

    val aPositionLocation = glGetAttribLocation(A_POSITION)
    val aColorLocation = glGetAttribLocation(A_COLOR)
    val aDirectionVectorLocation = glGetAttribLocation(A_DIRECTION_VECTOR)
    val aParticlesStartTimeLocation = glGetAttribLocation(A_PARTICLE_START_TIME)

    fun setUniforms(matrix: FloatArray, elapsedTime: Float, textureId: Int) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        GLES20.glUniform1f(uTimeLocation, elapsedTime)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glUniform1i(uTextureLocation, 0)
    }

}