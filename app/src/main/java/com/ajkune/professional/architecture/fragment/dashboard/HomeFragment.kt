package com.ajkune.professional.architecture.fragment.dashboard

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.adapters.CategoryAdapter
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.architecture.viewmodels.dashboard.HomeViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.HomeFragmentBinding
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    lateinit var binding : HomeFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    lateinit var categoryAdapter: CategoryAdapter

    var categories: MutableList<Category> = mutableListOf()

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
        initBaseFunctions()
        initRecyclerViewCategory()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onLoad() {

        viewModel.getActiveCategories()

        viewModel.categories.observe(this, Observer {
            if (it != null) {
                categories.addAll(it)
                binding.rvCategory.adapter?.notifyDataSetChanged()
            }
        })
    }

    override fun onError() {
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
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setData(categories)
        categoryAdapter.setHasStableIds(true)
        recyclerView.adapter = categoryAdapter

    }
}