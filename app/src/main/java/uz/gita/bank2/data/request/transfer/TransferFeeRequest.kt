package uz.gita.bank2.data.request.transfer

data class TransferFeeRequest(
    val amount: Int,
    val receiverPan: String,
    val sender: Int
)
