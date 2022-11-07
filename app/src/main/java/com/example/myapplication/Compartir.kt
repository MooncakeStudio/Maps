package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.startActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import java.security.AccessController.getContext



fun Compartir(context: Context, nombre:String, direccion:String, latitude:Double, longitude:Double){

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "Found this interesting location!\n"+ nombre + "\nFound at: "+direccion+"\nhttps://maps.google.com/?q="+latitude+","+longitude+",20z")
        //putExtra(Intent.EXTRA_TEXT, "Found this interesting location!\n"+ nombre + "\nFound at: "+direccion+"\nhttps://www.google.com/maps/@"+latitude+","+longitude+",20z")


        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "null")
    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    MainActivity.appContext.startActivity(shareIntent)

}