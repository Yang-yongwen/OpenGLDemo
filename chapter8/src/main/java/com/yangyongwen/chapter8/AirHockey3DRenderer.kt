package com.yangyongwen.chapter8

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

    private val modelMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val modelViewProjectionMatrix = FloatArray(16)
    private val viewProjectionMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)

    private var texture = 0
    private var table: Table? = null
    private var mallet: Mallet? = null
    private var puck: Puck? = null
    private var textureProgram: TextureShaderProgram? = null
    private var colorProgram: ColorShaderProgram? = null

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        table = Table()
        mallet = Mallet(0.08f, 0.15f, 32)
        puck = Puck(0.06f, 0.02f, 32)
        textureProgram = TextureShaderProgram(context)
        colorProgram = ColorShaderProgram(context)
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        Matrix.perspectiveM(projectionMatrix, 0, 45f, width.toFloat() / height.toFloat(), 1f, 10f)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        val table = table ?: return
        val mallet = mallet ?: return
        val puck = puck ?: return
        val textureProgram = textureProgram ?: return
        val colorProgram = colorProgram ?: return


        positionTableInScene()
        textureProgram.apply {
            userProgram()
            setUniforms(modelViewProjectionMatrix, texture)
            table.bindData(this)
            table.draw()
        }

        positionObjectInScene(0f, mallet.height / 2f, -0.4f)
        colorProgram.apply {
            userProgram()
            setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f)
            mallet.bindData(this)
            mallet.draw()
        }

        positionObjectInScene(0f, mallet.height / 2f, 0.4f)
        colorProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f)
        mallet.draw()


        positionObjectInScene(0f, puck.height / 2f, 0f)
        colorProgram.apply {
            setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f)
            puck.bindData(this)
            puck.draw()
        }
    }

    private fun positionTableInScene() {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0)
    }

    private fun positionObjectInScene(x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, x, y, z)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0)
    }

}