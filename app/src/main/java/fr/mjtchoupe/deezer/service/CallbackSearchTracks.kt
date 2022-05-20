package fr.mjtchoupe.deezer.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import fr.mjtchoupe.deezer.service.dataClasses.Album
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

public abstract class CallbackSearchTracks : Callback {
    companion object {
        private val TAG = "CallbackSearchTracks"
    }

    override fun onFailure(call: Call, e: IOException) {}
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            return;
        }
        val responseData = response.body!!.string()
        val gson = Gson();
        val data: Album = gson.fromJson(responseData, Album::class.java)
        println(responseData);

        Log.d(TAG, "info : $data")
        runOnUiThread(Runnable {
            fireResponseOk(data)
        })
       }

    abstract fun fireResponseOk(data: Album)
    private fun runOnUiThread(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task)
    }
}