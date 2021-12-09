package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.bank2.data.models.StartScreenEnum
import uz.gita.bank2.domain.usecase.SplashUseCase
import uz.gita.bank2.presentation.viewmodel.SplashViewModel
import uz.gita.bank2.utils.eventFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val useCase: SplashUseCase,
) : ViewModel(), SplashViewModel {

    override val openLoginScreenFlow = eventFlow()
    override val openMainScreenFlow = eventFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            if (useCase.startScreen() == StartScreenEnum.MAIN) openMainScreenFlow.tryEmit(Unit)
            else openLoginScreenFlow.tryEmit(Unit)
        }
    }
}