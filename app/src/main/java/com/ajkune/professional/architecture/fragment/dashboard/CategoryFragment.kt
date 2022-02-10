package com.ajkune.professional.architecture.fragment.dashboard

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.adapters.AllCategoryAdapter
import com.ajkune.professional.architecture.adapters.CategoryAdapter
import com.ajkune.professional.architecture.models.Category
import com.ajkune.professional.architecture.viewmodels.dashboard.CategoryViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.CategoryFragmentBinding
import javax.inject.Inject

class CategoryFragment : BaseFragment() , AllCategoryAdapter.Listener{

    lateinit var binding : CategoryFragmentBinding

    lateinit var allCategoryAdapter: AllCategoryAdapter

    private var categories : MutableList<Category> = mutableListOf()

    var isFirstTime : Boolean = true

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.category_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[CategoryViewModel::class.java]
        initBaseFunctions()
        initRecyclerViewCategory()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onLoad() {
        if (isFirstTime){
            viewModel.getActiveCategories()
            isFirstTime = false
        }

        viewModel.categories.observe(this, Observer {
            if (it != null) {
                hideLoader()
                categories.addAll(it)
                binding.rvCategories.adapter?.notifyDataSetChanged()
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
        binding.imgClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setToolbar() {

    }

    private fun initRecyclerViewCategory(){
        val recyclerView = binding.rvCategories
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        allCategoryAdapter = AllCategoryAdapter(this)
        allCategoryAdapter.setData(categories)
        allCategoryAdapter.setHasStableIds(true)
        recyclerView.adapter = allCategoryAdapter

    }

    override fun onCategoryClicked(category: Category, position: Int) {
        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToHomeFragment(0,0,"",category.id))
    }
}