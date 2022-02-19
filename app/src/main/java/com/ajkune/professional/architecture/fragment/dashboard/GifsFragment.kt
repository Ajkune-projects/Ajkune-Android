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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.dashboard.GifsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.GifsFragmentBinding
import com.bluehomestudio.luckywheel.WheelItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class GifsFragment : BaseFragment() , SwipeRefreshLayout.OnRefreshListener{

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
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.VISIBLE
        binding = DataBindingUtil.inflate(inflater, R.layout.gifs_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[GifsViewModel::class.java]
        binding.swipeContainer.setOnRefreshListener(this)
        initBaseFunctions()
    }

    override fun onLoad() {
        showLoader()
        viewModel.hasGifts()

        viewModel.hasUserGifts.observe(this, Observer {
            if (it != null) {
                hideLoader()
                if (it.userHasGift){
                    binding.clPlay.visibility = View.VISIBLE
                    binding.cvEmptyGifts.visibility = View.GONE
                }else{
                    binding.clPlay.visibility = View.GONE
                    binding.cvEmptyGifts.visibility = View.VISIBLE
                }
                binding.swipeContainer.isRefreshing = false
            }
        })

    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_gifsFragment_to_luckyWheelFragment)
        }
    }

    override fun setToolbar() {
    }

    override fun onRefresh() {
        viewModel.hasGifts()
    }
}