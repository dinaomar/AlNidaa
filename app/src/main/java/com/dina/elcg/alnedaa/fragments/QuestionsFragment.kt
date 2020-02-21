package com.dina.elcg.alnedaa.fragments

import android.graphics.Color
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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dina.elcg.alnedaa.GameViewModel
import com.dina.elcg.alnedaa.QuestionsBank
import com.dina.elcg.alnedaa.R
import com.dina.elcg.alnedaa.Utilities
import com.dina.elcg.alnedaa.databinding.FragmentQuestionsBinding
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_questions.*


class QuestionsFragment : Fragment() {

    lateinit var listOfTextViews: ArrayList<TextView>
    lateinit var listOfLinesImageView: ArrayList<RelativeLayout>
    var correctSentence = false
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentQuestionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_questions, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upBt.setOnClickListener { viewModel.functionUp(cursorLayout) }
        downBt.setOnClickListener { viewModel.functionDown(cursorLayout) }
        leftBt.setOnClickListener { viewModel.functionLeft(cursorLayout) }
        rightBt.setOnClickListener { viewModel.functionRight(cursorLayout) }
        selectBtn.setOnClickListener { functionSelect() }
        replay.visibility = View.GONE
        drawLayout()
        drawLives()
        val displayMetrics = resources.displayMetrics
        // Display Rect Boundaries
        val parentRect = Rect(20, 20, displayMetrics.widthPixels, displayMetrics.heightPixels)
        viewModel.screenRect.value = parentRect
    }

    private fun drawLives() {
        for (i: Int in 0 until viewModel.score.value!!) {
            val heartImage = ImageView(requireContext())
            heartImage.setImageResource(R.drawable.heartcropped)
            scoreLayout.addView(heartImage)
        }
    }

    private fun drawLayout() {
        val words: List<String> = Utilities.splitQuestion(QuestionsBank.questionOne)
        listOfLinesImageView = ArrayList()
        listOfTextViews = ArrayList()
        for (word: String in words) {
            // words layout
            val wordContainer = RelativeLayout(requireContext())
            val wordText = TextView(requireContext())
            wordText.text = word
            wordContainer.addView(wordText)
            wordsLayout.addView(wordContainer)
            wordText.textSize = 18F
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
                navigateToQuestionTwo()
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

    private fun navigateToQuestionTwo() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val questionTwoFragment = QuestionTwoFragment.newInstance()
        fragmentTransaction?.setCustomAnimations(
            R.anim.in_from_right,
            R.anim.out_to_left
        )
        fragmentTransaction?.replace(R.id.fragment, questionTwoFragment)
        fragmentTransaction?.commitAllowingStateLoss()
    }

    private fun drawLive() {
        val heartImage = ImageView(requireContext())
        heartImage.setImageResource(R.drawable.heartcropped)
        scoreLayout.addView(heartImage)

    }

    private fun checkResult() {
        var score = 0
        var counter = listOfTextViews.size
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
                TastyToast.makeText(
                    requireContext(),
                    "أحسنت",
                    TastyToast.LENGTH_LONG,
                    TastyToast.SUCCESS
                )
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
                val animation1 = AlphaAnimation(0.0f, 1.0f)
                animation1.duration = 500
                animation1.startOffset = 300
                animation1.fillAfter = true
                sentenceType.startAnimation(animation1)
                viewModel.score.value = 1
                drawLive()

            } else {
                // not all words placed correctly
                // show error message
                TastyToast.makeText(
                    requireContext(),
                    "حاول مرة أخري",
                    TastyToast.LENGTH_LONG,
                    TastyToast.ERROR
                )
                val mp: MediaPlayer = MediaPlayer.create(
                    requireContext(),
                    R.raw.boo2
                )
                mp.start()
                lines.removeAllViews()
                wordsLayout.removeAllViews()
                drawLayout()
            }
        }

    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuestionsFragment()
    }
}

