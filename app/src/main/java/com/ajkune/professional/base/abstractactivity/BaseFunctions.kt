package com.ajkune.professional.base.abstractactivity

interface BaseFunctions {
    fun onLoad()
    fun onError()
    fun onClickEvents()
    fun setToolbar()


    fun initBaseFunctions(){
        onLoad()
        onError()
        onClickEvents()
        setToolbar()
    }
}