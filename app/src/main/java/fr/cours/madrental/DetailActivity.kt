package fr.cours.madrental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        val price = intent.getIntExtra("textViewPriceVehicule",0)
        val category = intent.getStringExtra("textViewCategoryVehicule")

        val text = "Nom : $name \n"+
                "Prix : $price € / jour \n"+
                "Catégorie CO2 : $category"

        val bundle = Bundle()
        bundle.putString("text", text)
        fragment.arguments = bundle


    }
    fun insertFavori(view : View?)
    {
        val name = intent.getStringExtra("textViewNameVehicule")
        val price = intent.getIntExtra("textViewPriceVehicule",0)
        val category = intent.getStringExtra("textViewCategoryVehicule")

        AppDatabaseHelper.getDatabase(this)
            .VehiculeDAO()
            .insert(VehiculeDTO(0,name,price,category))
        val test = AppDatabaseHelper.getDatabase(this)
            .VehiculeDAO()
            .getListeVehicules()
    }


}