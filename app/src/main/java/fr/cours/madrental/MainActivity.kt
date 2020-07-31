package fr.cours.madrental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import fr.cours.madrental.bdd.AppDatabaseHelper
import fr.cours.madrental.bdd.VehiculeDTO
import fr.cours.madrental.ws.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    private lateinit var vehiculesAdapter: VehiculesAdapter
    private lateinit var listVehiculeWs: MutableList<VehiculeWs>
    private var mainActivity = this
    private var favori_select: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            favori_select = savedInstanceState.getBoolean("favori")
        }
        list_vehicules.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        list_vehicules.layoutManager = layoutManager
        Log.d("TAG", "$favori_select")


        if (ReseauHelper.estConnecte(this))
        {
            val service = RetrofitSingleton.retrofit.create(WSInterface::class.java)
            val call = service.wsGet()
            call.enqueue(object : Callback<MutableList<VehiculeWs>>
            {
                override fun onResponse(
                    call: Call<MutableList<VehiculeWs>>,
                    response: Response<MutableList<VehiculeWs>>
                ){
                    if (response.isSuccessful)
                    {
                        val retourWSGet = response.body()
                        if(retourWSGet != null)
                        {
                            listVehiculeWs = retourWSGet
                            if(favori_select)
                            {
                                val listVehiculesDTO: MutableList<VehiculeDTO> = AppDatabaseHelper.getDatabase(mainActivity)
                                    .VehiculeDAO()
                                    .getListeVehicules()

                                vehiculesAdapter = VehiculesAdapter(castList(listVehiculesDTO),mainActivity)
                            } else {
                                vehiculesAdapter = VehiculesAdapter(retourWSGet,mainActivity)

                            }
                            list_vehicules.adapter = vehiculesAdapter
                        }
                    }
                }
                override fun onFailure(call: Call<MutableList<VehiculeWs>>, t: Throwable) {
                    Log.e("tag", "${t.message}")
                }
            })
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("favori", favori_select)
        super.onSaveInstanceState(outState)

    }

    fun onShowFavori(view : View?)
    {
        if(favori.isChecked)
        {
            favori_select = true
            val listVehiculesDTO: MutableList<VehiculeDTO> = AppDatabaseHelper.getDatabase(this)
                .VehiculeDAO()
                .getListeVehicules()

            vehiculesAdapter.updateList(castList(listVehiculesDTO))
        } else
        {
            favori_select = false
            vehiculesAdapter.updateList(listVehiculeWs)
        }

    }

    fun castList(list: MutableList<VehiculeDTO>): MutableList<VehiculeWs>
    {
        val newList: MutableList<VehiculeWs> = ArrayList()
        for (vehicule in list)
        {
            newList.add(VehiculeWs(vehicule.name,vehicule.image,vehicule.price,vehicule.category))
        }
        return newList
    }

    fun insertFavori(view : View?)
    {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        val name = preferences.getString("name","")
        val image = preferences.getString("image","")
        val price = preferences.getInt("price", 0)
        val category = preferences.getString("category","")

        if(category != null && name != null && price != 0 && image != null){
            val vehicule = AppDatabaseHelper.getDatabase(this)
                .VehiculeDAO()
                .getVehicule(name,image,price,category)
//            Log.d("tag", vehicule.name)
            if(vehicule == null)
            {
                AppDatabaseHelper.getDatabase(this)
                    .VehiculeDAO()
                    .insert(VehiculeDTO(0,name,image,price,category))
            } else
            {
                Toast.makeText(this,"Ce vehicule est déja présent dans vos Favoris", Toast.LENGTH_LONG).show()
            }

        }
    }
}
