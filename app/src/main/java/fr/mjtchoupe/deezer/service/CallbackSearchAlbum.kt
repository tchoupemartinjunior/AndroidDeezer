package fr.mjtchoupe.deezer.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import fr.mjtchoupe.deezer.MainActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

public abstract class CallbackSearchAlbum : Callback {
    companion object {
        private val TAG = "CallbackSearchAlbum"
    }

    override fun onFailure(call: Call, e: IOException) {}
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            return;
        }
        val responseData = response.body!!.string()
        val gson = Gson();
        val data: SearchAlbum = gson.fromJson(responseData, SearchAlbum::class.java);
        println(responseData);

        Log.d(TAG, "info : $data")
        runOnUiThread(Runnable {
            fireResponseOk(data)
        })
       }

    abstract fun fireResponseOk(data: SearchAlbum)
    private fun runOnUiThread(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task)
    }
}