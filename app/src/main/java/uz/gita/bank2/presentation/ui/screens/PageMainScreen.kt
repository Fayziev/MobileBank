package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bank2.R
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.databinding.PageMainBinding
import uz.gita.bank2.presentation.ui.adapter.CardAdapter
import uz.gita.bank2.presentation.viewmodel.CardViewModel
import uz.gita.bank2.presentation.viewmodel.impl.CardViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast

@AndroidEntryPoint
class PageMainScreen : Fragment(R.layout.page_main) {
    private val binding by viewBinding(PageMainBinding::bind)
    private val viewModel: CardViewModel by viewModels<CardViewModelImpl>()
    private val list = ArrayList<CardData>()
    private val adapter = CardAdapter(list)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        listCard.adapter = adapter
//        listCard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.getAllCard()
        viewModel.allCardFlow.onEach {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }.launchIn(lifecycleScope)

        viewModel.errorMessageFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.progressFlow.onEach {
            if (it) progress.show()
            else progress.hide()
        }.launchIn(lifecycleScope)

        viewModel.progressCardFlow.onEach {
            if (it) progressCard.show()
            else progressCard.hide()
        }.launchIn(lifecycleScope)

    }
}