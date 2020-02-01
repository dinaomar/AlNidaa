package com.dina.elcg.alnedaa

import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.animation.DecelerateInterpolator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.dina.elcg.alnedaa.fragments.LogoFragment

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showLogoFragment()
    }

    private fun showLogoFragment() {
        val logoFragment = LogoFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val slide = Slide()
        slide.duration = 500
        slide.interpolator = DecelerateInterpolator()
        logoFragment.enterTransition = slide

        fragmentTransaction.replace(R.id.fragment, logoFragment)
        fragmentTransaction.commit()

    }
}
