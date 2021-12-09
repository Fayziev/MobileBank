package uz.gita.bank2.data.request.auth

data class VerifyRequest(
    val phone: String,
    val code: String
)