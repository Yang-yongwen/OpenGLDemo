package com.yangyongwen.chapter4

import android.opengl.GLSurfaceView
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter4.*

class Chapter4Activity : BaseGLSurfaceActivity() {

    override fun getGlSurfaceView(): GLSurfaceView {
        return gl_surface
    }

    override fun getRenderer(): GLSurfaceView.Renderer {
        return AirHockeyRenderer(this)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_chapter4
    }
}
