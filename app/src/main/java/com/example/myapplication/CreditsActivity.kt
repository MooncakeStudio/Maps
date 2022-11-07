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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class CreditsActivity : ComponentActivity() {

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
                            "Home" -> {
                                finish()
                            }
                            "Contact" -> {
                                var intent = Intent(this@CreditsActivity, ContactActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            "Credits" -> scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Creado por MooncakeStudio")
                Spacer(modifier = Modifier.size(5.dp))
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo")
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Diseñado y Desarrollado por: ")
                Spacer(modifier = Modifier.size(5.dp))
                Text(text = "Pablo Álvarez de Lara Fernández")
                Spacer(modifier = Modifier.size(2.dp))
                Text(text = "Daniel Mayoral Fernández-Baíllo")
                Spacer(modifier = Modifier.size(2.dp))
                Text(text = "Javier Picado Hijón")
                Spacer(modifier = Modifier.size(2.dp))
                Text(text = "Marta Rodríguez Castillo")
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = "Logo GitHub diseñado por PixelPerfect")
                IconButton(onClick = {
                    val uri = Uri.parse("https://www.flaticon.es/icono-gratis/github_779088?term=github&page=1&position=15&page=1&position=15&related_id=779088&origin=tag")
                    val gitHubIntent = Intent(Intent.ACTION_VIEW,uri)
                    this@CreditsActivity.startActivity(gitHubIntent)
                }) {
                    Icon(painter = painterResource(id = R.mipmap.github), contentDescription = "GitHub", modifier = Modifier.size(20.dp))
                }
                Row(){}
            }

        }
    }
}