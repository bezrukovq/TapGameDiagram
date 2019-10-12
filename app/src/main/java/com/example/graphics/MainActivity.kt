package com.example.graphics

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.*
import android.view.View
import android.widget.ViewSwitcher
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.TextView

class MainActivity : AppCompatActivity(), ViewSwitcher.ViewFactory {
    private var cnt3 = 3
    private var cnt5 = 5
    private var leftFinger = 0
    private var rightFinger = 0
    private var nowLeft = true
    private var inGame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(!inGame){
            initGame()
        }
    }

    private fun initGame() {
        textSwitcher.setFactory(this)
        textSwitcher.inAnimation = AnimationUtils.loadAnimation(
            this,
            android.R.anim.slide_in_left
        )
        textSwitcher.outAnimation = AnimationUtils.loadAnimation(
            this,
            android.R.anim.slide_out_right
        )
        textSwitcher.setText("Проверим твои пальчики?")
        btn_yeah.setOnClickListener {
            begin()
            inGame = true
        }
    }

    private fun begin() {
        if (nowLeft)
            textSwitcher.setText("Тыкай указательным пальцем ЛЕВОЙ руки как можно быстрее на счет 3")
        else
            textSwitcher.setText("Тыкай указательным пальцем ПРАВОЙ руки как можно быстрее на счет 3")
        btn_yeah.visibility = View.GONE
        Handler().postDelayed({
            countFive()
        }, 2000)
    }

    private fun countFive() {
        Handler().postDelayed({
            if (cnt3 != 0) {
                textSwitcher.setText(cnt3.toString())
                cnt3--
                countFive()
            } else {
                cnt3 = 3
                textSwitcher.setText("Жмякай!!!!")
                vibrate()
                start()
            }
        }, 1000)
    }

    private fun countTen() {
        Handler().postDelayed({
            if (cnt5 != 0) {
                textSwitcher.setText(cnt5.toString())
                cnt5--
                countTen()
            } else {
                cnt5 = 5
                textSwitcher.setText("Охладись!!!!")
                vibrate()
                btn_click.visibility = View.GONE
                Handler().postDelayed({
                    if (nowLeft) {
                        nowLeft = false
                        begin()
                    } else
                        finishGame()
                }, 2000)
            }
        }, 1000)
    }

    private fun start() {
        btn_click.visibility = View.VISIBLE
        btn_click.setOnClickListener {
            if (nowLeft)
                leftFinger++
            else
                rightFinger++
        }
        countTen()
    }

    private fun finishGame() {
        Handler().postDelayed({
            textSwitcher.setText("Результаты обрабатываются...")
            Handler().postDelayed({
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("left", leftFinger)
                intent.putExtra("right", rightFinger)
                startActivity(intent)
                finish()
            }, 1000)
        }, 1000)
    }

    private fun vibrate() {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(100)
        }
    }

    override fun makeView(): View {
        val textView = TextView(this)
        textView.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
        textView.textSize = 20f
        textView.setTextColor(Color.BLACK)
        return textView
    }
}
