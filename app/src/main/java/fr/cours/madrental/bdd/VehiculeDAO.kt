package fr.cours.madrental.bdd

import androidx.room.*

@Dao
abstract class VehiculeDAO
{
    @Query("SELECT * FROM vehicules")
    abstract fun getListeVehicules(): MutableList<VehiculeDTO>

    @Insert
    abstract fun insert(vararg vehicules: VehiculeDTO)

    @Update
    abstract fun update(vararg vehicules: VehiculeDTO)

    @Delete
    abstract fun delete(vararg vehicules: VehiculeDTO)
}