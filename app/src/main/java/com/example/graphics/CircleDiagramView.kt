package com.example.graphics

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View as View
import android.graphics.RectF
import java.util.*
import kotlin.collections.ArrayList

class CircleDiagramView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var segmentsCount = 0
    private var intList = ArrayList<Int>()
    private var angleList = ArrayList<Float>()
    private var paint = Paint().apply {
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        this.color = color
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private var arcPart = RectF().apply {
        set(340f, 620f, 740f, 1020f)
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.CircleDiagramView,
            0,
            0
        )
        segmentsCount = a.getInteger(R.styleable.CircleDiagramView_cdv_count, 1)
        a.recycle()
    }

    fun setIntList(newIntList: ArrayList<Int>) {
        intList = newIntList
        setAngleList()
        invalidate()
    }

    private fun setAngleList() {
        var sum = 0
        for (i in intList)
            sum += i
        for (j in intList)
            angleList.add(-1f + 360F * j / sum) // x/360 = intList{i}/sum
    }

    override fun onDraw(canvas: Canvas?) {
        var startAngel = 0F
        for (i in 0 until segmentsCount) {
            canvas?.drawArc(arcPart, startAngel, angleList[i], true, paint)
            startAngel += angleList[i] + 1f
        }
    }
}
