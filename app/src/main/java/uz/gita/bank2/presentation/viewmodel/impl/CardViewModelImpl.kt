package uz.gita.bank2.presentation.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.domain.usecase.impl.CardUseCaseImpl
import uz.gita.bank2.presentation.viewmodel.CardViewModel
import uz.gita.bank2.utils.eventFlow
import uz.gita.bank2.utils.eventValueFlow
import uz.gita.bank2.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class CardViewModelImpl @Inject constructor(
    private val useCaseImpl: CardUseCaseImpl,
) : ViewModel(), CardViewModel {

    override val progressCardFlow = eventValueFlow<Boolean>()
    override val errorMessageFlow = eventValueFlow<String>()
    override val allCardFlow = eventValueFlow<List<CardData>>()
    override val successFlow = eventFlow()
    override val openEditScreenFlow = eventFlow()
    override fun getAllCard() {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressCardFlow.emit(true)
        }
        useCaseImpl.cardGet().onEach {
            it.onSuccess { list ->
                viewModelScope.launch {
                    progressCardFlow.emit(false)
                    allCardFlow.emit(list)
                }
            }
            it.onFailure { throwable ->
                viewModelScope.launch {
                    errorMessageFlow.emit(throwable.message.toString())
                    progressCardFlow.emit(false)
                }
            }
        }.launchIn(viewModelScope)
    }




    override fun openEditScreen() {
        viewModelScope.launch {
            openEditScreenFlow.emit(Unit)
        }
    }

}