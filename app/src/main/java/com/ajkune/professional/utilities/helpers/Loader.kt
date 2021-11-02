package com.ajkune.professional.utilities.helpers

import androidx.fragment.app.FragmentManager
import com.ajkune.professional.utilities.fragments.LoaderFragment

class Loader (private val supportFragmentManager: FragmentManager, private val isBlackBackground: Boolean) {
    lateinit var bottomDialogFragment : LoaderFragment

    fun show() {
        bottomDialogFragment= LoaderFragment.newInstance(isBlackBackground)
        if (bottomDialogFragment.isAdded) {
            bottomDialogFragment.dismiss()
        }
        bottomDialogFragment.show(supportFragmentManager, "txn_tag")

    }

    fun hide() {
        if (::bottomDialogFragment.isInitialized){
            bottomDialogFragment.dismiss()

        }
    }
}