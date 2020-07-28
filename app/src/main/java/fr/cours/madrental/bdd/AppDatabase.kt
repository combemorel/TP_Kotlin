package fr.cours.madrental.bdd

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VehiculeDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun VehiculeDAO(): VehiculeDAO
}