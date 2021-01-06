package com.eju.architecture.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LifecycleOwner
import com.eju.architecture.application
import com.eju.architecture.livedata.UILiveData
import com.eju.architecture.observe
import timber.log.Timber

object NetworkManager {

    private var networkStateLiveData = UILiveData<NetworkState>()

    val networkState:NetworkState get() {
        return networkStateLiveData.value?:NetworkState.NONE
    }

    fun networkConnected()=networkState!=NetworkState.NONE

    fun init() {
        val connectivityManager= application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback=object:
            ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
//                Timber.i( "onAvailable: ")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
//                Timber.i( "onLost: ")
                onNetworkStateChanged(NetworkState.NONE)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
                    if(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)){
//                        Timber.i(  "onCapabilitiesChanged wifi: ${networkCapabilities}")
                        onNetworkStateChanged(NetworkState.WIFI)
                    }else{
//                        Timber.i(  "onCapabilitiesChanged mobile: ${networkCapabilities}")
                        onNetworkStateChanged(NetworkState.MOBILE)
                    }
                }

            }
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            connectivityManager.registerDefaultNetworkCallback(callback)
        }else{
            connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(),callback)
        }
    }

    private fun onNetworkStateChanged(newNetworkState: NetworkState){
        if(newNetworkState!= networkState){
            val oldNetworkState= networkState
            Timber.i("onNetworkStateChanged ${oldNetworkState}  ${newNetworkState}")
            this.networkStateLiveData.changeValue(newNetworkState)
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner,onNetworkStateChanged:(Boolean,NetworkState)->Unit){
        lifecycleOwner.observe(networkStateLiveData){
            onNetworkStateChanged.invoke(networkConnected(),it)
        }
    }

}

enum class NetworkState{
    NONE,
    WIFI,
    MOBILE,
}