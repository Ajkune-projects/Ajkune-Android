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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ajkune.professional.R
import com.ajkune.professional.architecture.viewmodels.dashboard.LuckyWheelViewModel
import com.ajkune.professional.base.fragment.BaseFragment
import com.ajkune.professional.base.viewmodel.AjkuneViewModelFactory
import com.ajkune.professional.databinding.LuckyWheelFragmentBinding
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.WheelItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject
import android.graphics.Bitmap
import java.io.IOException
import java.net.URL
import android.os.StrictMode





class LuckyWheelFragment : BaseFragment() {

    lateinit var binding : LuckyWheelFragmentBinding

    @Inject
    lateinit var viewModelFactory: AjkuneViewModelFactory

    private val lw: LuckyWheel? = null
    var wheelItems: ArrayList<WheelItem>? = null

    var giftIds : MutableList<Int> = mutableListOf()

    var randomGiftId : Int = 0

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
        //generateWheelItems()
        initBaseFunctions()
    }

    override fun onLoad() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
       val colorList = arrayListOf("#fc6c6c", "#00E6FF", "#F00E6F", "#8c93a9", "#a8466f","#37474f","#FF03DAC5", "#FF6200EE",
           "#ebf0ff","#bfeea3", "#fff493", "#e23eaf","#F03939")
        showLoader()
        viewModel.getListOfGifts()
        viewModel.gifts.observe(this, Observer {
            if (it != null) {
                hideLoader()
                binding.btnPlay.visibility = View.VISIBLE
                wheelItems = ArrayList<WheelItem>()
                it.forEach { gift ->
                    var imageBitmap : Bitmap? = null
                    giftIds.add(gift.id)
                    try {
                        val url = URL(gift.imageUrl)
                        imageBitmap = BitmapFactory.decodeStream(url.openStream())

                    } catch (e: IOException) {
                        println(e)
                    }
                    wheelItems?.add( WheelItem(Color.parseColor(colorList.random()),  getResizedBitmap(imageBitmap!!, 100), gift.title))
                }
                binding.lwv.addWheelItems(wheelItems)
                val randomIndex: Int = ThreadLocalRandom.current().nextInt(giftIds.size)
                randomGiftId = giftIds[randomIndex]
                binding.lwv.setTarget(randomGiftId)
                binding.lwv.visibility = View.VISIBLE
            }
        })

        viewModel.successGiftAdded.observe(this, Observer {
            if (it != null) {
                hideLoader()
                findNavController().popBackStack()
            }
        })


        binding.lwv.setLuckyWheelReachTheTarget {
            viewModel.addGiftFromSpinner(randomGiftId)
        }
    }

    override fun onError() {

    }

    override fun onClickEvents() {
        binding.btnPlay.setOnClickListener {
            binding.lwv.rotateWheelTo(randomGiftId)
        }

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun setToolbar() {

    }


    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}