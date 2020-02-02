package com.dina.elcg.alnedaa.fragments

import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.R
import kotlinx.android.synthetic.main.fragment_game.*
import java.util.*


class GameFragment : Fragment() {

    var mPlayerbackground: MediaPlayer? = null
    var mPlayerQustion: MediaPlayer? = null
    lateinit var handlerOne: Handler
    lateinit var handlerTwo: Handler
    lateinit var handlerThree: Handler

    var i = 0
    var start = 0
    lateinit var stringOne: String
    lateinit var wordsOne: List<String>
    lateinit var stringTwo: String
    lateinit var wordsTwo: List<String>
    lateinit var stringThree: String
    lateinit var wordsThree: List<String>
    var span = SpannableStringBuilder()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPlayerbackground = MediaPlayer.create(this.context, R.raw.musicbg)
        mPlayerQustion = MediaPlayer.create(this.context, R.raw.vo)
        mPlayerbackground?.setVolume(0.3f, 0.3f)
        stringOne = textView1.text.toString()
        wordsOne = stringOne.split(" ")

        stringTwo = textView2.text.toString()
        wordsTwo = stringTwo.split(" ")

        stringThree = textView3.text.toString()
        wordsThree = stringThree.split(" ")

        // Initialize the handler instance
        handlerOne = Handler()
        handlerThree = Handler()
        handlerTwo = Handler()

        mPlayerbackground?.start()
        startTimer()
        btnStart.setOnClickListener { startGame() }

    }

    private fun startGame() {
        val questionsFragment = QuestionsFragment.newInstance()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
        fragmentTransaction?.replace(R.id.fragment, questionsFragment)
        fragmentTransaction?.commit()
    }

    private fun startTimer() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                mPlayerbackground?.setVolume(0.1f, 0.1f)
                mPlayerQustion?.start()
                handlerOne.postDelayed(runnableOne, 1000)

            }
        }, 1000)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onStop() {
        super.onStop()
        mPlayerbackground?.stop()
        mPlayerQustion?.stop()
        handlerOne.removeCallbacks(runnableOne);
        handlerTwo.removeCallbacks(runnableTwo);
        handlerThree.removeCallbacks(runnableThree);

    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayerbackground = null
        mPlayerQustion = null
    }

    private val runnableOne = object : Runnable {
        override fun run() {
            if (i < wordsOne.size) {
                span = SpannableStringBuilder(stringOne)
                span.setSpan(
                    BackgroundColorSpan(Color.YELLOW),
                    start,
                    wordsOne[i].length + start,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView1.setTextKeepState(span)
                start += wordsOne[i].length + i
                span.clearSpans()
                handlerOne.postDelayed(this, (100 * wordsOne[i].length).toLong())
                i++

            } else {
                i = 0
                start = 0
                handlerTwo.postDelayed(runnableTwo, 10)
                span = SpannableStringBuilder(stringOne)
                textView1.setTextKeepState(span)
            }
        }

    }

    private val runnableTwo = object : Runnable {
        override fun run() {
            if (i < wordsTwo.size) {
                span = SpannableStringBuilder(stringTwo)
                span.setSpan(
                    BackgroundColorSpan(Color.YELLOW),
                    start,
                    wordsTwo[i].length + start,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView2.setTextKeepState(span)
                start += wordsTwo[i].length + i
                span.clearSpans()
                handlerTwo.postDelayed(this, (100 * wordsTwo[i].length).toLong())
                i++

            } else {
                i = 0
                start = 0
                handlerThree.postDelayed(runnableThree, 10)
                span = SpannableStringBuilder(stringTwo)
                textView2.setTextKeepState(span)
            }
        }
    }

    private val runnableThree = object : Runnable {
        override fun run() {
            if (i < wordsThree.size) {
                span = SpannableStringBuilder(stringThree)
                span.setSpan(
                    BackgroundColorSpan(Color.YELLOW),
                    start,
                    wordsThree[i].length + start,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textView3.setTextKeepState(span)
                start += wordsThree[i].length + i
                span.clearSpans()
                handlerThree.postDelayed(this, (100 * wordsThree[i].length).toLong())
                i++

            } else {
                i = 0
                start = 0
                span = SpannableStringBuilder(stringThree)
                textView3.setTextKeepState(span)
            }

        }
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            GameFragment()

    }
}
