package com.example.pastyra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.pastyra.ui.components.PastyraGameStart
import com.example.pastyra.ui.theme.PastyraTheme
import com.example.pastyra.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            PastyraTheme {
                PastyraGameStart(viewModel)
            }
        }
    }
}
