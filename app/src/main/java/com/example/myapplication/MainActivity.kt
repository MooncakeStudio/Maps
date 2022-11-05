package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition.*
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var composeView : ComposeView
    var posicion:String = ""
    var currentLocation : LatLng = LatLng(0.0,0.0)


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
                    scaffold(scaffoldState = scaffoldState, scope = scope)

                }


            }
        }
    }

    @Composable
    fun scaffold(scaffoldState: ScaffoldState, scope: CoroutineScope){
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ){

                alertDialog()
            }
        }
    }

    @Composable
    fun alertDialog(){
        var popup by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("")}

        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = {mapPosition();
                            if(!posicion.equals("(0.0)(0.0)")){popup = true;}
                            },
                        modifier = Modifier.dashedBorder(1.dp, 5.dp, Color.DarkGray)
                    )
                    {
                        Icon(
                            Icons.Filled.AddCircle, contentDescription="h",
                            tint= Color.DarkGray,
                            modifier= Modifier.size(height=20.dp, width=300.dp)
                        )
                    }
                }

                if(popup){
                    AlertDialog(

                        onDismissRequest = {popup = false},
                        title = { Text(text = "Add")},
                        text = {
                            Column() {Text(text = posicion)}
                            Column() {
                                TextField(
                                    value = text,
                                    onValueChange = {text = it},
                                    placeholder = { Text(text = "")}
                                )
                            }
                        },
                        confirmButton = {
                            Row(
                                modifier = Modifier.padding(all = 8.dp)
                            ) {
                                Button(onClick ={popup = false}) {}
                            }
                        }
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun mapPosition() {
        if(checkPermissions()){
            if(locationEnabled()){
                var location = LocationServices.getFusedLocationProviderClient(this).lastLocation
                    .addOnCompleteListener {task->
                        task.addOnSuccessListener { result ->
                            currentLocation = LatLng(result.latitude,result.longitude)
                        }


                }

                posicion = "("+currentLocation.latitude+")("+currentLocation.longitude+")"
                Toast.makeText(this, posicion
                    ,Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermission()
        }


    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    fun checkPermissions() : Boolean{
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    fun locationEnabled() : Boolean{
        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this,
        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
        PERMISSION_REQUEST_ACCESS_LOCATION)
    }

}