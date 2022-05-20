package fr.mjtchoupe.deezer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import fr.mjtchoupe.deezer.adapter.RecyclerAlbumAdapter
import fr.mjtchoupe.deezer.databinding.FragmentSecondBinding
import fr.mjtchoupe.deezer.service.CallbackSearchAlbum
import fr.mjtchoupe.deezer.service.DeezerService
import fr.mjtchoupe.deezer.service.SearchAlbum

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    companion object {
        private val TAG = "AlbumFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.recyclerviewAlbum.layoutManager = LinearLayoutManager(context)
        Log.d(TAG,"onCreateView")

        val idAlbum = arguments?.get("idAlbum")
        val nameAlbum = arguments?.get("nameAlbum")
        Log.d(TAG,"idAlbum : ${idAlbum.toString()}")
        //val service = DeezerService()
        DeezerService.searchAlbum(idAlbum.toString(), object: CallbackSearchAlbum() {
            override fun fireResponseOk(album: SearchAlbum) {
                Log.d(TAG,"adapter")
                binding.recyclerviewAlbum.adapter = RecyclerAlbumAdapter(album)
                (activity as AppCompatActivity).supportActionBar?.title = nameAlbum.toString()
            }

        })
        /*requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
           findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/

        return binding.root

    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}