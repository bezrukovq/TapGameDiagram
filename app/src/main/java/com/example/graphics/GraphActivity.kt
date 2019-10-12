package com.example.graphics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : AppCompatActivity() {

    private var view : CircleDiagramView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        view = cdv_main
        val arrayList: ArrayList<Int> = ArrayList()
        val left = intent.getIntExtra("left",1)
        val right = intent.getIntExtra("right",1)
        arrayList.add(left)
        arrayList.add(right)
        view?.setIntList(arrayList)
        tv_left.text = "Left: $left"
        tv_right.text = "Right: $right"
    }
}
