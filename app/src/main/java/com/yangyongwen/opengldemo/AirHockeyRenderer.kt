package com.yangyongwen.opengldemo

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.yangyongwen.opengldemo.utils.ResourceUtils
import com.yangyongwen.opengldemo.utils.ShaderHelper
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/17
 */
class AirHockeyRenderer(private val context: Context) : GLSurfaceView.Renderer {

    companion object {
        private const val BYTES_PER_FLOAT = 4
        private const val POSITION_COMPONENT_COUNT = 2
        private const val U_COLOR = "u_Color"
        private const val A_POSITION = "a_Position"
    }

    private val tableVerticesWithTriangles = floatArrayOf(
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            -0.5f, 0f,
            0.5f, 0f,

            0f, -0.25f,
            0f, 0.25f
    )

    private val vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(tableVerticesWithTriangles)

    private var uColorLocation = 0
    private var aPositionLocation = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 0f)
        val vertexShader = ShaderHelper.compileVertexShader(ResourceUtils.readTextFromResource(context, R.raw.simple_vertex_shader))
        val fragmentShader = ShaderHelper.compileFragmentShader(ResourceUtils.readTextFromResource(context, R.raw.simple_fragment_shader))
        val program = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        GLES20.glUseProgram(program)

        uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
        vertexData.position(0)
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData)
        GLES20.glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glUniform4f(uColorLocation, 1f, 1f, 1f, 1f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6)

        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)

        GLES20.glUniform4f(uColorLocation, 0f, 0f, 1f, 1f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

        GLES20.glUniform4f(uColorLocation, 1f, 0f, 0f, 1f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)
    }

}