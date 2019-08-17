package com.yangyongwen.opengldemo.utils

import android.app.ActivityManager
import android.content.Context

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/17
 */

object OpenGLUtils {

    @JvmStatic
    fun supportGlEs2(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return am.deviceConfigurationInfo.reqGlEsVersion >= 0x20000
    }

}

