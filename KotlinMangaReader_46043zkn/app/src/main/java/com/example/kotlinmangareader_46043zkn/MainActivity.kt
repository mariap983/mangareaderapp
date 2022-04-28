package com.example.kotlinmangareader_46043zkn

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinmangareader_46043zkn.Adapter.MainSliderAdapter
import com.example.kotlinmangareader_46043zkn.Adapter.MyComicAdapter
import com.example.kotlinmangareader_46043zkn.Common.Common
import com.example.kotlinmangareader_46043zkn.Retrofit.IComicAPI
import com.example.kotlinmangareader_46043zkn.Service.PicassoImageLoadingService
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider

class MainActivity : AppCompatActivity() {

    internal var compositeDisposable = CompositeDisposable()
    internal lateinit var iComicAPI: IComicAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init API
        iComicAPI = Common.api

        Slider.init(PicassoImageLoadingService(this))

        //View setup
        recycler_comic.setHasFixedSize(true)
        recycler_comic.layoutManager = GridLayoutManager(this, 2)

        swipe_refresh.setColorSchemeResources( R.color.colorPrimary,android.R.color.holo_orange_dark,android.R.color.background_dark)
        swipe_refresh.setOnRefreshListener {
            if(Common.isConnectedToInternet(baseContext))
            {
                fetchBanner()
                fetchComic()
            }
            else
            {
                Toast.makeText(baseContext,"Please check your connection",Toast.LENGTH_SHORT).show()

            }
        }
        //Default, load first time
        swipe_refresh.post(Runnable {
            if(Common.isConnectedToInternet(baseContext))
            {
                fetchBanner()
                fetchComic()
            }
            else
            {
                Toast.makeText(baseContext,"Please check your connection",Toast.LENGTH_SHORT).show()

            }
        })


    }

    private fun fetchComic() {

        val dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please wait...")
            .build()
        if(swipe_refresh.isRefreshing)
            dialog.show()
        compositeDisposable.add(iComicAPI.comicList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({comicList ->
                    txt_comic.text = StringBuilder("NEW COMIC (")
                        .append(comicList.size)
                        .append(")")
                    recycler_comic.adapter = MyComicAdapter(baseContext,comicList)
                if(!swipe_refresh.isRefreshing)
                    dialog.dismiss()
                swipe_refresh.isRefreshing = false
            },

                {thr ->
                    Toast.makeText(baseContext,""+thr.message,Toast.LENGTH_SHORT).show()
                    if(!swipe_refresh.isRefreshing)
                        dialog.dismiss()
                    swipe_refresh.isRefreshing = false
                }))
    }

    private fun fetchBanner() {
        compositeDisposable.add(iComicAPI.bannerList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                banners -> banner_slider.setAdapter(MainSliderAdapter(banners))
            },

                {
                    Toast.makeText(baseContext,"Error while load banner",Toast.LENGTH_SHORT).show()
                }))
    }
}
