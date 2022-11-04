package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showSystemUi = true)
@Composable
fun ListaMensajes()
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var estadoLista by remember { mutableStateOf(listOf(""))}
        InputLista {
            item -> estadoLista += listOf(item)
        }
        InterfazLista(estadoLista)
    }
}

@Composable
fun InputLista(
    onItemAnadido: (String) -> Unit
){
    var estadoTexto by remember {mutableStateOf("")}
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = estadoTexto,
        onValueChange = { estadoTexto = it },
        label = {Text("Nuevo item")}
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Button(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            onClick = { onItemAnadido(estadoTexto) }
        ) {
            Text("Anadir item")
        }
    }
}

@Composable
fun InterfazLista(lista: List<String>){
    LazyColumn{
        items(lista.size){ index ->
            Text(lista[index])
        }
    }
}