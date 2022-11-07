package com.example.myapplication

class Tarjeta(nombre: String) {
    var nombre: String = nombre
    var descripcion: String = ""

    var altitud: Double = 0.0
    var latitud: Double = 0.0

    // Constructor

    constructor(nombre: String, descripcion: String, altitud: Double, latitud: Double) : this(nombre){
        this.descripcion = descripcion
        this.altitud = altitud
        this.latitud = latitud
    }
}