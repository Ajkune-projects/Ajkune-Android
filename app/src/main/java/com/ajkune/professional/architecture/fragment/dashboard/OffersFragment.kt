package com.ajkune.professional.architecture.fragment.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.activities.DashboardActivity
import com.ajkune.professional.architecture.activities.ProductDetailsActivity
import com.ajkune.professional.architecture.adapters.CategoryAdapter
import com.ajkune.professional.architecture.adapters.OffersAdapter
import com.ajkune.professional.architecture.adapters.ProductsAdapter
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.architecture.models.Offer
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.viewmodels.dashboard.OffersViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.OffersFragmentBinding
import com.ajkune.professional.utilities.extensions.loadUrl
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class OffersFragment : BaseFragment(), OffersAdapter.Listener {

    lateinit var binding : OffersFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    lateinit var offersAdapter: OffersAdapter

    var offers: MutableList<Offer> = mutableListOf()

    var isFirstTime : Boolean = true

    var minPrice : Int? = null
    var maxPrice : Int? = null
    var type : String? = null

    companion object {
        fun newInstance() = OffersFragment()
    }

    private lateinit var viewModel: OffersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.offers_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[OffersViewModel::class.java]
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.VISIBLE
        getData()
        initBaseFunctions()
        initRecyclerViewOffers()

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLoad() {
        if (type!= null){
            showLoader()
            binding.cvBanner.visibility = View.GONE
            viewModel.filterOffers(minPrice!!,maxPrice!!,type!!)
            isFirstTime = false
        }else{
            showLoader()
            viewModel.getBanner()
            viewModel.getAllOffers()
            isFirstTime = false
        }

        viewModel.offers.observe(this, Observer {
            if (it != null) {
                hideLoader()
                offers.clear()
                offers.addAll(it)
                binding.rvProducts.adapter?.notifyDataSetChanged()
                initRecyclerViewOffers()
            }
        })

        viewModel.filterProductOrOffers.observe(this, Observer {
            if (it != null) {
                hideLoader()
                offers.clear()
                offers.addAll(it)
                binding.rvProducts.adapter?.notifyDataSetChanged()
                initRecyclerViewOffers()
            }
        })

        viewModel.banners.observe(this, Observer {
            if (it != null) {
                hideLoader()
                binding.txtBannerPrice.text = getString(R.string.price,it[0].price)
                it[0].imagePath.let {
                    binding.imgBanner.loadUrl(it)
                    binding.imgBanner.visibility = View.VISIBLE
                }
                binding.txtBannerText.text = it[0].title

            }
        })
    }

    override fun onError() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onClickEvents() {
        binding.txtFilter.setOnClickListener {
            findNavController().navigate(OffersFragmentDirections.actionOffersFragmentToFilterProductsFragment("offer"))
        }
    }

    override fun setToolbar() {
    }

    private fun initRecyclerViewOffers(){
        val recyclerView = binding.rvProducts
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        offersAdapter = OffersAdapter(this)
        offersAdapter.setData(offers)
        offersAdapter.setHasStableIds(true)
        recyclerView.adapter = offersAdapter

    }

    private fun getData() {
        arguments?.let { bundle ->
            HomeFragmentArgs.fromBundle(bundle).let {
                if (it.type != ""){
                    minPrice = it.minPrice
                    maxPrice = it.maxPrice
                    type = it.type
                    if (type == "product"){
                        (activity as DashboardActivity).openHomeFragment()
                    }
                }

            }
        }
    }

    override fun onOffersClicked(product: Offer) {
        val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
        intent.putExtra("PRODUCT_DETAILS", Gson().toJson(product))
        startActivity(intent)
    }
}