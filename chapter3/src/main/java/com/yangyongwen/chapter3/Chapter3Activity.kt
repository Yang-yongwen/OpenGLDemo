package com.yangyongwen.chapter3

import android.opengl.GLSurfaceView
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter3.*

class Chapter3Activity : BaseGLSurfaceActivity() {

    override fun getGlSurfaceView(): GLSurfaceView = gl_surface

    override fun getRenderer() = AirHockeyRenderer(this)

    override fun getLayoutRes() = R.layout.activity_chapter3
}
