package com.yucheng.autozoomviewpaggerdialog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fakeData by lazy {
        listOf(
            R.layout.fake_page1,
            R.layout.fake_page2,
            R.layout.fake_page3,
            R.layout.fake_page4,
            R.layout.fake_page5
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textview.setOnClickListener {
            AutoZoomViewpagerDialog(fakeData).show(supportFragmentManager, "")
        }
    }
}
