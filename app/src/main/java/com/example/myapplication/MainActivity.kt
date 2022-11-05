package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var composeView : ComposeView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                            Cosa()
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

                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ){
                    dialog(this@MainActivity)

                }
            }
        }
    }
}



@Composable
fun mapa(){
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun preview(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                Cosa()
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

        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ){
        //dialog()
    }



}