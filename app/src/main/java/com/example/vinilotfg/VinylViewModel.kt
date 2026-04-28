package com.example.vinilotfg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VinylViewModel : ViewModel() {
    // Aquí guardaremos la lista de vinilos.
    // Usamos MutableStateFlow porque avisará a la pantalla cuando los datos cambien.
    private val _vinyls = MutableStateFlow<List<Vinyl>>(emptyList())
    val vinyls = _vinyls.asStateFlow()

    init {
        fetchVinyls() // Llamamos a la API nada más crear el ViewModel
    }

    private fun fetchVinyls() {
        viewModelScope.launch {
            try {
                // Aquí usamos tu RetrofitClient para hacer la llamada real
                val response = RetrofitClient.instance.getProductos()
                _vinyls.value = response
            } catch (e: Exception) {
                // Aquí podrías manejar errores (ej: si el servidor está apagado)
                e.printStackTrace()
            }
        }
    }
}