package com.mtw.josealejandrosanchezortega.mitrayecto.api

object VehiclesModel {

    data class VehiclesApiResponse (
        // val totalItems : Int,
        val items : List<VehicleModel>
    )

    data class VehicleModel (
            val id : Int,
            val smallname : String,
            val description : String,
            val imageURL : String
    )
}