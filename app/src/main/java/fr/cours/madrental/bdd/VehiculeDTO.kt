package fr.cours.madrental.bdd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicules")
class VehiculeDTO (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val image: String = "",
    val price: Int = 0 ,
    val category: String = ""
)
