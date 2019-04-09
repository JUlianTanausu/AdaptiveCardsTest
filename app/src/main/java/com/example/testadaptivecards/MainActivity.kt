package com.example.testadaptivecards

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private val file_layout1 = ""
    private val file_layout2 = ""
    private val file_layout3 = "layoutJsons/apartamentosLayout.json"
    private val file_layout4 = ""
    private val file_layout5 = "layoutJsons/teatroLayout.json"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setupClicks()
    }


    private fun setupClicks(){
        image_close.setOnClickListener {
            finish()
        }
        text_first.setOnClickListener {
            val intent = Intent(it.context, ContentActivity::class.java)
            intent.putExtra("suggestion", "")
            startActivityForResult(intent, 0)
        }
        text_second.setOnClickListener {
            val intent2 = Intent(it.context, ContentActivity::class.java)
            intent2.putExtra("suggestion", "")
            startActivityForResult(intent2, 0)
        }
        text_third.setOnClickListener {
            val intent3 = Intent(it.context, ContentActivity::class.java)
            val obj = ReadJson(this, file_layout3)
            obj.readFileFromAssets()
            intent3.putExtra("configuration", obj.configureLayout)
            startActivityForResult(intent3, 0)
        }
        text_fourth.setOnClickListener {
            val intent4 = Intent(it.context, ContentActivity::class.java)

            startActivityForResult(intent4, 0)
        }
        text_fiveth.setOnClickListener {
            val intent5 = Intent(it.context, ContentActivity::class.java)
            val obj = ReadJson(this, file_layout5)
            obj.readFileFromAssets()
            intent5.putExtra("configuration", obj.configureLayout)
            startActivityForResult(intent5, 0)
        }
    }

}
