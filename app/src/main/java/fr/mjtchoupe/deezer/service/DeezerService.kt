package fr.mjtchoupe.deezer.service

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.internal.ContextUtils.getActivity
import okhttp3.*
import okio.IOException
import java.net.URLEncoder


object DeezerService {
    var mediaPlayer: MediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    fun searchArtist(name:String, callback: Callback) {
        val client = OkHttpClient()
        val url = "https://api.deezer.com/search/artist?q=" + URLEncoder.encode(name, "UTF-8");
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(callback)
    }

    fun searchAlbum(id:String, callback: Callback) {
        val client = OkHttpClient()
        val url = "https://api.deezer.com/artist/"+ URLEncoder.encode(id,"UTF-8")+"/albums";
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(callback)
    }

    fun searchTracks(idAlbum:String, callback: Callback) {
        val client = OkHttpClient()
        val url = "https://api.deezer.com/album/"+ URLEncoder.encode(idAlbum,"UTF-8")
        Log.d("TrackFragment",url)
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(callback)
    }
}
