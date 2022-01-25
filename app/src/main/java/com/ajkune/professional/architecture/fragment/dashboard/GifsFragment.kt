package com.ajkune.professional.architecture.fragment.dashboard

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.dashboard.GifsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.GifsFragmentBinding
import com.bluehomestudio.luckywheel.WheelItem
import javax.inject.Inject

class GifsFragment : BaseFragment() {

    lateinit var binding : GifsFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    val wheelItems: MutableList<WheelItem> = ArrayList()


    companion object {
        fun newInstance() = GifsFragment()
    }

    private lateinit var viewModel: GifsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.gifs_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[GifsViewModel::class.java]
        initBaseFunctions()
    }

    override fun onLoad() {

    }

    override fun onError() {
    }

    override fun onClickEvents() {
    }

    override fun setToolbar() {
    }
}