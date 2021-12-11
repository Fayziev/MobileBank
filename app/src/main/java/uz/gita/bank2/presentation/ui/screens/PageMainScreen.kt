package uz.gita.bank2.presentation.ui.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bank2.R
import uz.gita.bank2.data.request.card.DeleteCardRequest
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.databinding.PageMainBinding
import uz.gita.bank2.presentation.ui.adapter.CardAdapter
import uz.gita.bank2.presentation.viewmodel.CardDeleteViewModel
import uz.gita.bank2.presentation.viewmodel.CardViewModel
import uz.gita.bank2.presentation.viewmodel.impl.CardDeleteViewModelImpl
import uz.gita.bank2.presentation.viewmodel.impl.CardViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast

@AndroidEntryPoint
class PageMainScreen : Fragment(R.layout.page_main) {
    private val binding by viewBinding(PageMainBinding::bind)
    private val viewModel: CardViewModel by viewModels<CardViewModelImpl>()
    private val viewModelDelete: CardDeleteViewModel by viewModels<CardDeleteViewModelImpl>()
    private val list = ArrayList<CardData>()
    private val adapter = CardAdapter(list)
    private lateinit var cardData: CardData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openEditScreenFlow.onEach {
            val bundle = Bundle()
            bundle.putParcelable("data", cardData)
            findNavController().navigate(R.id.cardEditScreen, bundle)
        }.launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        listCard.adapter = adapter
        viewModel.getAllCard()

        adapter.setClickListener { data, pos ->
            cardData = data
            viewModel.openEditScreen()
        }

        adapter.setLongClickListener { data, pos ->

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Delete")
                .setMessage("Do you really want to delete?")
                .setPositiveButton("Yes") { dialog, which ->
                    viewModelDelete.cardDelete(
                        DeleteCardRequest(
                            data.pan
                        )
                    )
                    list.removeAt(pos)
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

            dialog.show()
        }

        viewModel.allCardFlow.onEach {
            list.clear()
            list.addAll(it)
            adapter.notifyDataSetChanged()
        }.launchIn(lifecycleScope)

        viewModel.errorMessageFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.progressCardFlow.onEach {
            if (it) progressCard.show()
            else progressCard.hide()
        }.launchIn(lifecycleScope)

        viewModelDelete.successFlow.onEach {
            showToast("Success")
        }.launchIn(lifecycleScope)

        viewModelDelete.errorMessageFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModelDelete.progressFlow.onEach {
            if (it) progress.show()
            else progress.hide()
        }.launchIn(lifecycleScope)

    }
}