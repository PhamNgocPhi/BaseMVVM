package com.example.basemvvm.utils.network

import android.content.Context
import com.example.basemvvm.utils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(val context: Context) : Interceptor {

    private val networkEvent: NetworkEvent = NetworkEvent

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        /*
         * We check if there is internet
         * available in the device. If not, pass
         * the networkState as NO_INTERNET.
         * */
        if (!isInternetAvailable(context)) {
            networkEvent.publish(NetworkState.NO_INTERNET)
        } else {
            when (response.code) {
                400 -> networkEvent.publish(NetworkState.BAD_REQUEST)
                401 -> networkEvent.publish(NetworkState.UNAUTHORISED)
                403 -> networkEvent.publish(NetworkState.FORBIDDEN)
                404 -> networkEvent.publish(NetworkState.NOT_FOUND)
            }
        }
        return response
    }
}