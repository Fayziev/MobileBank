package uz.gita.bank2.presentation.ui.screens

import android.os.Bundle
import android.os.Parcelable
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
import uz.gita.bank2.data.request.card.EditCardRequest
import uz.gita.bank2.data.response.card.CardData
import uz.gita.bank2.databinding.CardEditScreenBinding
import uz.gita.bank2.presentation.viewmodel.CardEditViewModel
import uz.gita.bank2.presentation.viewmodel.impl.CardEditViewModelImpl
import uz.gita.bank2.utils.scope
import uz.gita.bank2.utils.showToast

@AndroidEntryPoint
class CardEditScreen : Fragment(R.layout.card_edit_screen) {
    private val binding by viewBinding(CardEditScreenBinding::bind)
    private val viewModel: CardEditViewModel by viewModels<CardEditViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.successFlow.onEach {
            showToast("Success")
            findNavController().navigate(R.id.mainScreen)
        }.launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        val bundle = requireArguments()
        val data = bundle.getParcelable<Parcelable>("data") as CardData
        pan.text = data.pan
        money.text = data.balance.toString()
        name.text = data.owner
        cardName.text = data.cardName
        term.text = data.exp
        combine(
            cardNameEditText.textChanges().map {
                cardName.text = it
                it.isNotEmpty() && it.length < 20
            },
            transform = { name ->
                name
            }
        ).onEach { loginBtn.isEnabled = it[0] }.launchIn(lifecycleScope)

        loginBtn.setOnClickListener {
            viewModel.cardEdit(
                EditCardRequest(
                    data.pan,
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

    }
}