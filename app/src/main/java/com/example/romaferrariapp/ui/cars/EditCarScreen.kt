package com.example.romaferrariapp.ui.cars

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.romaferrariapp.R
import com.example.romaferrariapp.models.Car
import com.example.romaferrariapp.ui.components.CustomTextField
import com.example.romaferrariapp.ui.components.DialogButton
import com.example.romaferrariapp.ui.components.TopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.romaferrariapp.viewModels.EditCarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCarScreen(
    onNavigateToListCars: () -> Unit,
    carId: String,
    modifier: Modifier = Modifier,
    viewModel: EditCarViewModel = viewModel()
) {
    val car by viewModel.car.observeAsState(null)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(carId) {
        viewModel.loadCar(carId)
    }

    if (car != null) {
        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = stringResource(id = R.string.app_name),
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    navigateUp = onNavigateToListCars
                )
            },
            contentColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            EditCarBody(car, onNavigateToListCars, viewModel, innerPadding)
        }
    }
}

@Composable
fun EditCarBody(
    car: Car?,
    onNavigateToListCars: () -> Unit,
    viewModel: EditCarViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(contentPadding)
    ) {
        CarEditInputForm(car, onNavigateToListCars, viewModel)
    }
}

@Composable
fun CarEditInputForm(
    car: Car?,
    onNavigateToListCars: () -> Unit,
    viewModel: EditCarViewModel,
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf(car?.getNome() ?: "") }
    var modelo by remember { mutableStateOf(car?.getModelo() ?: "") }
    var ano by remember { mutableStateOf(car?.getAno() ?: "") }
    var marca by remember { mutableStateOf(car?.getMarca() ?: "") }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome",
            modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = "Modelo",
            modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
            value = ano,
            onValueChange = { ano = it },
            label = "Ano",
            modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
            value = marca,
            onValueChange = { marca = it },
            label = "Marca",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nome.isEmpty() || modelo.isEmpty() || ano.isEmpty() || marca.isEmpty()) {
                    Toast.makeText(context, "Preencha todos os Campos!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val updatedCar = car?.apply {
                    setNome(nome)
                    setModelo(modelo)
                    setAno(ano)
                    setMarca(marca)
                }
                updatedCar?.let {
                    viewModel.updateCar(it)
                    Toast.makeText(context, "Carro atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    onNavigateToListCars()
                }
            }
        ) {
            Text(text = "Salvar", color = MaterialTheme.colorScheme.secondary)
        }

        DialogButton(
            label = "Deletar",
            title = "Confirmar Exclusão",
            message = "Você tem certeza de que deseja deletar este carro?",
            icon = {
                Icon(
                    androidx.compose.material.icons.Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            onConfirm = {
                car?.getId()?.let { carId ->
                    viewModel.deleteCar(carId)
                    Toast.makeText(context, "Carro deletado com sucesso", Toast.LENGTH_SHORT).show()
                    onNavigateToListCars()
                }
            }
        )
    }
}