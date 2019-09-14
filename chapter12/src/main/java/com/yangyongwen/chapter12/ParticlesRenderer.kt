package com.yangyongwen.chapter12

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.support.v4.content.ContextCompat
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
    private val modelMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val viewMatrixForSkyBox = FloatArray(16)
    private val modelViewProjectionMatrix = FloatArray(16)
    private val tempMatrix = FloatArray(16)
    private var particleTextureId = 0

    private lateinit var particleProgram: ParticleShaderProgram
    private lateinit var skyboxProgram: SkyboxShaderProgram
    private lateinit var heightMapProgram: HeightMapShaderProgram
    private lateinit var particleSystem: ParticleSystem
    private lateinit var redParticleShooter: ParticleShooter
    private lateinit var greenParticleShooter: ParticleShooter
    private lateinit var blueParticleShooter: ParticleShooter
    private lateinit var skyBox: SkyBox
    private lateinit var heightMap: HeightMap
    private var skyboxTextureId = 0
    private var globalStartTime = 0L
    private val angleVarianceInDegrees = 5f
    private val speedVariance = 1f
    private var xRotation = 0f
    private var yRotation = 0f

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        GLES20.glEnable(GLES20.GL_CULL_FACE)
        particleTextureId = TextureHelper.loadTexture(context, R.drawable.particle_texture)

        heightMapProgram = HeightMapShaderProgram(context)
        heightMap = HeightMap((ContextCompat.getDrawable(context, R.drawable.heightmap) as BitmapDrawable).bitmap)

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
        updateViewMatrices()
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        drawHeightMap()
        drawSkybox()
        drawParticles()
    }

    private fun drawSkybox() {
        GLES20.glDepthFunc(GLES20.GL_LEQUAL)
        Matrix.setIdentityM(modelMatrix, 0)
        updateMvpMatrixForSkyBox()
        skyboxProgram.userProgram()
        skyboxProgram.setUniforms(modelViewProjectionMatrix, skyboxTextureId)
        skyBox.bindData(skyboxProgram)
        skyBox.draw()
        GLES20.glDepthFunc(GLES20.GL_LESS)
    }

    private fun drawParticles() {
        GLES20.glDepthMask(false)
        val currentTime = (System.nanoTime() - globalStartTime) / 1000000000f

        redParticleShooter.addParticles(particleSystem, currentTime, 5)
        greenParticleShooter.addParticles(particleSystem, currentTime, 5)
        blueParticleShooter.addParticles(particleSystem, currentTime, 5)

        Matrix.setIdentityM(modelMatrix, 0)
        updateMvpMatrix()

        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE)

        particleProgram.userProgram()
        particleProgram.setUniforms(modelViewProjectionMatrix, currentTime, particleTextureId)
        particleSystem.bindData(particleProgram)
        particleSystem.draw()

        GLES20.glDisable(GLES20.GL_BLEND)
        GLES20.glDepthMask(true)
    }

    private fun drawHeightMap() {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.scaleM(modelMatrix, 0, 100f, 10f, 100f)
        updateMvpMatrix()
        heightMapProgram.userProgram()
        heightMapProgram.setUniforms(modelViewProjectionMatrix)
        heightMap.bindData(heightMapProgram)
        heightMap.draw()
    }

    fun handleTouchDrag(deltaX: Float, deltaY: Float) {
        xRotation += deltaX / 16f
        yRotation += deltaY / 16f

        when {
            yRotation < -90f -> yRotation = -90f
            yRotation > 90f -> yRotation = 90f
        }
        updateViewMatrices()
    }

    private fun updateViewMatrices() {
        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.rotateM(viewMatrix, 0, -yRotation, 1f, 0f, 0f)
        Matrix.rotateM(viewMatrix, 0, -xRotation, 0f, 1f, 0f)

        System.arraycopy(viewMatrix, 0, viewMatrixForSkyBox, 0, viewMatrix.size)
        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f)
    }

    private fun updateMvpMatrix() {
        Matrix.multiplyMM(tempMatrix, 0, viewMatrix, 0, modelMatrix, 0)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, tempMatrix, 0)
    }

    private fun updateMvpMatrixForSkyBox() {
        Matrix.multiplyMM(tempMatrix, 0, viewMatrixForSkyBox, 0, modelMatrix, 0)
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, projectionMatrix, 0, tempMatrix, 0)
    }

}