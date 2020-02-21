package com.dina.elcg.alnedaa.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.R
import kotlinx.android.synthetic.main.fragment_logo.*
import java.util.*

class LogoFragment : Fragment() {

    var mPlayerbackground: MediaPlayer? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPlayerbackground = MediaPlayer.create(requireContext(), R.raw.light)
        mPlayerbackground?.setVolume(0.1f, 0.1f)
        mPlayerbackground?.start()
        logo.animate().translationXBy(550f).duration = 2000
        startTimer()
    }

    private fun startTimer() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    val gameFragment = GameFragment.newInstance()
                    fragmentTransaction?.setCustomAnimations(
                        R.anim.in_from_right,
                        R.anim.out_to_left
                    )
                    fragmentTransaction?.replace(R.id.fragment, gameFragment)
                    fragmentTransaction?.commitAllowingStateLoss()

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
