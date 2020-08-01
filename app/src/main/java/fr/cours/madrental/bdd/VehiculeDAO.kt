package fr.cours.madrental.bdd

import androidx.room.*

@Dao
abstract class VehiculeDAO
{
    @Query("SELECT * FROM vehicules")
    abstract fun getListeVehicules(): MutableList<VehiculeDTO>

    @Query("SELECT * FROM vehicules WHERE name = :name AND price = :price AND category = :category AND image = :img")
    abstract fun getVehicule( name: String, img: String, price: Int, category: String): VehiculeDTO

    @Insert
    abstract fun insert(vararg vehicules: VehiculeDTO)

}