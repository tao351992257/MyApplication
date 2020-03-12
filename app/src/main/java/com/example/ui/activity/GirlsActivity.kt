package com.example.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.myapplication.R
import com.example.ui.adapter.GirlsAdapter
import kotlinx.android.synthetic.main.activity_girls.*

class GirlsActivity : AppCompatActivity() {
    private var grils = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girls)
        initGirls()
        initView()
    }

    private fun initView() {
        recyclerView.adapter = GirlsAdapter(this, grils)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun initGirls() {
        grils.add(R.mipmap.image1)
        grils.add(R.mipmap.image2)
        grils.add(R.mipmap.image3)
        grils.add(R.mipmap.image4)
        grils.add(R.mipmap.image5)
        grils.add(R.mipmap.image6)
        grils.add(R.mipmap.image7)
        grils.add(R.mipmap.image8)
        grils.add(R.mipmap.image9)
        grils.add(R.mipmap.image10)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
