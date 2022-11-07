package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.MutableDouble
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
class LoadActivity : ComponentActivity() {

    val currentLocation : MutableLiveData<LatLng> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentLocation.observe(this, Observer{
            var intent = Intent(this,MainActivity::class.java)
            intent.putExtra("Lat",currentLocation.value!!.latitude)
            intent.putExtra("Long",currentLocation.value!!.longitude)
            this.startActivity(intent)
        })
        setContent {
            MyApplicationTheme {
                mapPosition()
                Column(
                    modifier = Modifier.size(1000.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Image(
                        painter = painterResource(id = R.drawable.logomovil),
                        contentDescription = "Logo Mooncake Studio"
                    )
                    Text(text = "Cargando...")
                }

            }
        }
    }

    @SuppressLint("MissingPermission")
    fun mapPosition() {
        if(checkPermissions()){
            if(locationEnabled()){
                var location = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                println(currentLocation)
                location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0L,0f,locationListener)

            }else{
                Toast.makeText(this,"Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermission()
        }

    }

    val locationListener = android.location.LocationListener {
            location -> currentLocation.value = LatLng(location.latitude,location.longitude); println("Por lo menos estoy aqui"); }

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
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION)
    }
}