package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun dialog(){
    var popup by remember { mutableStateOf(false) }
    var textoFinal by remember { mutableStateOf(TextFieldValue())}

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
                    properties = DialogProperties(usePlatformDefaultWidth = false),
                    modifier=Modifier.wrapContentHeight(),
                    onDismissRequest = {popup = false},
                    title = { Text(text = "Add")},
                    text = {
                        Column() {
                            val maxChar=50
                            TextField(
                                value = textoFinal,
                                onValueChange = { if (it.text.length <= maxChar) textoFinal = it },
                                textStyle = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 30.sp,
                                    lineHeight=30.sp //Interlineado
                                ),
                                //lineHeight=30.sp,
                                trailingIcon = {
                                    Icon(Icons.Default.Clear,
                                        contentDescription = "clear text",
                                        modifier = Modifier
                                            .clickable {
                                                textoFinal=TextFieldValue("")
                                            }
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent
                                )
                            )
                            Text(
                                text = "${textoFinal.text.length} / $maxChar",
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
                            )
                            //Text("The textfield has this text: " + textoFinal)

                        }
                    },
                    confirmButton = {
                        Row(
                            //modifier = Modifier.padding(all = 8.dp)
                        ) {
                            TextButton(onClick = {popup = false}) {Text("Confirm")}
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                textoFinal=TextFieldValue("")
                                popup = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }
        }
    }

}
