package uz.gita.bank2.domain.usecase.impl

import uz.gita.bank2.data.models.StartScreenEnum
import uz.gita.bank2.domain.repository.AppRepository
import uz.gita.bank2.domain.usecase.SplashUseCase
import javax.inject.Inject

class SplashUseCaseImpl @Inject constructor(private val repository: AppRepository) : SplashUseCase {
    override fun startScreen(): StartScreenEnum = repository.startScreen()
}
