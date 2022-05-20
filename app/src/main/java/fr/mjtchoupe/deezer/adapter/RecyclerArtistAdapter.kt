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
import fr.mjtchoupe.deezer.databinding.RecyclerviewArtistItemBinding
import fr.mjtchoupe.deezer.service.SearchAlbum
import fr.mjtchoupe.deezer.service.SearchArtist
import java.math.RoundingMode
import java.text.DecimalFormat


    class RecyclerArtistAdapter(private val artistList: SearchArtist) :
        RecyclerView.Adapter<RecyclerArtistAdapter.ArtistViewHolder>() {

        lateinit var context: Context


        companion object {
            private const val TAG = "RecyclerArtistAdapter"
        }

        //pour changer fragment
        private var listener: (() -> Unit)? = null


        //il faut binding sur layout qu'on a besoin d'afficher (ici c'est le layout recyclerview_artist_item) => donc type de binding = RecyclerviewArtistItemBinding
        inner class ArtistViewHolder(val binding: RecyclerviewArtistItemBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
            val binding = RecyclerviewArtistItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            context = parent.context

            return ArtistViewHolder(binding)
        }

        //pour charger les données récupérées
        override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
            val data = artistList.data[position]

            try {
                Glide.with(holder.binding.imageArtist.context).load(data.picture_medium)
                    .into(holder.binding.imageArtist)

            } catch (e: Throwable) {
                e.printStackTrace()
                Log.e(TAG, "Glide", e)
            }
            holder.binding.artistName.text = data.name
            val decimal = DecimalFormat("#.##")
            decimal.roundingMode = RoundingMode.CEILING
            val fans = decimal.format(data.nb_fan) + " fans"
            holder.binding.nbFans.text = fans
            Log.e(TAG, "${data.name} with ${data.nb_fan} fans ")
            //communi = activity  as  Communicator
            holder.binding.root.setOnClickListener {
                Log.d(TAG, data.id.toString())
                Toast.makeText(context, "Clicked id ${data.id}", Toast.LENGTH_SHORT).show()
                val idAlbum = data.id.toString()
                val nameAlbum = data.name
                val args = bundleOf("idAlbum" to idAlbum, "nameAlbum" to nameAlbum)
                //Log.d(TAG, it.findNavController().currentDestination.toString())
                it.findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, args)
            }
        }

        //renvoyer la taille de cette donnée
        override fun getItemCount(): Int {
            return artistList.data.size
        }
    }


