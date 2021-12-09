package uz.gita.bank2.data.response


data class IncomesResponse(
    val data: DataIncomePage
)

data class DataIncomePage(
    val pageNumber: Int,
    val data: List<DataIncome>,
    val pageSize: Int,
    val totalCount: Int
)

data class DataIncome(
    val amount: Double,
    val sender: Int,
    val fee: Double,
    val time: Long
)
