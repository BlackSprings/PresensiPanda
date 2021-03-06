package com.presensi.panda.helper

import android.content.Context
import android.util.Log
import com.presensi.panda.models.Auth
import com.presensi.panda.network.Reference
import com.presensi.panda.utils.SharedPrefManager
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.lang.Exception

internal class AuthInterceptor(context: Context) : Interceptor {
    var context = context
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPrefManager = SharedPrefManager.getInstance(context)
        val currentToken = sharedPrefManager.auth.token
        var originalRequest = chain.request()
        val response : Response = chain.proceed(chain.request())
        val responseBody : String = response.peekBody(2048).string() //its weird

        try {
            if(response.isSuccessful){
                if(responseBody.contains("code")){
                    val server = if(sharedPrefManager.server.isEmpty()) Reference.sipanda else sharedPrefManager.server
                    val refreshTokenUrl = server+Reference.refreshUrl
                    Log.d("onIntercept","refreshTokenUrl: ${sharedPrefManager.server}")
                    val jsonObject = JSONObject(responseBody)
                    val code = jsonObject.optInt("code")
                    if(code == 401){
                        val refreshTokenRequest =
                            originalRequest
                                .newBuilder()
                                .get()
                                .url(refreshTokenUrl)
                                .addHeader("Authorization","Bearer ${currentToken}")
                                .build()
                        val refreshResponse = chain.proceed(refreshTokenRequest)
                        Log.d("onResponseCode","refreshResponse: $refreshTokenRequest")
                        val refreshResponseBody = refreshResponse.peekBody(2048).string() //its super weird
                        if(refreshResponse.isSuccessful){
                            if(refreshResponseBody.contains("status")){
                                val jsonObjectRefresh = JSONObject(refreshResponseBody)
                                val token = jsonObjectRefresh.optString("token")
                                val token_type = jsonObjectRefresh.optString("token_type")
                                val expires_in = jsonObjectRefresh.optInt("expires_in")

                                sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_AUTH)

                                var auth = Auth(
                                    token,
                                    expires_in,
                                    token_type,
                                )
                                sharedPrefManager.saveAuth(auth)
                                originalRequest = originalRequest.newBuilder().header("Authorization","Bearer ${token}").build()
                                return chain.proceed(originalRequest)
                            }
                        }
                        Log.d("refreshToken","onResponseRefreshToken ${refreshResponse.peekBody(2048).string()}")
                        Log.i("info","kie kadu refresh token")
                    }
                }
                return response
            }
        }catch (e: Exception){
            Log.e("interceptor","onInterceptorError: ${e.message}")
        }
        return response
    }
}