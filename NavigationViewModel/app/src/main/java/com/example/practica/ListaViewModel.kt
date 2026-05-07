package com.example.practica

import androidx.lifecycle.ViewModel
import com.example.practica.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListaViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()
    private var contador = 0

    init {
        recargar()
    }

    fun recargar() {

        contador++

        _items.value = listOf(
            Item(1, "Elemento $contador", "Descripción $contador"),
            Item(2, "Elemento ${contador+1}", "Descripción ${contador + 1}"),
        )
    }
}