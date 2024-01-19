package com.caicai.myapplication

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import com.caicai.myapplication.customView3_qqStepView.QQStepView
import com.caicai.myapplication.customView4_colorTextView.ColorTrackTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 测试 QQStepView
//        testQQStepView()
        // 测试ColorTrackTextView
        testColorTrackTextView()
    }

    private fun testColorTrackTextView() {
        val leftToRightButton = findViewById<Button>(R.id.left_to_right)
        val rightToLeftButton = findViewById<Button>(R.id.right_to_left)
        leftToRightButton.setOnClickListener { fromLeftToRight() }
        rightToLeftButton.setOnClickListener { fromRightToLeft() }
    }

    private fun fromLeftToRight() {
        val colorTrackTextView: ColorTrackTextView = findViewById(R.id.colorTextView)
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.setDuration(2000)
        colorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT)
        valueAnimator.addUpdateListener {
            val values = it.animatedValue as Float
            colorTrackTextView.setCurrentProgress(values)
        }
        valueAnimator.start()
    }

    private fun fromRightToLeft() {
        val colorTrackTextView: ColorTrackTextView = findViewById(R.id.colorTextView)
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.setDuration(2000)
        colorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT)
        valueAnimator.addUpdateListener {
            val values = it.animatedValue as Float
            colorTrackTextView.setCurrentProgress(values)
        }
        valueAnimator.start()
    }

    private fun testQQStepView() {
        val qqStepView: QQStepView = findViewById(R.id.qqStepView)
        qqStepView.setStepMax(10000)
        val valueAnimator: ValueAnimator = ValueAnimator.ofInt(0, 8000)
        valueAnimator.setDuration(3000L)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animatorValue ->
            val curStep = animatorValue.animatedValue as Int
            qqStepView.setCurrentStep(curStep)
        }
        valueAnimator.start()
    }
}