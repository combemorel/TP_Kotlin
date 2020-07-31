package fr.cours.madrental

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.cours.madrental.fragments.DetailFragment
import fr.cours.madrental.ws.ReseauHelper
import fr.cours.madrental.ws.RetrofitSingleton
import fr.cours.madrental.ws.VehiculeWs
import fr.cours.madrental.ws.WSInterface
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehiculesAdapter(private var listVehiculeWs: MutableList<VehiculeWs>, private var mainActivity: MainActivity) : RecyclerView.Adapter<VehiculesAdapter.VehiculeViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder
    {
        val viewCourse = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicule, parent, false)
        return VehiculeViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int)
    {
        holder.textViewNameVehicule.text = listVehiculeWs[position].nom
        holder.textViewPriceVehicule.text = listVehiculeWs[position].prixjournalierbase.toString()+" € / jour"
        holder.textViewCategoryVehicule.text = "Catégorie CO2 :"+listVehiculeWs[position].categorieco2
        val path: String = "http://s519716619.onlinehome.fr/exchange/madrental/images/"+listVehiculeWs[position].image
        Log.d("TAG","image => $path")
        Picasso.get()
            .load("$path")
            .fit()
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = listVehiculeWs.size

    inner class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewNameVehicule: TextView = itemView.findViewById(R.id.name_fragment)
        val textViewPriceVehicule: TextView = itemView.findViewById(R.id.price_fragment)
        val textViewCategoryVehicule: TextView = itemView.findViewById(R.id.category_fragment)
        val imageView: ImageView = itemView.findViewById(R.id.img)


        /**
         * Constructeur.
         */
        init {

            itemView.setOnClickListener {
                val frameLayout = mainActivity.conteneur_fragment
                if(frameLayout != null)
                {
                    val fragment = DetailFragment()

                    val transaction: FragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.conteneur_fragment, fragment, "exemple2")
                    transaction.commit()

                    val name = textViewNameVehicule.text
                    val price = listVehiculeWs[adapterPosition].prixjournalierbase
                    val image = listVehiculeWs[adapterPosition].image
                    val category = listVehiculeWs[adapterPosition].categorieco2

                    val preferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)
                    val editor = preferences.edit()

                    editor.putString("name", name as String?)
                    editor.putInt("price", price)
                    editor.putString("category", category)
                    editor.putString("image", image)
                    editor.apply()

                    val bundle = Bundle()
                    bundle.putString("name", "Nom : $name")
                    bundle.putString("price", "Prix : $price € / jour")
                    bundle.putString("category", "Catégorie CO2 : $category")
                    bundle.putString("img", "$image")
                    fragment.arguments = bundle

                } else
                {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra("textViewNameVehicule", textViewNameVehicule.text)
                    intent.putExtra("textViewPriceVehicule", listVehiculeWs[adapterPosition].prixjournalierbase)
                    intent.putExtra("img", listVehiculeWs[adapterPosition].image)
                    intent.putExtra("textViewCategoryVehicule", listVehiculeWs[adapterPosition].categorieco2)
                    Log.d("TAG","image => "+listVehiculeWs[adapterPosition].image)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    fun updateList(vehicules: MutableList<VehiculeWs>)
    {
        this.listVehiculeWs = vehicules
        notifyDataSetChanged()
    }


}

