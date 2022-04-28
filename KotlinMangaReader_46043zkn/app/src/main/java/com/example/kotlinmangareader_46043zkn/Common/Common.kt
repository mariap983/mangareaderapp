package com.example.kotlinmangareader_46043zkn.Common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.kotlinmangareader_46043zkn.Model.Comic
import com.example.kotlinmangareader_46043zkn.Retrofit.IComicAPI
import com.example.kotlinmangareader_46043zkn.Retrofit.RetrofitClient

object Common {

    var selected_comic: Comic?=null

    fun isConnectedToInternet(context: Context?): Boolean {
            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cm!=null)
        {
            if(Build.VERSION.SDK_INT < 23)
            {
                val ni = cm.activeNetworkInfo
                if(ni!=null)
                    return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI ||
                            ni.type == ConnectivityManager.TYPE_MOBILE)
            }
            else
            {
                val n = cm.activeNetwork
                if(n!=null)
                {
                    val nc = cm.getNetworkCapabilities(n)
                    return nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)

                }
            }
        }
        return false

    }

    val api:IComicAPI
    get() {
        val retrofit = RetrofitClient.instance
        return retrofit.create(IComicAPI::class.java)
    }
}