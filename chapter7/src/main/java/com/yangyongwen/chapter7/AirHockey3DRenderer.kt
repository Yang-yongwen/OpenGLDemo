package com.yangyongwen.chapter7

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.yangyongwen.common.utils.TextureHelper
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/18
 */
class AirHockey3DRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private val projectionMatrix = FloatArray(16)

    private var texture = 0
    private var table: Table? = null
    private var mallet: Mallet? = null
    private var textureProgram: TextureShaderProgram? = null
    private var colorProgram: ColorShaderProgram? = null

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        table = Table()
        mallet = Mallet()
        textureProgram = TextureShaderProgram(context)
        colorProgram = ColorShaderProgram(context)
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        val modelMatrix = FloatArray(16)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f)
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)

        Matrix.perspectiveM(projectionMatrix, 0, 45f, width.toFloat() / height.toFloat(), 1f, 10f)

        val tmp = FloatArray(16)
        Matrix.multiplyMM(tmp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(tmp, 0, projectionMatrix, 0, tmp.size)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        textureProgram?.apply {
            userProgram()
            setUniforms(projectionMatrix, texture)
            table?.bindData(this)
            table?.draw()
        }

        colorProgram?.apply {
            userProgram()
            setUniforms(projectionMatrix)
            mallet?.bindData(this)
            mallet?.draw()
        }
    }
}