package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.widget.textChanges
import uz.gita.bank2.R
import uz.gita.bank2.data.request.card.AddCardRequest
import uz.gita.bank2.databinding.CardAddScreenBinding
import uz.gita.bank2.presentation.viewmodel.AddCardViewModel
import uz.gita.bank2.presentation.viewmodel.impl.AddCardViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast

@AndroidEntryPoint
class CardAddScreen : Fragment(R.layout.card_add_screen) {
    private val binding by viewBinding(CardAddScreenBinding::bind)
    private val viewModel: AddCardViewModel by viewModels<AddCardViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        combine(
            cardNumberEditText.textChanges().map {
                pan.text = it
                it.length == 19
            },
            termEditText.textChanges().map {
                term.text = it
                it.length == 5 && it.startsWith("12/21")
            },
            cardNameEditText.textChanges().map {
                name.text = it
                it.isNotEmpty() && it.length < 20
            },
            transform = { number, term, name ->
                number && term && name
            }
        ).onEach { loginBtn.isEnabled = it }.launchIn(lifecycleScope)

        loginBtn.setOnClickListener {
            viewModel.cardAdd(
                AddCardRequest(
                    "8600${cardNumberEditText.rawText}",
                    termEditText.text.toString(),
                    cardNameEditText.text.toString()
                )
            )
        }

        viewModel.errorMessageFlow.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.progressFlow.onEach {
            if (it) progress.show()
            else progress.hide()
        }.launchIn(lifecycleScope)

        viewModel.successFlow.onEach {
            val bundle = Bundle()
            bundle.putString("pan", "8600${cardNumberEditText.rawText}")
            findNavController().navigate(R.id.cardVerifyScreen, bundle)
        }.launchIn(lifecycleScope)
    }
}