package com.dina.elcg.alnedaa.fragments

import android.annotation.SuppressLint
import android.graphics.BlendMode
import android.graphics.PorterDuff
import android.graphics.Rect
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.R
import kotlinx.android.synthetic.main.fragment_questions.*


class QuestionsFragment : Fragment() {

    var mPlayerbackground: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        mPlayerbackground = MediaPlayer.create(this.context, R.raw.musicbg)
        mPlayerbackground?.setVolume(0.3f, 0.3f)
        mPlayerbackground?.start()

        upBt.setOnClickListener { functionUp() }
        downBt.setOnClickListener { functionDown() }
        leftBt.setOnClickListener { functionLeft() }
        rightBt.setOnClickListener { functionRight() }
        selectBtn.setOnClickListener { functionSelect() }
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun functionSelect() {
        if (checkCollision(cursor,wordOne)){
            // do something
            wordOne.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.colorPrimary)
        }
        else if (checkCollision(cursor,wordTwo)){

        }
        else if (checkCollision(cursor,wordThree)){

        }
        else if (checkCollision(cursor,wordFour)){

        }
        else if (checkCollision(cursor,wordFive)){

        }
        else if (checkCollision(cursor,wordSix)){

        }
    }

    private fun checkCollision(v1: View, v2: View): Boolean {
        val R1 = Rect(v1.left, v1.top, v1.right, v1.bottom)
        val R2 = Rect(v2.left, v2.top, v2.right, v2.bottom)
        return R1.intersect(R2)
    }

    private fun functionUp() {
        val paramsTop = cursor.layoutParams as RelativeLayout.LayoutParams
        paramsTop.bottomMargin += 20
        cursor.layoutParams = paramsTop
    }

    private fun functionDown() {
        val paramsBottom = cursor.layoutParams as RelativeLayout.LayoutParams
        paramsBottom.bottomMargin -= 20
        cursor.layoutParams = paramsBottom
    }

    private fun functionLeft() {
        val paramsLeft = cursor.layoutParams as RelativeLayout.LayoutParams
        paramsLeft.leftMargin -= 20
        cursor.layoutParams = paramsLeft
    }

    private fun functionRight() {
        val paramsRight = cursor.layoutParams as RelativeLayout.LayoutParams
        paramsRight.leftMargin += 20
        cursor.layoutParams = paramsRight
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

