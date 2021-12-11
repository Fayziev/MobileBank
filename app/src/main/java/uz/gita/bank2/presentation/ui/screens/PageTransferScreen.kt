package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.bank2.R
import uz.gita.bank2.data.request.transfer.MoneyTransferRequest
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.databinding.PageTransferBinding
import uz.gita.bank2.presentation.ui.adapter.CardAdapter
import uz.gita.bank2.presentation.viewmodel.CardViewModel
import uz.gita.bank2.presentation.viewmodel.TransferViewModel
import uz.gita.bank2.presentation.viewmodel.impl.CardViewModelImpl
import uz.gita.bank2.presentation.viewmodel.impl.TransferViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast

@AndroidEntryPoint
class PageTransferScreen : Fragment(R.layout.page_transfer) {

    private val binding by viewBinding(PageTransferBinding::bind)
    private val viewModel: CardViewModel by viewModels<CardViewModelImpl>()
    private val viewModelTransfer: TransferViewModel by viewModels<TransferViewModelImpl>()

    private val list = ArrayList<CardData>()
    private val adapter by lazy { CardAdapter(list) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        listCard.adapter = adapter
        viewModel.getAllCard()
        viewModel.allCardFlow.onEach {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }.launchIn(lifecycleScope)
        combine(
            cardNumberEditText.textChanges().map {
                it.length == 19
            },
            cardMoneyEditText.textChanges().map {
                it.length >= 4 && it.toString().toInt() >= 2000
            },
            transform = { cardNumber, cardMoney -> cardNumber && cardMoney }
        ).onEach { loginBtn.isEnabled = it }.launchIn(lifecycleScope)
        loginBtn.setOnClickListener {
            viewModelTransfer.sendMoney(
                MoneyTransferRequest(
                    cardMoneyEditText.text.toString().toInt(),
                    "8600${cardNumberEditText.rawText}",
                    listCard.currentItem
                )
            )
        }
        viewModelTransfer.progressFlow.onEach {
            if (it) progress.show()
            else progress.hide()
        }.launchIn(lifecycleScope)
        viewModelTransfer.successFlow.onEach {
            showToast("Success")
        }.launchIn(lifecycleScope)
        viewModelTransfer.errorMessageFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

    }
}