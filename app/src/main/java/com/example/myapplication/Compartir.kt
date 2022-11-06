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



fun Compartir(context: Context){

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "null")
    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    MainActivity.appContext.startActivity(shareIntent)

}