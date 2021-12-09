package uz.gita.bank2.data.request.card

data class AddCardRequest(
    val pan: String,
    val exp: String,
    val cardName: String
)