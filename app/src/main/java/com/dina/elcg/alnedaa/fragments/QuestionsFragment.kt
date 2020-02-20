package com.dina.elcg.alnedaa.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.QuestionsBank
import com.dina.elcg.alnedaa.R
import com.dina.elcg.alnedaa.Utilities
import kotlinx.android.synthetic.main.fragment_questions.*


class QuestionsFragment : Fragment() {

    var mPlayerbackground: MediaPlayer? = null
    var listOfTextViews: ArrayList<TextView> = ArrayList()
    var listOfLinesImageView: ArrayList<RelativeLayout> = ArrayList()
    var correctSentence = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

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
            wordText.setTextColor(Color.parseColor("#E6BF24"))
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
            imageLine.tag = words.indexOf(word)
            lines.addView(lineContainer)
            listOfLinesImageView.add(lineContainer)
            val relativeParams = lineContainer.layoutParams as LinearLayout.LayoutParams
            relativeParams.setMargins(10, 5, 10, 5)// left, top, right, bottom
            lineContainer.layoutParams = relativeParams
        }
    }

    private fun functionSelect() {
        var rect1 = Rect()
        var rect2 = Rect()

        if (!correctSentence) {

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
                        view.setBackgroundResource(0)
                        checkResult()
                        break
                    }
                }
            }
        } else {
            cursorLayout.getGlobalVisibleRect(rect1)
            close.getGlobalVisibleRect(rect2)
            if (rect1.intersect(rect2)) {
                // kareeb selected - right answer
                close.setTextColor(Color.GREEN)
                away.setTextColor(Color.parseColor("#E6BF24"))
            }
            cursorLayout.getGlobalVisibleRect(rect1)
            away.getGlobalVisibleRect(rect2)
            if (rect1.intersect(rect2)) {
                // baeed selected - wrong
                away.setTextColor(Color.RED)
                close.setTextColor(Color.parseColor("#E6BF24"))

            }
        }
    }

    private fun checkResult() {
        var score = 0
        var counter = 6
        for (child: RelativeLayout in listOfLinesImageView) {
            if (child.childCount > 1) {
                counter--
                if (child[0].tag == child[1].tag) score++
            } else {
                counter++
                break
            }
        }
        if (counter == 0) {
            if (score == listOfTextViews.size) {
                // all words placed correct
                Toast.makeText(requireContext(), "all done correct", Toast.LENGTH_LONG).show()
                correctSentence = true
                // remove dashes
                for (child: RelativeLayout in listOfLinesImageView) {
                    child[0].visibility = View.GONE
                }
                // play clap sound
                val mp: MediaPlayer = MediaPlayer.create(
                    requireContext(),
                    R.raw.applause10
                )
                mp.start()
                // show sentence type
                sentenceType.visibility = View.VISIBLE
                val animation1 = AlphaAnimation(0.4f, 1.0f)
                animation1.duration = 500
                animation1.startOffset = 3000
                animation1.fillAfter = true
                sentenceType.startAnimation(animation1)


            } else {
                // not all words placed correctly
                // show error message
                Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                val mp: MediaPlayer = MediaPlayer.create(
                    requireContext(),
                    R.raw.boo2
                )
                mp.start()
            }
        }

    }

    private fun functionUp() {

        val paramsTop = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsTop.bottomMargin += 100
        cursorLayout.layoutParams = paramsTop
        cursorLayout.invalidate()
    }

    private fun functionDown() {
        val paramsBottom = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsBottom.bottomMargin -= 100
        cursorLayout.layoutParams = paramsBottom
        cursorLayout.invalidate()

    }

    private fun functionLeft() {
        val paramsLeft = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsLeft.leftMargin -= 100
        cursorLayout.layoutParams = paramsLeft
        cursorLayout.invalidate()
    }

    private fun functionRight() {
        val paramsRight = cursorLayout.layoutParams as RelativeLayout.LayoutParams
        paramsRight.leftMargin += 100
        cursorLayout.layoutParams = paramsRight
        cursorLayout.invalidate()
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

