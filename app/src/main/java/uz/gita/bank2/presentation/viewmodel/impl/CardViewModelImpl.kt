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
    override val progressFlow = eventValueFlow<Boolean>()

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
                errorMessageFlow.emit(throwable.message.toString())
                progressCardFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

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
                progressFlow.emit(false)
                successFlow.emit(Unit)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

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
                progressFlow.emit(false)
                successFlow.emit(Unit)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun resetUser(request: ResetRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCaseImpl.resetUser(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(Unit)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun logoutUser() {
        if (!isConnected()) {
            viewModelScope.launch {
                errorMessageFlow.emit("Internet bilan muammo")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCaseImpl.logoutUser().onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(Unit)
            }
            it.onFailure { throwable ->
                errorMessageFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

}