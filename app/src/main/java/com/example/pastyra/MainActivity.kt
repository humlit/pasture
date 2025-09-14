package com.example.pastyra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.pastyra.ui.components.PastyraGameStart
import com.example.pastyra.ui.theme.PastyraTheme
import com.example.pastyra.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<GameViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PastyraTheme {
                PastyraGameStart(viewModel)
            }
        }
    }
}

/*
ЗАМЕЧЕННЫЙ БАГИ

НАЧАЛО СВЯЗКИ -> КАРТА ИЗ РУКИ УХОДИТ В МАССИВ АКТИВНЫХ КАРТ, ЕСЛИ МЫ ЕЁ ВЕРНЁМ НА СТОЛ И ВОЗЬМЁМ ПЕРВОЙ КАРТОЙ ДРУГУЮ КАРТУ, ТО ПРИ СМЕНЕ КАРТЫ В РУКУ
ВЕРНЁТСЯ КАРТА ПОД ИНДЕКСОМ [0], ВМЕСТО НАШЕЙ КАРТЫ

ПРИМЕРНЫЙ ФИКС

СДЕЛАТЬ УСЛОВИЕ, ПРИ КОТОРОМ ЕСЛИ КАРТ В АКТИВНОМ МАССИВЕ НЕТ, СТАДИЯ ИГРЫ СТАНОВИТСЯ -> DefaultStage

---------------------------------------------------------------------------------------------------------------------------------------------------------

ПОЧЕМУ ТО МОЖНО СВЯЗАТЬ 7 СВЯЗАННУЮ НА СТОЛЕ + ТУЗ СО СТОЛА КОГДА В РУКЕ ДВЕ 8

ПОЧЕМУ
 */