package com.example.romaferrariapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.romaferrariapp.models.Car
import com.example.romaferrariapp.repository.CarRepository
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore

class CarsViewModel : ViewModel() {
    private val _cars = MutableLiveData<List<Car>>()
    val cars: LiveData<List<Car>> get() = _cars

    private val db = FirebaseFirestore.getInstance()

    init {
        loadCars()
    }

    private fun loadCars() {
        viewModelScope.launch {
            _cars.value = CarRepository(db).getCars()
        }
    }
}
