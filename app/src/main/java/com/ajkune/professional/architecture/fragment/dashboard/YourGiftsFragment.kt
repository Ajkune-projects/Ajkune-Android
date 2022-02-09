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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajkune.professional.R
import com.ajkune.professional.architecture.adapters.CategoryAdapter
import com.ajkune.professional.architecture.adapters.YourGiftsAdapter
import com.ajkune.professional.architecture.models.Gift
import com.ajkune.professional.architecture.viewmodels.dashboard.YourGiftsViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.YourGiftsFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class YourGiftsFragment : BaseFragment() {

    lateinit var binding : YourGiftsFragmentBinding

    lateinit var adapter : YourGiftsAdapter

    var gifts : ArrayList<Gift> = arrayListOf()

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = YourGiftsFragment()
    }

    private lateinit var viewModel: YourGiftsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.your_gifts_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.dashboardNavigationView?.visibility = BottomNavigationView.GONE
        viewModel = ViewModelProvider(this, viewModelFactory)[YourGiftsViewModel::class.java]
        initBaseFunctions()
        initRecyclerViewCategory()
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onLoad() {
        showLoader()
        viewModel.getListOfGifts()

        viewModel.gifts.observe(this, Observer {
            if (it != null) {
                hideLoader()
                gifts.clear()
                gifts.addAll(it)
                binding.rvYourGifts.adapter?.notifyDataSetChanged()

                if (gifts.isEmpty()){
                    binding.cvEmptyGifts.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onError() {

    }

    override fun onClickEvents() {
        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setToolbar() {

    }

    private fun initRecyclerViewCategory(){
        val recyclerView = binding.rvYourGifts
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = YourGiftsAdapter()
        adapter.setData(gifts)
        adapter.setHasStableIds(true)
        recyclerView.adapter = adapter

    }

}