package uz.gita.bank2.data.request.card

data class EditCardRequest(
    val cardNumber: String,
    val newName: String
)