package com.dina.elcg.alnedaa.fragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dina.elcg.alnedaa.R


class GameFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mPlayer: MediaPlayer = MediaPlayer.create(this.context, R.raw.musicbg)
        mPlayer.setVolume(50f,50f)
        mPlayer.start()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
//        mPlayer.stop()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            GameFragment()

    }
}
