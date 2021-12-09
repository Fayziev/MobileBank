package uz.gita.bank2.data.response

data class OutcomesResponse(
	val data: DataOutcomePage
)
data class DataOutcome(
	val amount: Float,
	val receiver: Int,
	val fee: Float,
	val time: Long,
	val status: Int
)

data class DataOutcomePage(
	val pageNumber: Int,
	val data: List<DataOutcome>,
	val pageSize: Int,
	val totalCount: Int
)
