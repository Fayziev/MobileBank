package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.card.DeleteCardRequest

interface CardDeleteViewModel {

    val progressFlow: Flow<Boolean>
    val errorMessageFlow: Flow<String>
    val successFlow: Flow<Unit>
    fun cardDelete(request: DeleteCardRequest)

}