package uz.gita.bank2.data.request.auth

data class ResendRequest(
    val phone: String,
    val password: String
)