package com.example.myapplication

import androidx.compose.ui.graphics.vector.ImageVector

data class Item(
    val id: String,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector
)
