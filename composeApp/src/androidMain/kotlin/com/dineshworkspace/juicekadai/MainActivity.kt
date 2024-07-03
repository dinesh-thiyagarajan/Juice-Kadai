package com.dineshworkspace.juicekadai

import JuiceKadaiApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import repositories.JuiceKadaiRepository
import viewModels.JuiceKadaiViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val juiceKadaiViewModel: JuiceKadaiViewModel =
                viewModel { JuiceKadaiViewModel(JuiceKadaiRepository()) }
            JuiceKadaiApp(juiceKadaiViewModel)
        }
    }
}