package com.ajkune.professional.architecture.fragment.dashboard

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.dashboard.LuckyWheelViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.LuckyWheelFragmentBinding
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget
import com.bluehomestudio.luckywheel.WheelItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList
import javax.inject.Inject

class LuckyWheelFragment : BaseFragment() {

    lateinit var binding : LuckyWheelFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    private val lw: LuckyWheel? = null
    var wheelItems: ArrayList<WheelItem>? = null

    companion object {
        fun newInstance() = LuckyWheelFragment()
    }

    private lateinit var viewModel: LuckyWheelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.lucky_wheel_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
        viewModel = ViewModelProvider(this,viewModelFactory)[LuckyWheelViewModel::class.java]
        generateWheelItems()
        initBaseFunctions()
    }

    override fun onLoad() {
        binding.lwv.addWheelItems(wheelItems)
        binding.lwv.setTarget(1)

        binding.lwv.setLuckyWheelReachTheTarget {
            Toast.makeText(requireContext(), "Target Reached", Toast.LENGTH_LONG).show()
        }
    }

    override fun onError() {

    }

    override fun onClickEvents() {
        binding.btnPlay.setOnClickListener {
            binding.lwv.rotateWheelTo(1)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setToolbar() {

    }


    private fun generateWheelItems() {
        wheelItems = ArrayList<WheelItem>()
        (wheelItems)?.add(
            WheelItem(
                Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.home_icon
                ), "100 $"
            )
        )
        (wheelItems)?.add(
            WheelItem(
                Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.appointment_icon
                ), "0 $"
            )
        )
        (wheelItems)?.add(
            WheelItem(
                Color.parseColor("#F00E6F"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.gifs_icon
                ), "30 $"
            )
        )
        (wheelItems)?.add(
            WheelItem(
                Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.offers_icon
                ), "6000 $"
            )
        )
        (wheelItems)?.add(
            WheelItem(
                Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.account_icon
                ), "9 $"
            )
        )
        (wheelItems)?.add(
            WheelItem(
                Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.calendar_icon
                ), "20 $"
            )
        )
    }
}