package com.ajkune.professional.architecture.fragment.dashboard

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.adapters.AppointmentAdapter
import com.ajkune.professional.architecture.adapters.CommentsAdapter
import com.ajkune.professional.architecture.models.Offer
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.viewmodels.dashboard.ProductDetailsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.ProductDetailsFragmentBinding
import com.ajkune.professional.utilities.extensions.loadUrl
import com.ajkune.professional.utilities.helpers.BaseAccountManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class ProductDetailsFragment : BaseFragment() {

    lateinit var binding : ProductDetailsFragmentBinding

    var product : Product? = null

    var productId : Int = 0

    var isProduct : Boolean = false

    @Inject
    lateinit var baseAccountManager: BaseAccountManager

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    lateinit var commentsAdapter : CommentsAdapter

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

        showLoader()
        if (isProduct){
            viewModel.getProductById(productId)
        }else{
            viewModel.getOffersById(productId)
        }

        viewModel.products.observe(this , Observer {
            if (it != null) {
                hideLoader()
                product = it[0]
                initView()
                initRecyclerViewComments()
            }
        })

        viewModel.newComment.observe(this, Observer {
            if (it != null) {
                hideLoader()
                it[0].comments?.let { comments ->
                    commentsAdapter.setData(comments)
                }
                it[0].commentsOffer?.let { commentsOffer ->
                    commentsAdapter.setData(commentsOffer)
                }
                binding.etMessageTitle.setText("")
                binding.etMessageDescription.setText("")
                Toast.makeText(requireContext(), getString(R.string.comment_added),Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onError() {
        viewModel.error.observe(this, Observer {
            if (it != null) {
                hideLoader()
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
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

        binding.btnComment.setOnClickListener {
            if (binding.etMessageTitle.text.isNullOrBlank()){
                Toast.makeText(requireContext(), "Please write a title",Toast.LENGTH_LONG).show()
            }else if (binding.etMessageDescription.text.isNullOrBlank()){
                Toast.makeText(requireContext(), "Please write your comment",Toast.LENGTH_LONG).show()
            }else{
                showLoader()
                val title = binding.etMessageTitle.text.toString()
                val description = binding.etMessageDescription.text.toString()
                if (isProduct){
                    viewModel.addCommentForProduct(product?.id!!, title, description)
                }else{
                    viewModel.addCommentForOffer(product?.id!!, title, description)
                }

            }
        }
    }

    override fun setToolbar() {
    }

    private fun getData(){
        activity?.intent?.let {
            if (it.getStringExtra("PRODUCT_DETAILS") != null && it.getStringExtra("PRODUCT_DETAILS") != "") {
                productId = Gson().fromJson(it.getStringExtra("PRODUCT_DETAILS"), Product::class.java).id
                isProduct = true
            }
            if (it.getStringExtra("OFFERS_DETAILS") != null && it.getStringExtra("OFFERS_DETAILS") != "") {
                productId = Gson().fromJson(it.getStringExtra("OFFERS_DETAILS"), Product::class.java).id
                isProduct = false
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

            val current = baseAccountManager.language

            when (current.toString()) {
                "en_GB" -> {
                    binding.txtSpecificationDetails.text = product.descEn
                }
                "de" -> {
                    binding.txtSpecificationDetails.text = product.descDe
                }
                "fr" -> {
                    binding.txtSpecificationDetails.text = product.descFr
                }
                "it" -> {
                    binding.txtSpecificationDetails.text = product.descIt
                }
                else -> {
                    binding.txtSpecificationDetails.text = product.descEn
                }
            }
        }
    }

    private fun initRecyclerViewComments(){
        val recyclerView = binding.rvComments
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        commentsAdapter = CommentsAdapter()
        product?.comments?.let {
            commentsAdapter.setData(it)
        }
        product?.commentsOffer?.let {
            commentsAdapter.setData(it)
        }
        commentsAdapter.setHasStableIds(true)
        recyclerView.adapter = commentsAdapter

    }
}