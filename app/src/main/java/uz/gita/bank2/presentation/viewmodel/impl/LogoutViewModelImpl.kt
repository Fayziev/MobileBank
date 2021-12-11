package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.domain.usecase.impl.CardUseCaseImpl
import uz.gita.bank2.presentation.viewmodel.LogoutViewModel
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

class LogoutViewModelImpl @Inject constructor(
    private val useCaseImpl: CardUseCaseImpl,
) : ViewModel(), LogoutViewModel {

    override val errorMessageFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<Unit>()
    override fun logoutUser() {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        useCaseImpl.logoutUser().onEach {
            it.onSuccess {
                viewModelScope.launch {
                    successFlow.emit(Unit)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorMessageFlow.emit(throwable.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

}