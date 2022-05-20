package fr.mjtchoupe.deezer.adapter

import android.app.ActionBar
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import fr.mjtchoupe.deezer.R
import fr.mjtchoupe.deezer.databinding.RecyclerviewTrackItemBinding
import fr.mjtchoupe.deezer.service.DeezerService
import fr.mjtchoupe.deezer.service.dataClasses.Album


class RecyclerTrackAdapter(private val trackList: Album) :
    RecyclerView.Adapter<RecyclerTrackAdapter.TrackViewHolder>() {

    lateinit var context: Context
    inner class TrackViewHolder(val binding: RecyclerviewTrackItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private const val TAG = "RecyclerTrackAdapter"
        private var currMediaPlayer : MediaPlayer = MediaPlayer()
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackViewHolder {
        val binding =
            RecyclerviewTrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context

        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val data = trackList.tracks.data[position]
        holder.binding.trackTitle.text = data.title
        holder.binding.root.setOnClickListener {
            Log.e(TAG, data.title.toString())
            Toast.makeText(context, "Clicked id ${data.title}", Toast.LENGTH_SHORT).show()
        }
        val url = data.preview // your URL here
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepare()
        }
        holder.binding.playButton.setOnClickListener {
            if(mediaPlayer != currMediaPlayer && currMediaPlayer.isPlaying ){
                currMediaPlayer.stop()
                currMediaPlayer.prepareAsync()
            }
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                holder.binding.playButton.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24)
            } else {
                mediaPlayer.start()
                holder.binding.playButton.setBackgroundResource(R.drawable.ic_baseline_pause_24)
            }

            currMediaPlayer = mediaPlayer
            DeezerService.mediaPlayer = mediaPlayer
        }
    }

    //renvoyer la taille de cette donnée
    override fun getItemCount(): Int {
        return trackList.tracks.data.size
    }


}