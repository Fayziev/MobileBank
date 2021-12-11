package uz.gita.bank2.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.databinding.ItemCardBinding

class CardAdapter(private val list: List<CardData>) : RecyclerView.Adapter<CardAdapter.VH>() {
    private var longClickListener: ((CardData, Int) -> Unit)? = null
    private var clickListener: ((CardData, Int) -> Unit)? = null

    inner class VH(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnLongClickListener {
                longClickListener?.invoke(list[absoluteAdapterPosition], absoluteAdapterPosition)
                return@setOnLongClickListener true
            }
            itemView.setOnClickListener {
                clickListener?.invoke(list[absoluteAdapterPosition], absoluteAdapterPosition)
            }

        }

        fun bind() {
            binding.money.text = list[absoluteAdapterPosition].balance.toString()
            binding.name.text = list[absoluteAdapterPosition].owner
            binding.term.text = list[absoluteAdapterPosition].exp
            binding.pan.text = list[absoluteAdapterPosition].pan
            binding.cardName.text = list[absoluteAdapterPosition].cardName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()
    override fun getItemCount(): Int = list.size
    fun setLongClickListener(f: (CardData, Int) -> Unit) {
        longClickListener = f
    }

    fun setClickListener(f: (CardData, Int) -> Unit) {
        clickListener = f
    }
}