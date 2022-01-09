package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.FilterProductsFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class FilterProductsFragment : BaseFragment() {

    var startPrice: Int = 10
    var endPrice: Int = 200

    lateinit var binding : FilterProductsFragmentBinding

    var type : String = "product"

    var currentType : String = "product"

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = FilterProductsFragment()
    }

    private lateinit var viewModel: FilterProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.filter_products_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
        viewModel = ViewModelProvider(this,viewModelFactory)[FilterProductsViewModel::class.java]
        getData()
        initBaseFunctions()
    }


    override fun onLoad() {
        binding.txtStartPrice.text = "CHF $startPrice"
        binding.txtEndPrice.text = "CHF $endPrice"

        binding.rangeBar.setMinStartValue(10f).apply()
        binding.rangeBar.setMaxStartValue(200f).apply()

        binding.rangeBar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            binding.txtStartPrice.text = "CHF $minValue"
            binding.txtEndPrice.text = "CHF $maxValue "
            startPrice = minValue.toInt()
            endPrice = maxValue.toInt()
        }

        if (type == "offer"){
            binding.clFilterOffers.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f)
            binding.clFilterProducts.setBackgroundResource(R.drawable.border_radius_6_cl_8c93a9)
            binding.txtFilterOffers.setTextColor(requireContext().getColor(R.color.white))
            binding.txtFilterProduct.setTextColor(requireContext().getColor(R.color.cl_354044))
        }
    }

    override fun onError() {
    }

    override fun onClickEvents() {

        binding.clFilterProducts.setOnClickListener {

        }


        binding.clFilterProducts.setOnClickListener {
            type = "product"
            binding.clFilterProducts.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f)
            binding.clFilterOffers.setBackgroundResource(R.drawable.border_radius_6_cl_8c93a9)
            binding.txtFilterProduct.setTextColor(requireContext().getColor(R.color.white))
            binding.txtFilterOffers.setTextColor(requireContext().getColor(R.color.cl_354044))
        }
        binding.clFilterOffers.setOnClickListener {
            type = "offer"
            binding.clFilterOffers.setBackgroundResource(R.drawable.border_radius_6_cl_a8466f)
            binding.clFilterProducts.setBackgroundResource(R.drawable.border_radius_6_cl_8c93a9)
            binding.txtFilterOffers.setTextColor(requireContext().getColor(R.color.white))
            binding.txtFilterProduct.setTextColor(requireContext().getColor(R.color.cl_354044))
        }

        binding.imgClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDone.setOnClickListener {
            if (type == "product"){
                findNavController().navigate(FilterProductsFragmentDirections.actionFilterProductsFragmentToHomeFragment(startPrice,endPrice,type))
            }else{
                findNavController().navigate(FilterProductsFragmentDirections.actionFilterProductsFragmentToOffersFragment(startPrice,endPrice,type))
            }
        }
    }

    override fun setToolbar() {

    }

    private fun getData() {
        arguments?.let { bundle ->
            FilterProductsFragmentArgs.fromBundle(bundle).let {
                if (it.sortType != ""){
                   type = it.sortType
                }

            }
        }
    }

}