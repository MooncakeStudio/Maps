package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class ContactActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        
        
        setContent {
            MyApplicationTheme {
                menuLateral()
            }
        }
    }
    
    @ExperimentalMaterial3Api
    @Composable
    fun menuLateral() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

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
                            id = "Contact",
                            title = "Contacto",
                            contentDescription = "Ponganse en contacto",
                            icon = Icons.Default.Call
                        )
                    ),
                    onItemClick = {
                        when(it.id){
                            "Home" -> {
                                finish()
                            }
                            "Contact" -> println("Hola")
                        }
                    }
                )
            }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Creado por MooncakeStudio")
                Image(painter = painterResource(id = R.drawable.logomovil), contentDescription = "Logo")
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = "Contacto:")
                    IconButton(
                        onClick = {
                            val correoIntent = Intent(Intent.ACTION_SENDTO)
                            correoIntent.setType("message/rfc822")
                            correoIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mooncakestudiogames@gmail.com"))
                            correoIntent.putExtra(Intent.EXTRA_SUBJECT,"App Ubicaciones")
                            this@ContactActivity.startActivity(correoIntent)
                        }
                    )
                    {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                    }

                    IconButton(onClick = {
                        val uri = Uri.parse("https://mooncakestudio.github.io/")
                        val gitHubIntent = Intent(Intent.ACTION_VIEW,uri)
                        this@ContactActivity.startActivity(gitHubIntent)
                    }) {
                        Icon(painter = painterResource(id = R.mipmap.github), contentDescription = "GitHub")
                    }
                    IconButton(onClick = {
                        val uri = Uri.parse("https://linktr.ee/mooncakestudio")
                        val linkTreeIntent = Intent(Intent.ACTION_VIEW,uri)
                        this@ContactActivity.startActivity(linkTreeIntent)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_link), contentDescription = "LinkTree")
                    }
                }
            }

        }
    }
}

