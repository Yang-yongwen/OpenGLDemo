package com.yangyongwen.chapter10

import android.opengl.GLSurfaceView
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter10.*

class Chapter10Activity : BaseGLSurfaceActivity() {

    override fun getGlSurfaceView(): GLSurfaceView {
        return gl_surface
    }

    override fun getRenderer(): GLSurfaceView.Renderer {
        return ParticlesRenderer(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_chapter10
    }
}
