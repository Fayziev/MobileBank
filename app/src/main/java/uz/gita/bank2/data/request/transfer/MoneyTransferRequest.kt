package uz.gita.bank2.data.request.transfer


data class MoneyTransferRequest(
	val amount: Int,
	val receiverPan: String,
	val sender: Int
)
