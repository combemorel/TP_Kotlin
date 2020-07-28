package fr.cours.madrental.ws

import retrofit2.Call
import retrofit2.http.GET

interface WSInterface {
    @GET("get-vehicules")
    fun wsGet(): Call<MutableList<VehiculeWs>>
}