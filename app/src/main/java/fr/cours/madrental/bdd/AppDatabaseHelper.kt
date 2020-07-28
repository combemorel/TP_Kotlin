package fr.cours.madrental.bdd

import android.content.Context
import androidx.room.Room

class AppDatabaseHelper (context : Context)
{
    // Bloc de code "static" :
    companion object
    {
        // Helper :
        private lateinit var databaseHelper: AppDatabaseHelper

        // Getter instance :
        fun getDatabase(context: Context): AppDatabase
        {
            if (!::databaseHelper.isInitialized)
            {
                databaseHelper = AppDatabaseHelper(context)
            }

            return databaseHelper.database
        }
    }
    // Base de données :
    val database: AppDatabase = Room
        .databaseBuilder(context.applicationContext, AppDatabase::class.java, "vehicule.db")
        .allowMainThreadQueries()
        .build()

}