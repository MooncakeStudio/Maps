package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.ListaMensajes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private lateinit var composeView: ComposeView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var estadoListaUbis by remember { mutableStateOf(listOf<Tarjeta>()) }

                cargarMenuPrincipal(estadoListaUbis)
                dialog(estadoListaUbis) { item ->
                    var nuevaTarjeta = Tarjeta(item)
                    estadoListaUbis += listOf(nuevaTarjeta)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun cargarMenuPrincipal(estadoListaUbis: List<Tarjeta>) {
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
        InterfazLista(estadoListaUbis)
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
fun InterfazLista(estadoListaUbis: List<Tarjeta>) {
    LazyColumn {
        items(estadoListaUbis.size) { index ->
            Text(estadoListaUbis[index].nombre)
        }
    }
}


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