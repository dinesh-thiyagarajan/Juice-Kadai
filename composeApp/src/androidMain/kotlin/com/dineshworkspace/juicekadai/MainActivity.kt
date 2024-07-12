package com.dineshworkspace.juicekadai

import JuiceKadaiApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import auth.repositories.AuthRepository
import auth.viewModels.AuthViewModel
import com.google.firebase.FirebaseApp
import juiceSelection.repositories.JuiceKadaiRepository
import juiceSelection.viewModels.JuiceKadaiViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        setContent {
            val juiceKadaiViewModel: JuiceKadaiViewModel =
                viewModel { JuiceKadaiViewModel(JuiceKadaiRepository()) }
            val authViewModel: AuthViewModel =
                viewModel { AuthViewModel(AuthRepository()) }
            JuiceKadaiApp(
                authViewModel = authViewModel,
                juiceKadaiViewModel = juiceKadaiViewModel
            )
        }
    }
}