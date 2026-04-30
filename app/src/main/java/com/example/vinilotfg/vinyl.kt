package com.example.vinilotfg

data class Vinyl(
    val id: String,
    val nombre: String,
    val artista: String,
    val genero: String,
    val precio: Double,
    val imagen_url: String,
    val descripcion: String?,
    val stock: Int,
    val estado: String
)