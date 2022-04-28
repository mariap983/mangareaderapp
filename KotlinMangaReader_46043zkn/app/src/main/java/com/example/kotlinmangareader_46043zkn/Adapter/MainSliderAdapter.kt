package com.example.kotlinmangareader_46043zkn.Adapter

import com.example.kotlinmangareader_46043zkn.Model.Banner
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class MainSliderAdapter(private val bannerList:List<Banner>): SliderAdapter() {
    override fun getItemCount(): Int {
        return bannerList.size
    }

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
        imageSlideViewHolder!!.bindImageSlide(bannerList[position].Link)
    }
}