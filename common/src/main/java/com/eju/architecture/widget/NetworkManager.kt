package com.eju.architecture.widget

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

    var networkState = NetworkState.NONE

    fun networkConnected()=
        networkState != NetworkState.NONE

    fun init() {
        val connectivityManager= application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val callback=object:
            ConnectivityManager.NetworkCallback(){
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
//                Timber.i( "onAvailable: ${Thread.currentThread().id}")
                val networkCapabilities=connectivityManager.getNetworkCapabilities(network)
                networkCapabilities?.let { networkCapabilities->
                    onNetworkStateChanged(
                        getConnectedNetworkState(
                            networkCapabilities
                        )
                    )
                }?: onNetworkStateChanged(
                    NetworkState.OTHER
                )
            }

            override fun onLost(network: Network) {
                super.onLost(network)
//                Timber.i( "onLost: ${Thread.currentThread().id}")
                onNetworkStateChanged(
                    NetworkState.NONE
                )
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
//                Timber.i( "onCapabilitiesChanged:  ${Thread.currentThread().id}")
                onNetworkStateChanged(
                    getConnectedNetworkState(
                        networkCapabilities
                    )
                )
            }
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            connectivityManager.registerDefaultNetworkCallback(callback)
        }else{
            connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(),callback)
        }
    }

    private fun getConnectedNetworkState(networkCapabilities: NetworkCapabilities): NetworkState {
        return when{
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkState.WIFI
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkState.MOBILE
            else -> NetworkState.OTHER
        }
    }



    private fun onNetworkStateChanged(newNetworkState: NetworkState){
        if(newNetworkState!= networkState){
            val oldNetworkState=
                networkState
            Timber.i("onNetworkStateChanged ${oldNetworkState}  ${newNetworkState}")
            networkState =newNetworkState
            networkStateLiveData.changeValue(newNetworkState)
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner,onNetworkStateChanged:(Boolean, NetworkState)->Unit){
        lifecycleOwner.observe(networkStateLiveData){
            onNetworkStateChanged.invoke(networkConnected(),it)
        }
    }

}

enum class NetworkState{
    NONE,
    WIFI,
    MOBILE,
    OTHER,
}