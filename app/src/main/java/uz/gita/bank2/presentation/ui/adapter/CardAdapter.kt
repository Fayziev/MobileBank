package uz.gita.bank2.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.databinding.ItemCardBinding

class CardAdapter(private val list: List<CardData>) : RecyclerView.Adapter<CardAdapter.VH>() {

    inner class VH(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.money.text = list[absoluteAdapterPosition].balance.toString()
            binding.name.text = list[absoluteAdapterPosition].owner
            binding.term.text = list[absoluteAdapterPosition].exp
            binding.pan.text = list[absoluteAdapterPosition].pan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind()
    override fun getItemCount(): Int = list.size
}