package com.yangyongwen.opengldemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yangyongwen.opengldemo.utils.OpenGLUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!OpenGLUtils.supportGlEs2(this)) {
            finish()
            return
        }
        initGL()
    }

    private fun initGL() {
        gl_surface.setEGLContextClientVersion(2)
        gl_surface.setRenderer(AirHockeyRenderer(this))
    }

    override fun onResume() {
        super.onResume()
        gl_surface.onResume()
    }

    override fun onPause() {
        super.onPause()
        gl_surface.onPause()
    }

}
