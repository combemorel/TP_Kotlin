package fr.cours.madrental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import fr.cours.madrental.bdd.AppDatabaseHelper
import fr.cours.madrental.bdd.VehiculeDTO
import fr.cours.madrental.fragments.DetailFragment
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val fragment = DetailFragment()

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.conteneur_fragment, fragment, "fragment")
        transaction.commit()

        val name = intent.getStringExtra("textViewNameVehicule")
        val image = intent.getStringExtra("img")
        val price = intent.getIntExtra("textViewPriceVehicule",0)
        val category = intent.getStringExtra("textViewCategoryVehicule")


        val bundle = Bundle()
        bundle.putString("name", "Nom : $name")
        bundle.putString("price", "Prix : $price € / jour")
        bundle.putString("category", "Catégorie CO2 : $category")
        bundle.putString("img", "$image")
        fragment.arguments = bundle


    }
    fun insertFavori(view : View?)
    {
        val name = intent.getStringExtra("textViewNameVehicule")
        val image = intent.getStringExtra("img")
        val price = intent.getIntExtra("textViewPriceVehicule",0)
        val category = intent.getStringExtra("textViewCategoryVehicule")

        if(category != null && name != null && price != 0 && image != null) {
            val vehicule = AppDatabaseHelper.getDatabase(this)
                .VehiculeDAO()
                .getVehicule(name, image, price, category)

            if(vehicule == null)
            {
                AppDatabaseHelper.getDatabase(this)
                    .VehiculeDAO()
                    .insert(VehiculeDTO(0,name,image,price,category))
            } else
            {
                Toast.makeText(this,"Ce vehicule est déja présent dans vos Favoris",Toast.LENGTH_LONG).show()
            }
        }else
        {
            Toast.makeText(this,"Un problème est survenue lors de l'envoie",Toast.LENGTH_LONG).show()
        }


    }


}