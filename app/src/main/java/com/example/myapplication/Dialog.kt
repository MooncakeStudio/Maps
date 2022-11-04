package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun dialog(){
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
                    onClick = { popup = true},
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
                        Column() {
                            TextField(
                                value = text,
                                onValueChange = {text = it}
                            )
                        }
                    },
                    confirmButton = {
                        Row(
                            modifier = Modifier.padding(all = 8.dp)
                        ) {
                            Button(onClick = {popup = false}) {

                            }
                        }
                    }
                )
            }
        }
    }

}