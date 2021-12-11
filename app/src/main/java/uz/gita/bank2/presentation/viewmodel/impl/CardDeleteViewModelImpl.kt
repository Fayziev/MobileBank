package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.domain.usecase.impl.CardUseCaseImpl
import uz.gita.bank2.presentation.viewmodel.CardDeleteViewModel
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class CardDeleteViewModelImpl @Inject constructor(
    private val useCaseImpl: CardUseCaseImpl
) : ViewModel(), CardDeleteViewModel {

    override val progressFlow = eventValueFlow<Boolean>()
    override val errorMessageFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<Unit>()

    override fun cardDelete(request: DeleteCardRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCaseImpl.cardDelete(request).onEach {
            it.onSuccess {
                viewModelScope.launch {
                    progressFlow.emit(false)
                    successFlow.emit(Unit)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorMessageFlow.emit(throwable.message.toString())
                    progressFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }

}