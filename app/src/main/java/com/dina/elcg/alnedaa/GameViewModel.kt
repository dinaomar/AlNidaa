package com.dina.elcg.alnedaa

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.graphics.contains
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var score: MutableLiveData<Int> = MutableLiveData(0)
    var screenRect: MutableLiveData<Rect> = MutableLiveData()
    lateinit var rect: Rect


    fun functionUp(view: View) {
        val paramsTop = view.layoutParams as RelativeLayout.LayoutParams
        rect = Rect(view.left, view.top - view.height * 2, view.right, view.bottom - view.height * 2)
        if (rect.intersect(screenRect.value)) {
            paramsTop.bottomMargin += 150
            view.layoutParams = paramsTop
            view.invalidate()
        }
    }

    fun functionDown(view: View) {
        val paramsBottom = view.layoutParams as RelativeLayout.LayoutParams
        rect = Rect(view.left, view.top + view.height * 3, view.right, view.bottom + view.height * 3)
        if (rect.intersect(screenRect.value)) {
            paramsBottom.bottomMargin -= 150
            view.layoutParams = paramsBottom
            view.invalidate()
        }

    }

    fun functionLeft(view: View) {
        val paramsLeft = view.layoutParams as RelativeLayout.LayoutParams
        rect = if ((view as ViewGroup).childCount == 1)
            Rect(view.left - view.width, view.top, view.right - view.width, view.bottom)
        else Rect(view.left - view.width, view.top, view.right - view.width, view.bottom)
        if (rect.intersect(screenRect.value)) {
            paramsLeft.leftMargin -= 150
            view.layoutParams = paramsLeft
            view.invalidate()
        }
    }

    fun functionRight(view: View) {
        val paramsRight = view.layoutParams as RelativeLayout.LayoutParams
        rect = if ((view as ViewGroup).childCount == 1)
            Rect(view.left + view.width * 3, view.top, view.right + view.width * 3, view.bottom)
        else Rect(view.left+ view.width *2 , view.top, view.right+ view.width * 2 , view.bottom)

        if (rect.intersect(screenRect.value)) {
            paramsRight.leftMargin += 150
            view.layoutParams = paramsRight
            view.invalidate()
        }
    }
}