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
import com.ajkune.professional.architecture.activities.MainActivity
import com.ajkune.professional.architecture.activities.ProductDetailsActivity
import com.ajkune.professional.architecture.adapters.CategoryAdapter
import com.ajkune.professional.architecture.adapters.ProductsAdapter
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.viewmodels.dashboard.HomeViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.HomeFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), CategoryAdapter.Listener, ProductsAdapter.Listener {

    lateinit var binding : HomeFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    lateinit var categoryAdapter: CategoryAdapter

    lateinit var productsAdapter: ProductsAdapter

    var categories: MutableList<Category> = mutableListOf()

    var products: MutableList<Product> = mutableListOf()

    var productsByCategoryId: MutableList<Product> = mutableListOf()

    var isFirstTime : Boolean = true

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.VISIBLE
        initBaseFunctions()
        initRecyclerViewProducts(products)
        initRecyclerViewCategory()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onLoad() {

        if (isFirstTime){
            showLoader()
            viewModel.getActiveCategories()
            viewModel.getActiveProducts()
            isFirstTime = false
        }

        viewModel.categories.observe(this, Observer {
            if (it != null) {
                hideLoader()
                val allCategory = Category()
                allCategory.name = "All Products"
                categories.add(0,allCategory)
                categories.addAll(it)
                binding.rvCategory.adapter?.notifyDataSetChanged()
            }
        })

        viewModel.products.observe(this, Observer {
            if (it != null) {
                hideLoader()
                products.addAll(it)
                binding.rvProducts.adapter?.notifyDataSetChanged()
                initRecyclerViewProducts(it)
            }
        })

        viewModel.productsByCategoryId.observe(this, Observer {
            if (it != null) {
                hideLoader()
                productsByCategoryId.clear()
                productsByCategoryId.addAll(it)
                initRecyclerViewProducts(productsByCategoryId)
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
    }

    override fun setToolbar() {
    }

    private fun initRecyclerViewCategory(){
        val recyclerView = binding.rvCategory
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(this)
        categoryAdapter.setData(categories)
        categoryAdapter.setHasStableIds(true)
        recyclerView.adapter = categoryAdapter

    }

    private fun initRecyclerViewProducts(products: List<Product>){
        val recyclerView = binding.rvProducts
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        productsAdapter = ProductsAdapter(this)
        productsAdapter.setData(products)
        productsAdapter.setHasStableIds(true)
        recyclerView.adapter = productsAdapter

    }

    override fun onCategoryClicked(category: Category, position: Int) {
        showLoader()
        if (position == 0 ){
            viewModel.getActiveProducts()
        }else{
            viewModel.getProductsByCategoryId(category.id)
        }
    }

    override fun onProductClicked(product: Product) {
        val intent = Intent(requireActivity(), ProductDetailsActivity::class.java)
        intent.putExtra("PRODUCT_DETAILS", Gson().toJson(product))
        startActivity(intent)
    }
}