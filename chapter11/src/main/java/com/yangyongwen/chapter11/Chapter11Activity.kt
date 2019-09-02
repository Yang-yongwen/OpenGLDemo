package com.yangyongwen.chapter11

import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.yangyongwen.common.ui.BaseGLSurfaceActivity
import kotlinx.android.synthetic.main.activity_chapter11.*

class Chapter11Activity : BaseGLSurfaceActivity() {

    private val renderer = ParticlesRenderer(this)
    private var lastX = 0f
    private var lastY = 0f

    override fun getGlSurfaceView(): GLSurfaceView {
        return gl_surface
    }

    override fun getRenderer(): GLSurfaceView.Renderer {
        return renderer
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_chapter11
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            return when (action) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = x
                    lastY = y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = x - lastX
                    val deltaY = y - lastY

                    lastX = x
                    lastY = y

                    gl_surface.queueEvent {
                        renderer.handleTouchDrag(deltaX, deltaY)
                    }
                    true
                }
                else -> false
            }
        }
        return super.onTouchEvent(event)
    }

}
