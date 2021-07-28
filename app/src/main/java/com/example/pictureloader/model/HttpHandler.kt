package com.example.pictureloader.model

import android.util.Log
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.security.auth.callback.Callback

object HttpHandler {
    const val GET : String = "GET"

    fun requestGET(url: String, callback: (String)->Unit) = CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = GET
        val responseCode = con.responseCode
        Log.i("RASPBERRY","Response Code :: $responseCode")
        if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
            val `in` = BufferedReader(InputStreamReader(con.inputStream))
            var inputLine: String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            `in`.close()
            Log.i("RASPBERRY","Response $response")
            callback(response.toString())
        }
    }
}