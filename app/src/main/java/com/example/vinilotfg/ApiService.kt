package com.example.vinilotfg

import retrofit2.http.GET

interface ApiService {
    @GET("api/productos") // Esta es la ruta que tu Backend (Spring Boot) expone
    suspend fun getProductos(): List<Vinyl> // Aquí usamos tu modelo 'Vinyl'
}