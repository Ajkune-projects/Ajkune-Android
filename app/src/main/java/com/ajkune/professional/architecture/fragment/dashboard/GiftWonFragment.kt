package com.ajkune.professional.architecture.fragment.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ajkune.professional.R
import com.ajkune.professional.architecture.activities.DashboardActivity
import com.ajkune.professional.architecture.models.Gift
import com.ajkune.professional.architecture.models.Product
import com.ajkune.professional.architecture.viewmodels.dashboard.GiftWonViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.GiftWonFragmentBinding
import com.ajkune.professional.utilities.extensions.loadUrl
import com.google.gson.Gson
import javax.inject.Inject

class GiftWonFragment : BaseFragment() {

    lateinit var binding : GiftWonFragmentBinding

    var gift : Gift? = null

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    companion object {
        fun newInstance() = GiftWonFragment()
    }

    private lateinit var viewModel: GiftWonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.gift_won_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory)[GiftWonViewModel::class.java]
        getData()
        initBaseFunctions()
    }

    override fun onLoad() {
        gift?.imageUrl?.let {
            binding.imgGift.loadUrl(it)
        }
        val email = "ajkune.professional@gmail.com"
        binding.txtGiftName.text = getString(R.string.gift_added_details, gift?.title, email)
    }

    override fun onError() {
    }

    override fun onClickEvents() {
        binding.btnDone.setOnClickListener {
            restartActivity()
        }
    }

    override fun setToolbar() {
    }

    private fun getData() {
        arguments?.let { bundle ->
            GiftWonFragmentArgs.fromBundle(bundle).let {
                if (it.gift != ""){
                    gift = Gson().fromJson(it.gift, Gift::class.java)
                }

            }
        }
    }

    private fun restartActivity() {
        val intent = requireActivity().intent
        startActivity(intent)
        requireActivity().finish()
    }
}