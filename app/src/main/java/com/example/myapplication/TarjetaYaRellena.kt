package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
fun abrirTarjetaRellena(context: Context, estaTarjetaVal:Tarjeta, nombreVal:TextFieldValue, descVal:TextFieldValue): Boolean {
    var popupRelleno by remember { mutableStateOf(true) }
    var nombre by remember { mutableStateOf(nombreVal) }
    var desc by remember { mutableStateOf(descVal) }
    var estaTarjeta by remember { mutableStateOf(estaTarjetaVal) }
    var textitoBonito=textoBonito(location = MainActivity.currentLocation)
    val maxCharNombre=50

    var estadoTextoD by remember {mutableStateOf(TextFieldValue())}

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

                    Text(
                        text = "${nombre.text.length} / $maxCharNombre",
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    )

                    val maxCharD=120
                    TextField(
                        value = desc,
                        onValueChange = { if (it.text.length <= maxCharD) desc = it },
                        textStyle = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            lineHeight=20.sp //Interlineado
                        ),trailingIcon = {
                            Icon(Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .clickable {
                                        desc=TextFieldValue("")
                                    }
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent
                        )
                    )
                    Text(
                        text = "${desc.text.length} / $maxCharD",
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                    )

                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
                        OutlinedButton(
                            onClick ={ Compartir(context,nombre.text, textitoBonito, MainActivity.currentLocation.latitude,MainActivity.currentLocation.longitude)},
                            modifier= Modifier
                                .background(Color.Transparent)
                                .padding(top = 25.dp, bottom = 0.dp),
                            shape = RoundedCornerShape(50)
                        ){
                            Row{
                                Icon(
                                    Icons.Filled.Share, contentDescription="h",
                                    tint= MaterialTheme.colorScheme.onSurface,
                                    //modifier= Modifier.size(height=20.dp, width=300.dp)
                                )
                                Text(text="Share",
                                    color = MaterialTheme.colorScheme.onSurface)                                }
                        }}



                }

            },



            confirmButton = {
                Row(
                    modifier = Modifier.padding(all =0.dp)
                ) {
                    TextButton(onClick = {
                        estaTarjeta.nombre=nombre.text
                        estaTarjeta.descripcion=desc.text
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

