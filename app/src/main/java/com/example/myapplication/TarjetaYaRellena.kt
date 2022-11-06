package com.example.myapplication

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


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
            title = { Text(text = "Modify") },
            text = {
                Column(){
                    GoogleMap(
                        modifier = Modifier.size(350.dp,200.dp),
                        cameraPositionState = CameraPositionState(CameraPosition.fromLatLngZoom(MainActivity.currentLocation, 15f)),
                        onMapClick = {
                            val uri = Uri.parse("geo:"+MainActivity.currentLocation.latitude+","+MainActivity.currentLocation.longitude)
                            val mapIntent = Intent(Intent.ACTION_VIEW,uri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            mapIntent.resolveActivity(MainActivity.appContext.packageManager)?.let {
                                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                MainActivity.appContext.startActivity(mapIntent)
                            }
                        }
                    ){
                        Marker(
                            state = MarkerState(position = MainActivity.currentLocation),
                            title = textoBonito(location = MainActivity.currentLocation),
                            snippet = "Marker in "+ ciudad(MainActivity.currentLocation)
                        )
                    }
                    TextField(
                        //value = nombre,
                        value=nombre,
                        onValueChange = { if (it.text.length <= maxCharNombre) nombre= it },
                        textStyle = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 30.sp,
                            lineHeight=30.sp //Interlineado
                        ),trailingIcon = {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .clickable {
                                        nombre=TextFieldValue("")
                                    }
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        )
                    )
                }

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
                        nombre=TextFieldValue("")
                        popupRelleno = false
                    },modifier = Modifier.padding(all =0.dp)
                ) {
                    Text("Dismiss")
                }
            }
        )
        println("Lanzar Popup "+ popupRelleno)


    }
    return popupRelleno

}

