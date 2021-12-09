package uz.gita.bank2.data.response.card

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AllCardResponse(
    val data: CardList
)

data class CardList(
    val data: List<CardData>
)

@Parcelize
data class CardData(
    val pan: String,
    val exp: String,
    val owner: String,
    val cardName: String,
    val balance: Long,
    val status: Int
) : Parcelable
