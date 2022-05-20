package fr.mjtchoupe.deezer.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

public abstract class CallbackSearchArtist : Callback {
    companion object {
        private val TAG = "CallBackSearchArtist"
    }

    override fun onFailure(call: Call, e: IOException) {}
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            return;
        }
        val responseData = response.body!!.string()
        val gson = Gson();
        val data: SearchArtist = gson.fromJson(responseData, SearchArtist::class.java);
        println(responseData);

        Log.d(TAG, "info : $data")
        runOnUiThread(Runnable {
            fireResponseOk(data)
        })
       }

    abstract fun fireResponseOk(data: SearchArtist)
    private fun runOnUiThread(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task)
    }
}