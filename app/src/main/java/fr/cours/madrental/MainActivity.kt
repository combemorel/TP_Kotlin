package fr.cours.madrental

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import fr.cours.madrental.bdd.AppDatabaseHelper
import fr.cours.madrental.bdd.VehiculeDAO
import fr.cours.madrental.bdd.VehiculeDTO
import fr.cours.madrental.ws.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    private lateinit var vehiculesAdapter: VehiculesAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_vehicules.setHasFixedSize(true)
        // layout manager, décrivant comment les items sont disposés :
        val layoutManager = LinearLayoutManager(this)
        list_vehicules.layoutManager = layoutManager

        if (ReseauHelper.estConnecte(this)) {
            val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
            val call = service.wsGet()
            call.enqueue(object : Callback<MutableList<VehiculeWs>> {
                override fun onResponse(
                    call: Call<MutableList<VehiculeWs>>,
                    response: Response<MutableList<VehiculeWs>>
                ) {
                    if (response.isSuccessful) {
                        val retourWSGet = response.body()
                        Log.d("tag", "$retourWSGet")
                        // adapter :
                        val vehiculesAdapter = retourWSGet?.let { VehiculesAdapter(it) }
                        list_vehicules.adapter = vehiculesAdapter

                    }
                }

                override fun onFailure(call: Call<MutableList<VehiculeWs>>, t: Throwable) {
                    Log.e("tag", "${t.message}")
                }
            })
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun onShowFavori(view : View?){
            Log.d("tag", favori.text.toString())
        if(favori.isSelected == true){
            Log.d("tag", "select")

            val listeVehicules: MutableList<VehiculeDTO> = AppDatabaseHelper.getDatabase(this)
                .VehiculeDAO()
                .getListeVehicules()
            vehiculesAdapter.updateListVehicule(listeVehicules)
        }

    }
}
