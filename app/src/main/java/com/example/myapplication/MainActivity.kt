package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.ListaMensajes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition.*
import kotlinx.coroutines.CoroutineScope
import androidx.core.app.ActivityCompat
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.CameraPosition
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.appContext = applicationContext
        val latitude = intent.getDoubleExtra("Lat",0.0)
        val longitude = intent.getDoubleExtra("Long",0.0)

        currentLocation = LatLng(latitude, longitude)

        setContent {
            MyApplicationTheme {
                cargarMenuPrincipal()
            }
        }
    }

    @ExperimentalMaterial3Api
    @Composable
    fun cargando(){
        Text(text = "Cargando...")
    }

    @ExperimentalMaterial3Api
    @Composable
    fun cargarMenuPrincipal() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        var estadoListaUbis by remember { mutableStateOf(listOf<Tarjeta>()) }

        /*Column(
        modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth()
    ) {*/
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                Barra(
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            drawerContent = {
                Cuerpo(
                    items = listOf(
                        Item(
                            id = "Home",
                            title = "Home",
                            contentDescription = "Menu principal",
                            icon = Icons.Default.Home
                        ),
                        Item(
                            id = "Contact",
                            title = "Contacto",
                            contentDescription = "Ponganse en contacto",
                            icon = Icons.Default.Call
                        ),
                        Item(
                            id="Credits",
                            title= "Créditos",
                            contentDescription = "Personas creadoras",
                            icon = Icons.Default.Person
                        )
                    ),
                    onItemClick = {
                        when(it.id){
                            "Home" -> println("Hola")
                            "Contact" -> {
                                var intent = Intent(this@MainActivity, ContactActivity::class.java)
                                startActivity(intent)
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }

                            }
                            "Credits"->{
                                var intent = Intent(this@MainActivity, CreditsActivity::class.java)
                                startActivity(intent)
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        }
                    }
                )
            }
        ) {
            InterfazLista(estadoListaUbis)
            dialog(context = MainActivity.appContext, estadoListaUbis) { item ->
                //var nuevaTarjeta = Tarjeta(item)
                estadoListaUbis += listOf(item)
            }
        }
    }

    companion object{

        val instance = MainActivity()
        lateinit var appContext : Context
        var canMap : Boolean = false
        var currentLocation: LatLng = LatLng(1.35, 103.87)
    }

    @SuppressLint("MissingPermission")
    fun devolverPosicion(context: Context):LatLng{
        if(canMap){
            var location = LocationServices.getFusedLocationProviderClient(appContext).lastLocation
                .addOnCompleteListener {task->
                    task.addOnSuccessListener { result ->
                        currentLocation = LatLng(result.latitude,result.longitude)
                    }
                }
        }

        return currentLocation
    }

/*}*/
}

@Composable
fun mapa() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}

@Composable
fun InterfazLista(estadoListaUbis: List<Tarjeta>) {
    var lanzarPopup by remember { mutableStateOf(false) }
    var nombre by remember {mutableStateOf(TextFieldValue())}
    var estaTarjeta by remember { mutableStateOf(Tarjeta("","",0.0,0.0))}

    LazyColumn (Modifier.padding(start=8.dp)){
        items(estadoListaUbis.size) { index ->
            //Text(estadoListaUbis[index].nombre);
            var gecoder = Geocoder(MainActivity.appContext, Locale.getDefault())
            var direccionesPosibles by remember { mutableStateOf(listOf<Address>())}
            if(Build.VERSION.SDK_INT >= 33){
                gecoder.getFromLocation(estadoListaUbis[index].altitud,estadoListaUbis[index].latitud,1,
                    Geocoder.GeocodeListener { addresses ->  direccionesPosibles = addresses})
            }else{
                direccionesPosibles = gecoder.getFromLocation(estadoListaUbis[index].altitud,estadoListaUbis[index].latitud,1) as List<Address>
            }

            var text : String = direccionesPosibles[0].getAddressLine(0)

            
            Button(onClick={
                lanzarPopup=true
                estaTarjeta=estadoListaUbis[index]
                nombre=TextFieldValue(estadoListaUbis[index].nombre)
            },
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ){
                Column(){
                Text(estadoListaUbis[index].nombre)
                Text(""+text)
            }
            }
            Spacer(Modifier.padding(8.dp))

        }
    }

    if (lanzarPopup){
        lanzarPopup=abrirTarjetaRellena(MainActivity.appContext, estaTarjeta,nombre)

    }


}



/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun preview() {
    var estadoListaUbis by remember { mutableStateOf(listOf<Tarjeta>()) }

    cargarMenuPrincipal(estadoListaUbis)
    dialog(estadoListaUbis) { item ->
        var nuevaTarjeta = Tarjeta(item)
        estadoListaUbis += listOf(nuevaTarjeta)
    }
}
*/
