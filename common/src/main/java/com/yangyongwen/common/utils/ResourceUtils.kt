package com.yangyongwen.common.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Description:
 *
 * @author YangYongwen
 * Created on 2019/08/17
 */
object ResourceUtils {

    fun readTextFromResource(context: Context, resId: Int): String {
        val sb = StringBuilder()
        val inputStream = context.resources.openRawResource(resId)
        inputStream.use {
            try {
                val reader = BufferedReader(InputStreamReader(inputStream))
                for (line in reader.readLines()) {
                    sb.append(line).append("\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }

}