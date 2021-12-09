package uz.gita.bank2.data.request.card

data class VerifyCardRequest(
    val pan:String,
    val code:String
)