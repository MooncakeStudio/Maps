package com.example.myapplication

import kotlinx.serialization.Serializable

class Tarjeta(nombre: String, descripcion: String, altitud: Double, latitud: Double) {
    var nombre: String = nombre
    var descripcion: String = descripcion

    var altitud: Double = altitud
    var latitud: Double = latitud

    // Constructor


}


@Serializable
data class TarjetaJson(
    val nombre: String,
    val descripcion: String,

    val altitud: Double,
    val latitud: Double
)