package uz.gita.bank2.data.response.transfer

sealed class MoneyTransferResponse {
    data class TransferResponse(
        val data: TransferHistory
    )

    data class TransferHistory(
        val pageNumber: Int,
        val pageSize: Int,
        val totalCount: Int,
        val data: List<HistoryData>
    )

    data class HistoryData(
        val receiver: Int,
        val sender: Int,
        val amount: Float,
        val time: Long,
        val fee: Float,
        val status: Int = 0
    )

}