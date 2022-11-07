package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.google.android.gms.maps.model.CameraPosition.*
import com.google.android.gms.maps.model.CameraPosition
import androidx.compose.runtime.mutableStateOf

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var composeView: ComposeView
    var currentLocation: LatLng = LatLng(0.0, 0.0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var estadoListaUbis by remember { mutableStateOf(listOf<Tarjeta>()) }
                var listaTarjetas: ArrayList<TarjetaModelClass> = ArrayList()

                val stringJson: String = recogerJson(listaTarjetas, this@MainActivity)

                cargarMenuPrincipal(estadoListaUbis, stringJson)
                dialog(context = this@MainActivity, estadoListaUbis) { item ->
                    var nuevaTarjeta = Tarjeta(item)

                    estadoListaUbis += listOf(nuevaTarjeta)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun cargarMenuPrincipal(estadoListaUbis: List<Tarjeta>, stringJson: String) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
                        contentDescription = "A casita",
                        icon = Icons.Default.Home
                    ),
                    Item(
                        id = "Settings",
                        title = "Settings",
                        contentDescription = "La opsione",
                        icon = Icons.Default.Settings
                    ),
                    Item(
                        id = "Contact",
                        title = "Contacto",
                        contentDescription = "Ponganse en contacto",
                        icon = Icons.Default.Call
                    )
                ),
                onItemClick = {
                    println("Clicked on ${it.title}")
                }
            )
        }
    ) {
        InterfazLista(estadoListaUbis, stringJson)
    }
}
/*}*/

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
fun InterfazLista(estadoListaUbis: List<Tarjeta>, stringJson: String) {
    var tarjetas: List<Tarjeta> = listOf()
    LazyColumn {
        /*items(stringJson.size) { index ->
            Text(stringJson[index].nombre)
        }*/
        items(estadoListaUbis.size) { index ->
            Text(estadoListaUbis[index].nombre)
        }
    }
}

fun recogerJson (listaJson: ArrayList<TarjetaModelClass>, contexto: Context) : String {
    var JsonString = Parser.getJsonFromAssets(contexto, "tarjetas.json")
    //var lista: List<Tarjeta> = Parser.getListFromString(JsonString)
    return JsonString
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

