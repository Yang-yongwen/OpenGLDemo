package com.yangyongwen.opengldemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.yangyongwen.chapter10.Chapter10Activity
import com.yangyongwen.chapter11.Chapter11Activity
import com.yangyongwen.chapter12.Chapter12Activity
import com.yangyongwen.chapter3.Chapter3Activity
import com.yangyongwen.chapter4.Chapter4Activity
import com.yangyongwen.chapter5.Chapter5Activity
import com.yangyongwen.chapter6.Chapter6Activity
import com.yangyongwen.chapter7.Chapter7Activity
import com.yangyongwen.chapter8.Chapter8Activity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val chapterList = arrayListOf(
        Chapter3Activity::class.java,
        Chapter4Activity::class.java,
        Chapter5Activity::class.java,
        Chapter6Activity::class.java,
        Chapter7Activity::class.java,
        Chapter8Activity::class.java,
        Chapter10Activity::class.java,
        Chapter11Activity::class.java,
        Chapter12Activity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (item in chapterList) {
            val button = Button(this)
            button.text = item.simpleName.replace("activity", "", ignoreCase = true).toLowerCase()
            button.setOnClickListener {
                startActivity(Intent(this@MainActivity, item))
            }
            ll_container.addView(
                button,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

}
