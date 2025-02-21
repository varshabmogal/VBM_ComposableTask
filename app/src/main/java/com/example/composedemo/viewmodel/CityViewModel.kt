package com.example.composedemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.model.City
import com.senses.composabletask.model.Post
import com.senses.composabletask.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CityViewModel(private val repository: PostRepository) : ViewModel() {

    private val _city = MutableStateFlow<List<City>>(emptyList())
    val citys = _city.asStateFlow()

    init {
        fetchCities()
    }

    private fun fetchCities() {
        viewModelScope.launch {
            _city.value = repository.getCity()
        }
    }
}
