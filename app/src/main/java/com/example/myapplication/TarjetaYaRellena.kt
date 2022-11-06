package com.example.myapplication

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun abrirTarjetaRellena(estaTarjetaVal:Tarjeta,nombreVal:TextFieldValue): Boolean {
    var popupRelleno by remember { mutableStateOf(true) }
    var nombre by remember { mutableStateOf(nombreVal) }
    var estaTarjeta by remember { mutableStateOf(estaTarjetaVal) }
    val maxCharNombre=50

    if (popupRelleno){
        AlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier= Modifier.wrapContentHeight(),
            onDismissRequest = { popupRelleno = false },
            title = { Text(text = "Add") },
            text = {
                   TextField(
                       //value = nombre,
                       value=nombre,
                       onValueChange = { if (it.text.length <= maxCharNombre) nombre= it })


            },



            confirmButton = {
                Row(
                    modifier = Modifier.padding(all =0.dp)
                ) {
                    TextButton(onClick = {
                        estaTarjeta.nombre=nombre.text
                        popupRelleno=false
                    }) {Text("Confirm")}
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {

                    },modifier = Modifier.padding(all =0.dp)
                ) { Text("Dismiss") }
            }
        )
        println("Lanzar Popup "+ popupRelleno)


    }
    return popupRelleno

}

