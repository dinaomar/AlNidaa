package com.dina.elcg.alnedaa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.R
import java.util.*

class LogoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTimer()
    }

    private fun startTimer() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val fragmentTransaction = fragmentManager?.beginTransaction()
                val gameFragment = GameFragment.newInstance()
                fragmentTransaction?.setCustomAnimations(R.anim.in_from_right,R.anim.out_to_left)
                fragmentTransaction?.replace(R.id.fragment,gameFragment)
                fragmentTransaction?.commit()
            }
        }, 5000)
    }


    companion object {
        @JvmStatic
        fun newInstance(): LogoFragment {
            return LogoFragment()
        }
    }
}
