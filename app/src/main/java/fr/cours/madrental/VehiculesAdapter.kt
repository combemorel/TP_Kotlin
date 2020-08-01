package fr.cours.madrental

import android.content.Intent
import android.os.Bundle
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
import fr.cours.madrental.ws.VehiculeWs
import kotlinx.android.synthetic.main.activity_main.*

class VehiculesAdapter(private var listVehiculeWs: MutableList<VehiculeWs>,
                       private var mainActivity: MainActivity) : RecyclerView.Adapter<VehiculesAdapter.VehiculeViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculeViewHolder
    {
        val viewCourse = LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_vehicule, parent, false)
        return VehiculeViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: VehiculeViewHolder, position: Int)
    {
        val textPrice =  listVehiculeWs[position].prixjournalierbase.toString()+" € / jour"
        val textCategory =  "Catégorie CO2 :"+listVehiculeWs[position].categorieco2

        holder.textViewNameVehicule.text = listVehiculeWs[position].nom
        holder.textViewPriceVehicule.text = textPrice
        holder.textViewCategoryVehicule.text = textCategory

        val path: String = "http://s519716619.onlinehome.fr/exchange/madrental/images/"+listVehiculeWs[position].image

        Picasso.get()
            .load(path)
            .fit()
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = listVehiculeWs.size

    fun updateList(vehicules: MutableList<VehiculeWs>)
    {
        this.listVehiculeWs = vehicules
        notifyDataSetChanged()
    }

    inner class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewNameVehicule: TextView = itemView.findViewById(R.id.name_fragment)
        val textViewPriceVehicule: TextView = itemView.findViewById(R.id.price_fragment)
        val textViewCategoryVehicule: TextView = itemView.findViewById(R.id.category_fragment)
        val imageView: ImageView = itemView.findViewById(R.id.img)

        init
        {
            itemView.setOnClickListener {

                val frameLayout = mainActivity.conteneur_fragment

                if(frameLayout != null)
                {
                    val fragment = DetailFragment()

                    val transaction: FragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.conteneur_fragment, fragment, "vehicule_fragment")
                    transaction.commit()

                    val name = listVehiculeWs[adapterPosition].nom
                    val price = listVehiculeWs[adapterPosition].prixjournalierbase
                    val image = listVehiculeWs[adapterPosition].image
                    val category = listVehiculeWs[adapterPosition].categorieco2

                    val preferences = PreferenceManager.getDefaultSharedPreferences(itemView.context)
                    val editor = preferences.edit()

                    editor.putString("name", name)
                    editor.putInt("price", price)
                    editor.putString("category", category)
                    editor.putString("image", image)
                    editor.apply()

                    val bundle = Bundle()
                    bundle.putString("name", "Nom : $name")
                    bundle.putString("price", "Prix : $price € / jour")
                    bundle.putString("category", "Catégorie CO2 : $category")
                    bundle.putString("img", image)
                    fragment.arguments = bundle

                } else
                {
                    val intent = Intent(itemView.context, DetailActivity::class.java)

                    intent.putExtra("textViewNameVehicule", textViewNameVehicule.text)
                    intent.putExtra("textViewPriceVehicule", listVehiculeWs[adapterPosition].prixjournalierbase)
                    intent.putExtra("img", listVehiculeWs[adapterPosition].image)
                    intent.putExtra("textViewCategoryVehicule", listVehiculeWs[adapterPosition].categorieco2)

                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}

