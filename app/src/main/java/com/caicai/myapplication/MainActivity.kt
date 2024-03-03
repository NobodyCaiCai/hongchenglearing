package com.caicai.myapplication

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.caicai.myapplication.customView3_qqStepView.QQStepView
import com.caicai.myapplication.customView4_colorTextView.ColorTrackTextView
import com.caicai.myapplication.customView5_progressBar.ProgressBar
import com.caicai.myapplication.customView6_shapeView.ShapeView
import com.caicai.myapplication.customView8_LetterSideBar.LetterSideBar
import com.caicai.myapplication.customView9_tagLayout.TagLayout
import com.caicai.myapplication.customView9_tagLayout.BaseAdapter
import com.caicai.myapplication.customView9_tagLayout.TagCloudView

class MainActivity : AppCompatActivity() {

    private lateinit var mTagLayout: TagLayout
    private val mutableList = mutableListOf<String>()

    companion object {
        const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTagLayout = findViewById(R.id.tagLayout)
        mutableList.add("111")
        mutableList.add("1111111")
        mutableList.add("1111")
        mutableList.add("11")
        mutableList.add("1111111111")
        mutableList.add("1111")
        mutableList.add("11")
        mutableList.add("1111111111")
        mutableList.add("1111")
        mutableList.add("11")
        mutableList.add("1111111111")
        mTagLayout.setAdapter(object : BaseAdapter(){
            override fun getCount(): Int {
                return mutableList.size
            }

            override fun getView(position: Int, parent: ViewGroup?): View {
                val textView = layoutInflater.inflate(R.layout.item_tag, parent, false) as TextView
                textView.text = mutableList[position]
                return textView
            }
        })


//         测试 QQStepView
        testQQStepView()
//         测试ColorTrackTextView
        testColorTrackTextView()
//         测试ProgressBar
        findViewById<Button>(R.id.test_progress_bar_button).setOnClickListener {
            testProgressBar()
        }
//         测试ShapeView
        findViewById<Button>(R.id.test_shape_view_button).setOnClickListener {
            testShapeView()
        }
//         测试 LetterSideBar
        val textView: TextView = findViewById(R.id.text_view)
        val letterSideBar: LetterSideBar = findViewById(R.id.letter_side_bar)
        letterSideBar.setOnLetterTouchListener { letter, isTouch ->
            if (isTouch == true) {
                textView.visibility = View.VISIBLE
                textView.text = letter
            } else {
                textView.visibility = View.GONE
            }
        }
    }

    private fun testShapeView() {
        val shapeView = findViewById<ShapeView>(R.id.shapeView)
        val handler = Handler(Looper.getMainLooper())
        val updateUiRunnable: Runnable = object : Runnable {
            override fun run() {
                Log.i(TAG, "update shapeView")
                shapeView.exchangeShape();
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(updateUiRunnable, 1000)
    }

    private fun testProgressBar() {
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.setDuration(2000)
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            progressBar.setCurProcess(value)
        }
        animator.start()
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

//class MainActivity : AppCompatActivity(), TagCloudView.OnTagClickListener {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val tags: MutableList<String> = ArrayList()
//        for (i in 0..19) {
//            tags.add("标签$i")
//        }
//        val tagCloudView0: TagCloudView = findViewById(R.id.tag_cloud_view_0)
//        tagCloudView0.setTags(tags.subList(0, 1))
//        tagCloudView0.setOnTagClickListener(this)
//        tagCloudView0.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView1: TagCloudView = findViewById(R.id.tag_cloud_view_1)
//        tagCloudView1.setTags(tags)
//        tagCloudView1.setOnTagClickListener { position ->
//            if (position == -1) {
//                tagCloudView1.singleLine(false)
//                Toast.makeText(
//                    applicationContext, "点击展开标签",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                Toast.makeText(
//                    applicationContext, "点击 position : $position",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//        tagCloudView1.setOnClickListener {
//            tagCloudView1.singleLine(true)
//            Toast.makeText(
//                applicationContext, "点击关闭标签",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView2: TagCloudView = findViewById(R.id.tag_cloud_view_2)
//        tagCloudView2.setTags(tags)
//        tagCloudView2.setOnTagClickListener(this)
//        tagCloudView2.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView3: TagCloudView = findViewById(R.id.tag_cloud_view_3)
//        tagCloudView3.setTags(tags)
//        tagCloudView3.setOnTagClickListener(this)
//        tagCloudView3.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView4: TagCloudView = findViewById(R.id.tag_cloud_view_4)
//        tagCloudView4.setTags(tags)
//        tagCloudView4.setOnTagClickListener(this)
//        tagCloudView4.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView5: TagCloudView = findViewById(R.id.tag_cloud_view_5)
//        tagCloudView5.setTags(tags)
//        tagCloudView5.setOnTagClickListener(this)
//        tagCloudView5.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView6: TagCloudView = findViewById(R.id.tag_cloud_view_6)
//        tagCloudView6.setTags(tags)
//        tagCloudView6.setOnTagClickListener(this)
//        tagCloudView6.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//        val tagCloudView7: TagCloudView = findViewById(R.id.tag_cloud_view_7)
//        tagCloudView7.setTags(tags)
//        tagCloudView7.setOnTagClickListener(this)
//        tagCloudView7.setOnClickListener(View.OnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        })
//        val tagCloudView8: TagCloudView = findViewById(R.id.tag_cloud_view_8)
//        tagCloudView8.setTags(tags)
//        tagCloudView8.setOnTagClickListener(this)
//        tagCloudView8.setOnClickListener {
//            Toast.makeText(
//                applicationContext, "TagView onClick",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    override fun onTagClick(position: Int) {
//        if (position == -1) {
//            Toast.makeText(
//                applicationContext, "点击末尾文字",
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            Toast.makeText(
//                applicationContext, "点击 position : $position",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//}