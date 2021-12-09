package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow

interface SplashViewModel {
    val openLoginScreenFlow : Flow<Unit>
    val openMainScreenFlow : Flow<Unit>
}