package fr.cours.madrental.ws

import android.text.Html
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WSInterface {
    @GET("get-vehicules.php")
    fun wsGet(): Call<MutableList<VehiculeWs>>

    @GET("images/{img}")
    fun wxGetImg(@Path("img") imageGetter: Html.ImageGetter)
}