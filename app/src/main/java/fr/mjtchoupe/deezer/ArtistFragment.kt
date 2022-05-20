package fr.mjtchoupe.deezer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mjtchoupe.deezer.databinding.FragmentArtistBinding
import fr.mjtchoupe.deezer.service.CallbackSearchArtist
import fr.mjtchoupe.deezer.service.DeezerService
import fr.mjtchoupe.deezer.service.SearchAlbum
import fr.mjtchoupe.deezer.adapter.RecyclerArtistAdapter
import fr.mjtchoupe.deezer.service.SearchArtist

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    companion object {
        private val TAG = "ArtistsFragment"
        //lateinit var applicationContext: Context private set

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView");
        val artistName = arguments?.get("artist")
        Log.d(TAG, "search : ${artistName.toString()}")
        binding.recyclerview.layoutManager = LinearLayoutManager(Fragment().context)

        DeezerService.searchArtist(artistName.toString(),  object: CallbackSearchArtist() {
            override fun fireResponseOk(artist: SearchArtist) {
                Log.d(TAG, "adapter")
                binding.recyclerview.adapter = RecyclerArtistAdapter(artist)
            }
        })
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}