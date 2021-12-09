package uz.gita.bank2.data.response.transfer

data class SendMoneyResponse(
    val data: DataMoney
)

data class DataMoney(
    val amount: Float,
    val receiver: Int,
    val sender: Int,
    val fee: Float,
    val id: Int,
    val time: Long,
    val status: Int
)
