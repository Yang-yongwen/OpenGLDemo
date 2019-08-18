package com.yangyongwen.chapter5

import android.opengl.GLSurfaceView
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter5.*

class Chapter5Activity : BaseGLSurfaceActivity() {

    override fun getGlSurfaceView(): GLSurfaceView {
        return gl_surface
    }

    override fun getRenderer(): GLSurfaceView.Renderer {
        return AirHockeyRenderer(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_chapter5
    }
}
