package com.ajkune.professional.architecture.fragment.dashboard

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.viewmodels.dashboard.ProductDetailsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.ProductDetailsFragmentBinding
import com.ajkune.professional.utilities.extensions.loadUrl
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment() {

    lateinit var binding : ProductDetailsFragmentBinding

    var product : Product? = null

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }

    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_details_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[ProductDetailsViewModel::class.java]
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
        getData()
        initBaseFunctions()
    }

    override fun onLoad() {
        initView()
    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.btnBack.setOnClickListener {
           requireActivity().finish()
        }

        binding.btnBuyNow.setOnClickListener {
            val url = product?.wpProductUrl
            val uri: Uri =
                Uri.parse(url) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun setToolbar() {
    }

    private fun getData(){
        activity?.intent?.let {
            if (it.getStringExtra("PRODUCT_DETAILS") != null && it.getStringExtra("PRODUCT_DETAILS") != "") {
                product = Gson().fromJson(it.getStringExtra("PRODUCT_DETAILS"), Product::class.java)
            }
        }
    }

    private fun initView(){
        product?.let { product ->
            binding.txtProductTitleToolbar.text = product.name
            binding.txtProductName.text = product.name
            binding.imgProductPhoto.loadUrl(product.image)
            binding.txtPrice.text = requireContext().getString(R.string.price, product.price)
            binding.ratingBar2.rating = product.rating.toFloat()
        }


    }
}