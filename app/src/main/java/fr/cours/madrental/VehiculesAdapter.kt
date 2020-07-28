package fr.cours.madrental

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.cours.madrental.bdd.VehiculeDTO
import fr.cours.madrental.ws.VehiculeWs
import java.util.*
import kotlin.collections.ArrayList

class VehiculesAdapter(private var listVehiculeWs: MutableList<VehiculeWs>) : RecyclerView.Adapter<VehiculesAdapter.VehiculeViewHolder>()
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
    }

    override fun getItemCount(): Int = listVehiculeWs.size

    inner class VehiculeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {


        val textViewNameVehicule: TextView = itemView.findViewById(R.id.name_vehicule)
        val textViewPriceVehicule: TextView = itemView.findViewById(R.id.price_vehicule)
        val textViewCategoryVehicule: TextView = itemView.findViewById(R.id.category_vehicule)

        /**
         * Constructeur.
         */
        init {

            // listener :
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("textViewNameVehicule", textViewNameVehicule.text)
                intent.putExtra("textViewPriceVehicule", listVehiculeWs[adapterPosition].prixjournalierbase)
                intent.putExtra("textViewCategoryVehicule", listVehiculeWs[adapterPosition].categorieco2)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun updateListVehicule(vehicules: MutableList<VehiculeDTO>)
    {
        val newList: MutableList<VehiculeWs> = ArrayList()
        for (vehicule in vehicules)
        {
            newList.add(VehiculeWs(vehicule.name,vehicule.price,vehicule.category))
        }
        this.listVehiculeWs = newList
        notifyDataSetChanged()
    }

    fun updateList(vehicules: MutableList<VehiculeWs>)
    {
        this.listVehiculeWs = vehicules
        notifyDataSetChanged()
    }


}

