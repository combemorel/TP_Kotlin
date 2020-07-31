package fr.cours.madrental.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.cours.madrental.R
import kotlinx.android.synthetic.main.item_vehicule.*


class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.item_vehicule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)

        val arguments = requireArguments()
        val name = arguments.getString("name")
        val price = arguments.getString("price")
        val image = arguments.getString("img")
        val category = arguments.getString("category")

        Picasso.get()
            .load("http://s519716619.onlinehome.fr/exchange/madrental/images/$image")
            .fit()
            .centerCrop()
            .into(img)

        name_fragment.text = name
        price_fragment.text = price
        category_fragment.text = category
    }
}