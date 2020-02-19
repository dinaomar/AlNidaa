package com.dina.elcg.alnedaa.fragments

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
import android.widget.LinearLayout
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
    var listOfTextViews: ArrayList<TextView> = ArrayList()
    var listOfLinesImageView: ArrayList<RelativeLayout> = ArrayList()


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

        getXY(cursorLayout)

        drawLayout()
    }

    private fun drawLayout() {
        val words: List<String> = Utilities.splitQuestion(QuestionsBank.questionOne)
//        val dpCalculation = resources.displayMetrics.density
        for (word: String in words) {
            // words layout
            val wordContainer = RelativeLayout(requireContext())
            val wordText = TextView(requireContext())
            wordText.text = word
            wordContainer.addView(wordText)
            wordsLayout.addView(wordContainer)
            wordText.textSize = 20F
            wordText.setTextColor(Color.parseColor("#FFFFFF"))
            wordText.layoutParams.height = 150
            wordText.setBackgroundResource(R.drawable.word_border)
            wordText.gravity = Gravity.CENTER
            wordText.tag = words.indexOf(word)
            listOfTextViews.add(wordText)

            // lines layout
            val lineContainer = RelativeLayout(requireContext())
            val imageLine = ImageView(requireContext())
            imageLine.setImageResource(R.drawable.lines)
            lineContainer.addView(imageLine)
            lines.addView(lineContainer)
            lineContainer.tag = words.indexOf(word)
            listOfLinesImageView.add(lineContainer)

        }
    }

    private fun functionSelect() {
        for (i in 0 until listOfTextViews.size) {
            if (cursorLayout.childCount==1) {
                if (checkCollision(listOfTextViews[i], cursorLayout)) {
                    if (listOfTextViews[i].parent != null) {
                        (listOfTextViews[i].parent as ViewGroup).removeView(listOfTextViews[i])
                    }
                    cursorLayout.addView(listOfTextViews[i])
                    break
                }
            }
            else {
                if (checkCollision(targets, cursorLayout)) {
                    val view = cursorLayout[1]
                    if (view.parent != null) {
                        (view.parent as ViewGroup).removeView(view)
                    }
                    targets.addView(view)
                    break
                }
            }
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
        getXY(cursorLayout)

    }

    private fun functionLeft() {
        val paramsLeft = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsLeft.leftMargin -= 60
        cursorLayout.layoutParams = paramsLeft
        cursorLayout.invalidate()
        getXY(cursorLayout)

    }

    private fun functionRight() {
        val paramsRight = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsRight.leftMargin += 60
        cursorLayout.layoutParams = paramsRight
        cursorLayout.invalidate()
        getXY(cursorLayout)

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

