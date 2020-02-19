package com.dina.elcg.alnedaa.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.QuestionsBank
import com.dina.elcg.alnedaa.R
import com.dina.elcg.alnedaa.Utilities
import com.dina.elcg.alnedaa.getLocationOnScreen
import kotlinx.android.synthetic.main.fragment_questions.*


class QuestionsFragment : Fragment() {

    var mPlayerbackground: MediaPlayer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mPlayerbackground = MediaPlayer.create(this.context, R.raw.musicbg)
//        mPlayerbackground?.setVolume(0.1f, 0.1f)
//        mPlayerbackground?.start()


        upBt.setOnClickListener { functionUp() }
        downBt.setOnClickListener { functionDown() }
        leftBt.setOnClickListener { functionLeft() }
        rightBt.setOnClickListener { functionRight() }
        selectBtn.setOnClickListener { functionSelect() }

        drawLayout()
    }

    private fun drawLayout() {
        val words: List<String> = Utilities.splitQuestion(QuestionsBank.questionOne)
        val dpCalculation = resources.displayMetrics.density
        for (word: String in words) {
            val wordContainer = RelativeLayout(requireContext())
            val wordText = TextView(requireContext())
            wordText.text = word
            wordContainer.addView(wordText)
            wordsLayout.addView(wordContainer)
            val imageLine = ImageView(requireContext())
            lines.addView(imageLine)
            wordText.textSize = 20F
            wordText.setTextColor(Color.parseColor("#FFFFFF"))
            wordText.layoutParams.height = 150
            wordText.setBackgroundResource(R.drawable.word_border)
            wordText.gravity = Gravity.CENTER

        }
    }


    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun functionSelect() {
//        if (cursorLayout.childCount == 1) {
        when {

//            }
//        } else {
//
//            when {
//                checkCollision(cursorLayout[0], lines) -> {
//                    val view = cursorLayout[0]
//                    if (view.parent != null)
//                        (view.parent as ViewGroup).removeView(view)
//                    targets.addView(view)
//                    cursorLayout[0].visibility = View.VISIBLE
//                    cursorLayout.removeView(view)
//                    checkResult()
//                }
//                checkCollision(cursorLayout[0], lines) -> {
//                    val view = cursorLayout[0]
//                    if (view.parent != null)
//                        (view.parent as ViewGroup).removeView(view)
//                    targets.addView(view)
//                    cursorLayout[0].visibility = View.VISIBLE
//                    cursorLayout.removeView(view)
//                    checkResult()
//                }
//                checkCollision(cursorLayout, lines) -> {
//                    val view = cursorLayout[0]
//                    if (view.parent != null)
//                        (view.parent as ViewGroup).removeView(view)
//                    targets.addView(view)
//                    cursorLayout[0].visibility = View.VISIBLE
//                    cursorLayout.removeView(view)
//                    checkResult()
//                }
//            }

        }
    }


    private fun checkResult() {
        if (targets.childCount == 6) {
            if (targets[0].tag == "one" && targets[1].tag == "two" &&
                targets[2].tag == "three" && targets[3].tag == "four" &&
                targets[4].tag == "five" && targets[5].tag == "six"
            )
                Log.w("right", "done")
        }
    }

    private fun checkCollision(v1: View, v2: View): Boolean {
//        var rect1 = Rect()
//
//        v1.getHitRect(rect1)
//        var rect2 = Rect()
//        v2.getHitRect(rect2)

        val R1 = Rect(v1.left, v1.top, v1.right, v1.bottom)
        val R2 = Rect(v2.left, v2.top, v2.right, v2.bottom)
        return R1.intersect(R2)

//        return rect1.intersect(rect2)
    }


    private fun functionUp() {

        val paramsTop = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsTop.bottomMargin += 60
        cursorLayout.layoutParams = paramsTop
        cursorLayout.invalidate()
        getXY(cursorLayout)
    }

    private fun functionDown() {
        val paramsBottom = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsBottom.bottomMargin -= 60
        cursorLayout.layoutParams = paramsBottom
        cursorLayout.invalidate()
    }

    private fun functionLeft() {
        val paramsLeft = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsLeft.leftMargin -= 60
        cursorLayout.layoutParams = paramsLeft
        cursorLayout.invalidate()
    }

    private fun functionRight() {
        val paramsRight = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsRight.leftMargin += 60
        cursorLayout.layoutParams = paramsRight
        cursorLayout.invalidate()
    }

    fun getXY(view: View) {
        Log.w("dinax", "" + view.getLocationOnScreen().x)
        Log.w("dinay", "" + view.getLocationOnScreen().y)

    }


    override fun onStop() {
        super.onStop()
        mPlayerbackground?.stop()
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuestionsFragment()
    }
}

