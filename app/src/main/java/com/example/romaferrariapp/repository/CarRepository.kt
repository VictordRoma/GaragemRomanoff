package com.example.romaferrariapp.repository

import android.util.Log
import com.example.romaferrariapp.models.Car
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CarRepository(private val db: FirebaseFirestore) {
    private val carsCollection = db.collection("cars")

    suspend fun addCar(car: Car): Boolean {
        return try {
            val newCarRef = carsCollection.add(
                mapOf(
                    "nome" to car.getNome(),
                    "modelo" to car.getModelo(),
                    "ano" to car.getAno(),
                    "marca" to car.getMarca()
                )
            ).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCars(): List<Car> {
        return try {
            val snapshot = carsCollection.get().await()
            snapshot.documents.map { document ->
                Car(
                    id = document.id,
                    nome = document.getString("nome") ?: "",
                    modelo = document.getString("modelo") ?: "",
                    ano = document.getString("ano") ?: "",
                    marca = document.getString("marca") ?: ""
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getCar(id: String): Car? {
        return try {
            val document = carsCollection.document(id).get().await()
            if (document.exists()) {
                Car(
                    id = document.id,
                    nome = document.getString("nome") ?: "",
                    modelo = document.getString("modelo") ?: "",
                    ano = document.getString("ano") ?: "",
                    marca = document.getString("marca") ?: ""
                )
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateCar(id: String, car: Car?): Boolean {
        return try {
            val documentRef = carsCollection.document(id)
            documentRef.update(
                "nome", car?.getNome(),
                "modelo", car?.getModelo(),
                "ano", car?.getAno(),
                "marca", car?.getMarca()
            ).await()
            true
        } catch (e: Exception) {
            Log.e("CarRepository", "Erro ao atualizar carro: ${e.message}")
            false
        }
    }

    suspend fun deleteCar(id: String): Boolean {
        return try {
            carsCollection.document(id).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}