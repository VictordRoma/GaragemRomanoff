package com.example.romaferrariapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.romaferrariapp.models.Car
import com.example.romaferrariapp.repository.CarRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class EditCarViewModel : ViewModel() {
    private val _car = MutableLiveData<Car?>()
    val car: LiveData<Car?> get() = _car

    private val db = FirebaseFirestore.getInstance()

    fun loadCar(carId: String) {
        viewModelScope.launch {
            try {
                val fetchedCar = CarRepository(db).getCar(carId)
                _car.value = fetchedCar
            } catch (e: Exception) {
                Log.e("EditCarViewModel", "Erro ao carregar caro", e)
            }
        }
    }

    fun updateCar(car: Car) {
        viewModelScope.launch {
            try {
                Log.d("EditCarViewModel", "Atualizando caro com ID: ${car.getId()}")
                CarRepository(db).updateCar(car.getId().toString(), car)
            } catch (e: Exception) {
                Log.e("EditCarViewModel", "Erro ao atualizar caro", e)
            }
        }
    }

    fun deleteCar(carId: String) {
        viewModelScope.launch {
            try {
                CarRepository(db).deleteCar(carId)
            } catch (e: Exception) {
                Log.e("EditCarViewModel", "Erro ao deletar caro", e)
            }
        }
    }
}