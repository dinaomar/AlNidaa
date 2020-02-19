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
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.children
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

            val sepratorView = View(requireContext())
            lines.addView(sepratorView)
            sepratorView.layoutParams.width = 20

        }
    }

    private fun functionSelect() {
        var rect1 = Rect()
        var rect2 = Rect()

        for (i in 0 until listOfTextViews.size) {
            if (cursorLayout.childCount == 1) {
                cursorLayout.getGlobalVisibleRect(rect1)
                listOfTextViews[i].getGlobalVisibleRect(rect2)
                if (rect1.intersect(rect2)) {
                    if (listOfTextViews[i].parent != null) {
                        (listOfTextViews[i].parent as ViewGroup).removeView(listOfTextViews[i])
                    }
                    cursorLayout.addView(listOfTextViews[i])
                    break
                }
            } else {
                cursorLayout.getGlobalVisibleRect(rect1)
                listOfLinesImageView[i].getGlobalVisibleRect(rect2)
                if (rect1.intersect(rect2)) {
                    val view = cursorLayout[1]
                    if (view.parent != null) {
                        (view.parent as ViewGroup).removeView(view)
                    }
                    listOfLinesImageView[i].addView(view)
                    break
                }
            }
        }
    }

    private fun checkResult(){
        var score:Int = 0
        if (lines.childCount == listOfTextViews.size){
            for (child:RelativeLayout in listOfLinesImageView){
                if (child[0].tag == child[1].tag){
                    score++
                }
            }
            if (score == listOfTextViews.size){
                // all words placed correct
                
            }
            else {
                // not all words placed correctly
                // show error message
            }
        }
    }

    private fun functionUp() {

        val paramsTop = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsTop.bottomMargin += 60
        cursorLayout.layoutParams = paramsTop
        cursorLayout.invalidate()
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

