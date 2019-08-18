package com.yangyongwen.chapter6

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.yangyongwen.common.utils.ResourceUtils
import com.yangyongwen.common.utils.ShaderHelper
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/18
 */
class AirHockey3DRenderer(private val context: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val BYTES_PER_FLOAT = 4
        private const val POSITION_COMPONENT_COUNT = 2
        private const val COLOR_COMPONENT_COUNT = 3
        private const val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT


        private const val A_POSITION = "a_Position"
        private const val A_COLOR = "a_Color"
        private const val U_MATRIX = "u_Matrix"
    }

    private val tableVerticesWithTriangles = floatArrayOf(

        // order of coordinates : x,y,r,g,b

        0f, 0f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,

        0f, -0.4f, 0f, 0f, 1f,
        0f, 0.4f, 1f, 0f, 0f
    )

    private val vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
        .put(tableVerticesWithTriangles)

    private val projectionMatrix = FloatArray(16)


    private var aPositionLocation = 0
    private var aColorLocation = 0
    private var uMatrixLocation = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        val vertexShader =
            ShaderHelper.compileVertexShader(
                ResourceUtils.readTextFromResource(
                    context,
                    R.raw.simple_vertex_shader_chapter6
                )
            )
        val fragmentShader = ShaderHelper.compileFragmentShader(
            ResourceUtils.readTextFromResource(
                context,
                R.raw.simple_fragment_shader_chapter6
            )
        )
        val program = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        GLES20.glUseProgram(program)

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR)
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)

        vertexData.position(0)
        GLES20.glVertexAttribPointer(
            aPositionLocation,
            POSITION_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertexData
        )
        GLES20.glEnableVertexAttribArray(aPositionLocation)

        vertexData.position(POSITION_COMPONENT_COUNT)
        GLES20.glVertexAttribPointer(
            aColorLocation,
            COLOR_COMPONENT_COUNT,
            GLES20.GL_FLOAT,
            false,
            STRIDE,
            vertexData
        )
        GLES20.glEnableVertexAttribArray(aColorLocation)
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
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)

        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)

        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)
    }
}