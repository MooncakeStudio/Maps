package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.delay
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.seconds


@SuppressLint("MissingPermission")
fun lastLocation() {
    var location =
        LocationServices.getFusedLocationProviderClient(MainActivity.appContext).lastLocation
            .addOnCompleteListener { task ->
                task.addOnSuccessListener { result ->
                    MainActivity.currentLocation = LatLng(result.latitude, result.longitude)
                }

            }
}
//@Preview(showSystemUi = true)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun dialog(context:Context,estadoListaUbis: List<Tarjeta>, onAddTarjeta: (Tarjeta) -> Unit) {
    var popup by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(LatLng(0.0,0.0)) }
    var estadoTexto by remember {mutableStateOf(TextFieldValue())}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = {
                lastLocation()
                text = MainActivity.currentLocation
                popup = true
                /*if(!text.latitude.equals(0.0) || !text.longitude.equals(0.0)){
                    popup = true
                }else{
                    Toast.makeText(MainActivity.appContext,"¿Seguro que le has dado? Inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                }*/
            },
            modifier = Modifier.dashedBorder(1.dp, 5.dp, Color.DarkGray)
        )
        {
            Icon(
                Icons.Filled.AddCircle, contentDescription = "h",
                tint = Color.DarkGray,
                modifier = Modifier.size(height = 20.dp, width = 300.dp)
            )
        }

        if (popup) {
            AlertDialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                modifier=Modifier.wrapContentHeight(),
                onDismissRequest = { popup = false },
                title = { Text(text = "Add") },
                text = {
                    Column() {
                        GoogleMap(
                            modifier = Modifier.size(350.dp,200.dp),
                            cameraPositionState = CameraPositionState(CameraPosition.fromLatLngZoom(text, 15f)),
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
                        val maxChar=50
                        TextField(
                            value = estadoTexto,
                            onValueChange = { if (it.text.length <= maxChar) estadoTexto = it },
                            textStyle = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 30.sp,
                                lineHeight=30.sp //Interlineado
                            ),trailingIcon = {
                                Icon(Icons.Default.Clear,
                                    contentDescription = "clear text",
                                    modifier = Modifier
                                        .clickable {
                                            estadoTexto=TextFieldValue("")
                                        }
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent
                            )
                        )
                        Text(
                            text = "${estadoTexto.text.length} / $maxChar",
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp)
                        )

                        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
                            OutlinedButton(
                                onClick ={ Compartir(context)},
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
                            var item = Tarjeta(estadoTexto.text,"",text.latitude,text.longitude)
                            onAddTarjeta(item)
                            estadoTexto=TextFieldValue("")
                            popup = false
                        }) {Text("Confirm")}
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            estadoTexto=TextFieldValue("")
                            popup = false
                        },modifier = Modifier.padding(all =0.dp)
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }}

@Composable
fun textoBonito(location : LatLng) :String{
    var gecoder = Geocoder(MainActivity.appContext, Locale.getDefault())
    var direccionesPosibles by remember { mutableStateOf(listOf<Address>())}
    if(Build.VERSION.SDK_INT >= 33){
        gecoder.getFromLocation(location.latitude,location.longitude,1,
            Geocoder.GeocodeListener { addresses ->  direccionesPosibles = addresses})
    }else{
        direccionesPosibles = gecoder.getFromLocation(location.latitude,location.longitude,1) as List<Address>
    }

    return direccionesPosibles[0].getAddressLine(0)
}

@Composable
fun ciudad(location : LatLng) :String{
    var gecoder = Geocoder(MainActivity.appContext, Locale.getDefault())
    var direccionesPosibles by remember { mutableStateOf(listOf<Address>())}
    if(Build.VERSION.SDK_INT >= 33){
        gecoder.getFromLocation(location.latitude,location.longitude,1,
            Geocoder.GeocodeListener { addresses ->  direccionesPosibles = addresses})
    }else{
        direccionesPosibles = gecoder.getFromLocation(location.latitude,location.longitude,1) as List<Address>
    }

    return direccionesPosibles[0].locality
}
