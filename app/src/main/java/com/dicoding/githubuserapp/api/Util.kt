package com.dicoding.githubuserapp.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread

object util {

    fun download(c : Context, url : String, response: (String) -> Unit) : Job = GlobalScope.launch(Dispatchers.IO) {
        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "Mozilla/5.0")
        client.addHeader("Authorization", "token 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")
        c.runOnUiThread{client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            )
            { Log.d("tag", "statuscode = $statusCode headers = ${headers?.joinToString()} responseBody = ${responseBody?.decodeToString()}")
                if (responseBody != null) {
                    val result = String(responseBody)
                    response.invoke(result)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) { Log.d("tag", "statuscode = $statusCode headers = ${headers?.joinToString()} responseBody = ${responseBody?.decodeToString()} error = $error", error)
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(c, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
        })}

    }

}