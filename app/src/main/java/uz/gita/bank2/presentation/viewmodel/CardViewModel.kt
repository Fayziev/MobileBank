package uz.gita.bank2.presentation.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.bank2.data.request.auth.ResetRequest
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.response.card.CardData

interface CardViewModel {
    val progressCardFlow: Flow<Boolean>
    val errorMessageFlow: Flow<String>
    val successFlow: Flow<Unit>
    val allCardFlow: Flow<List<CardData>>
    val openEditScreenFlow: Flow<Unit>
    fun getAllCard()
    fun openEditScreen()
}