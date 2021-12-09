package uz.gita.bank2.domain.usecase

import uz.gita.bank2.data.models.StartScreenEnum

interface SplashUseCase {
    fun startScreen(): StartScreenEnum
}