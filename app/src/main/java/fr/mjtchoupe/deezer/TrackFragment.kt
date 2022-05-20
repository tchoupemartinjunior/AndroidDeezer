package fr.mjtchoupe.deezer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import fr.mjtchoupe.deezer.adapter.RecyclerTrackAdapter
import fr.mjtchoupe.deezer.databinding.FragmentThirdBinding
import fr.mjtchoupe.deezer.service.*
import fr.mjtchoupe.deezer.service.dataClasses.Album

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class TrackFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    companion object {
        private val TAG = "TrackFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.recyclerViewTrack.layoutManager = LinearLayoutManager(context)
        Log.d(TAG,"onCreateView")

        val idTrack = arguments?.get("idTrack")
        val nameTrack= arguments?.get("nameTrack")
        Log.d(TAG, "idTrackList : ${idTrack.toString()}")
        //val service = DeezerService()
        DeezerService.searchTracks(idTrack.toString(), object: CallbackSearchTracks() {
            override fun fireResponseOk(album: Album) {
                try {
                    Glide.with(binding.trackListCover.context).load(album.cover_medium)
                        .into(binding.trackListCover)

                } catch (e: Throwable) {
                    e.printStackTrace()
                    Log.e(TAG, "Glide", e)
                }
                val dateAndSongs =
                    "${album.release_date} - ${album.nb_tracks} chansons"
                binding.info.text = dateAndSongs
                binding.trackListName.text = album.title
                (activity as AppCompatActivity).supportActionBar?.title = nameTrack.toString()
                binding.recyclerViewTrack.adapter = RecyclerTrackAdapter(album) }

        })

        return binding.root

    }
    override fun onPause() {
        super.onPause()
        if(DeezerService.mediaPlayer.isPlaying)
            DeezerService.mediaPlayer.stop()
    }
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }
}