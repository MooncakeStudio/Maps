package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class Tarjeta(nombre: String) {
    var nombre: String = nombre
    var descripcion: String = ""

    var altitud: Int = 0
    var latitud: Int = 0

    // Constructor

    constructor(nombre: String, descripcion: String, altitud: Int, latitud: Int) : this(nombre){
        this.descripcion = descripcion
        this.altitud = altitud
        this.latitud = latitud
    }


}