package fr.mjtchoupe.deezer.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.mjtchoupe.deezer.R
import fr.mjtchoupe.deezer.databinding.RecyclerviewAlbumItemBinding
import fr.mjtchoupe.deezer.databinding.RecyclerviewArtistItemBinding
import fr.mjtchoupe.deezer.service.SearchAlbum
import java.math.RoundingMode
import java.text.DecimalFormat




class RecyclerAlbumAdapter(private val albumList: SearchAlbum) :
    RecyclerView.Adapter<RecyclerAlbumAdapter.AlbumViewHolder>() {

    companion object {
        private const val TAG = "RecyclerAlbumAdapter"
    }
    lateinit var context: Context
    inner class AlbumViewHolder(val binding: RecyclerviewAlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        val binding =
            RecyclerviewAlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context

        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val data = albumList.data[position]
        try {
            Glide.with(holder.binding.albumCover.context).load(data.cover_medium)
                .into(holder.binding.albumCover)

        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e(TAG, "Glide", e)
        }
        holder.binding.albumTitle.text = data.title
        val date = "Release date: " + data.release_date
        holder.binding.releaseDate.text = date
        Log.e(TAG, "Title ${data.title} realease date: ${date}")
        holder.binding.root.setOnClickListener {
            Log.d(TAG,data.id.toString())
            val idTrack = data.id.toString()
            val nameTrack = data.title
            val args = bundleOf("idTrack" to idTrack, "nameTrack" to nameTrack)
            it.findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment,args)

        }
    }

    override fun getItemCount(): Int {
        return albumList.data.size
    }
}