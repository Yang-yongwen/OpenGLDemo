package com.yangyongwen.common.ui

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yangyongwen.common.utils.OpenGLUtils

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/17
 */
abstract class BaseGLSurfaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!OpenGLUtils.supportGlEs2(this)) {
            finish()
            return
        }
        setContentView(getLayoutRes())
        initGL()
    }

    protected abstract fun getGlSurfaceView(): GLSurfaceView
    protected abstract fun getRenderer(): GLSurfaceView.Renderer
    protected abstract fun getLayoutRes(): Int

    private fun initGL() {
        getGlSurfaceView().setEGLContextClientVersion(2)
        getGlSurfaceView().setRenderer(getRenderer())
    }

    override fun onResume() {
        super.onResume()
        getGlSurfaceView().onResume()
    }

    override fun onPause() {
        super.onPause()
        getGlSurfaceView().onPause()
    }

}