package com.yangyongwen.chapter8

import android.opengl.GLSurfaceView
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter8.*

class Chapter8Activity : BaseGLSurfaceActivity() {

    override fun getGlSurfaceView(): GLSurfaceView {
        return gl_surface
    }

    override fun getRenderer(): GLSurfaceView.Renderer {
        return AirHockey3DRenderer(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_chapter8
    }
}
