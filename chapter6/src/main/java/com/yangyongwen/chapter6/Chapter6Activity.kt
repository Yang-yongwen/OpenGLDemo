package com.yangyongwen.chapter6

import android.opengl.GLSurfaceView
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter6.*

class Chapter6Activity : BaseGLSurfaceActivity() {

    override fun getGlSurfaceView(): GLSurfaceView {
        return gl_surface
    }

    override fun getRenderer(): GLSurfaceView.Renderer {
        return AirHockey3DRenderer(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_chapter6
    }
}
