package mx.itson.world.interfaces

import mx.itson.world.entidades.Visita
import retrofit2.http.GET
import retrofit2.Call

interface CheemsAPI {
    @GET("visitas")
    fun getVisitas(): Call<List<Visita>>
}