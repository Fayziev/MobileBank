package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.domain.usecase.impl.CardUseCaseImpl
import uz.gita.bank2.presentation.viewmodel.CardEditViewModel
import uz.gita.bank2.presentation.viewmodel.CardViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class CardEditViewModelImpl @Inject constructor(
    private val useCaseImpl: CardUseCaseImpl
) : ViewModel(), CardEditViewModel {

    override val progressFlow= eventValueFlow<Boolean>()
    override val errorMessageFlow= eventValueFlow<String>()
    override val successFlow= eventFlow()
    override fun cardEdit(request: EditCardRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCaseImpl.cardEdit(request).onEach {
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