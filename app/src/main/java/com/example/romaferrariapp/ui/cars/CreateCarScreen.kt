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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.romaferrariapp.repository.CarRepository
import com.example.romaferrariapp.ui.components.CustomTextField
import com.example.romaferrariapp.ui.components.TopAppBar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCarScreen(
    onNavigateToListCars: () -> Unit,
    modifier: Modifier = Modifier
) {
    val user = FirebaseAuth.getInstance().currentUser
    val db = Firebase.firestore

    user?.let {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
            CreateCarBody(db, onNavigateToListCars, innerPadding)
        }
    }
}

@Composable
fun CreateCarBody(
    db: FirebaseFirestore,
    onNavigateToListCars: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(contentPadding)
    ) {
        CarInputForm(db, onNavigateToListCars)
    }
}

@Composable
fun CarInputForm(
    db: FirebaseFirestore,
    onNavigateToListCars: () -> Unit,
    car: Car? = null,
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf(car?.getNome() ?: "") }
    var modelo by remember { mutableStateOf(car?.getModelo() ?: "") }
    var ano by remember { mutableStateOf(car?.getAno() ?: "") }
    var marca by remember { mutableStateOf(car?.getMarca() ?: "") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
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

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(nome.isEmpty() || modelo.isEmpty() || ano.isEmpty() || marca.isEmpty()){
                    Toast.makeText(context, "Preencha todos os Campos!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                coroutineScope.launch {
                    try {
                        val car = Car(null, nome, modelo, ano, marca)
                        val result = CarRepository(db).addCar(car)

                        Toast.makeText(context, "Carro Criado com Sucesso!", Toast.LENGTH_SHORT).show()
                        onNavigateToListCars()
                    } catch (e: Exception) {
                        Toast.makeText(context, "Erro ao Criar Carro!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Salvar", color = MaterialTheme.colorScheme.secondary)
        }
    }
}