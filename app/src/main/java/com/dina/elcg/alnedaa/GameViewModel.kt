package com.dina.elcg.alnedaa

import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var score: MutableLiveData<Int> = MutableLiveData(0)

     fun functionUp(view:View) {
        val paramsTop = view.layoutParams as RelativeLayout.LayoutParams
        paramsTop.bottomMargin += 150
        view.layoutParams = paramsTop
        view.invalidate()
    }

     fun functionDown(view:View) {
        val paramsBottom = view.layoutParams as RelativeLayout.LayoutParams
        paramsBottom.bottomMargin -= 150
        view.layoutParams = paramsBottom
        view.invalidate()

    }

     fun functionLeft(view:View) {
        val paramsLeft = view.layoutParams as RelativeLayout.LayoutParams
        paramsLeft.leftMargin -= 150
        view.layoutParams = paramsLeft
        view.invalidate()
    }

     fun functionRight(view:View) {
        val paramsRight = view.layoutParams as RelativeLayout.LayoutParams
        paramsRight.leftMargin += 150
        view.layoutParams = paramsRight
        view.invalidate()
    }
}