package com.ajkune.professional.utilities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ajkune.professional.R

class LoaderFragment: DialogFragment() {
    companion object {
        var mIsBlackBackground: Boolean = false
        fun newInstance(isBlackBackground: Boolean):LoaderFragment{
            mIsBlackBackground = isBlackBackground
            return LoaderFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mIsBlackBackground) {
            setStyle(STYLE_NORMAL, R.style.BLACK_DIALOG)
        }else {
            setStyle(STYLE_NORMAL, R.style.MY_DIALOG)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loader_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            d.window?.setLayout(width, height)
        }
    }
}