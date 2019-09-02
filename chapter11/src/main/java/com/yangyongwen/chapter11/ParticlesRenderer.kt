package com.yangyongwen.chapter11

import android.content.Context
import android.graphics.Color
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.yangyongwen.common.utils.Point
import com.yangyongwen.common.utils.TextureHelper
import com.yangyongwen.common.utils.Vector
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/26
 */
class ParticlesRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val viewProjectionMatrix = FloatArray(16)
    private var particleTextureId = 0

    private lateinit var particleProgram: ParticleShaderProgram
    private lateinit var skyboxProgram: SkyboxShaderProgram
    private lateinit var particleSystem: ParticleSystem
    private lateinit var redParticleShooter: ParticleShooter
    private lateinit var greenParticleShooter: ParticleShooter
    private lateinit var blueParticleShooter: ParticleShooter
    private lateinit var skyBox: SkyBox
    private var skyboxTextureId = 0
    private var globalStartTime = 0L
    private val angleVarianceInDegrees = 5f
    private val speedVariance = 1f
    private var xRotation = 0f
    private var yRotation = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        particleTextureId = TextureHelper.loadTexture(context, R.drawable.particle_texture)

        skyBox = SkyBox()
        skyboxProgram = SkyboxShaderProgram(context)
        skyboxTextureId = TextureHelper.loadCubeMap(context, intArrayOf(
            R.drawable.left, R.drawable.right,
            R.drawable.bottom, R.drawable.top,
            R.drawable.front, R.drawable.back
        ))

        particleProgram = ParticleShaderProgram(context = context)
        particleSystem = ParticleSystem(10000)
        globalStartTime = System.nanoTime()

        val particleDirection = Vector(0f, 0.5f, 0f)

        redParticleShooter = ParticleShooter(
            Point(-1f, 0f, 0f),
            particleDirection,
            Color.rgb(255, 50, 5),
            angleVarianceInDegrees,
            speedVariance
        )

        greenParticleShooter = ParticleShooter(
            Point(0f, 0f, 0f),
            particleDirection,
            Color.rgb(25, 255, 25),
            angleVarianceInDegrees,
            speedVariance
        )

        blueParticleShooter = ParticleShooter(
            Point(1f, 0f, 0f),
            particleDirection,
            Color.rgb(5, 50, 255),
            angleVarianceInDegrees,
            speedVariance
        )

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        Matrix.perspectiveM(projectionMatrix, 0, 45f, width.toFloat() / height.toFloat(), 1f, 10f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        drawSkybox()
        drawParticles()
    }

    private fun drawSkybox() {
        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f)
        Matrix.rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f)
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        skyboxProgram.userProgram()
        skyboxProgram.setUniforms(viewProjectionMatrix, skyboxTextureId)
        skyBox.bindData(skyboxProgram)
        skyBox.draw()
    }

    private fun drawParticles() {
        val currentTime = (System.nanoTime() - globalStartTime) / 1000000000f

        redParticleShooter.addParticles(particleSystem, currentTime, 5)
        greenParticleShooter.addParticles(particleSystem, currentTime, 5)
        blueParticleShooter.addParticles(particleSystem, currentTime, 5)

        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f)
        Matrix.rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f)
        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f)
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE)

        particleProgram.userProgram()
        particleProgram.setUniforms(viewProjectionMatrix, currentTime, particleTextureId)
        particleSystem.bindData(particleProgram)
        particleSystem.draw()

        GLES20.glDisable(GLES20.GL_BLEND)
    }

    fun handleTouchDrag(deltaX: Float, deltaY: Float) {
        xRotation += deltaX / 16f
        yRotation += deltaY / 16f

        when {
            yRotation < -90f -> yRotation = -90f
            yRotation > 90f -> yRotation = 90f
        }
    }

}