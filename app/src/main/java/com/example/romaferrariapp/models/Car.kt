package com.example.romaferrariapp.models

data class Car(
    private var id: String? = null,
    private var nome: String,
    private var modelo: String,
    private var ano: String,
    private var marca: String
) {
    fun getId(): String? {
        return id
    }

    fun setId(novoId: String) {
        id = novoId
    }

    fun getNome(): String {
        return nome
    }

    fun setNome(novoNome: String) {
        nome = novoNome
    }

    fun getModelo(): String {
        return modelo
    }

    fun setModelo(novoModelo: String) {
        modelo = novoModelo
    }

    fun getAno(): String {
        return ano
    }

    fun setAno(novoAno: String) {
        ano = novoAno
    }

    fun getMarca(): String {
        return marca
    }

    fun setMarca(novaMarca: String) {
        marca = novaMarca
    }
}