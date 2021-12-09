package uz.gita.bank2.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.gita.bank2.presentation.ui.screens.PageHistoryScreen
import uz.gita.bank2.presentation.ui.screens.PageMainScreen
import uz.gita.bank2.presentation.ui.screens.PageProfile
import uz.gita.bank2.presentation.ui.screens.PageTransferScreen

class MainPageAdapter(
    private val fm: FragmentManager,
    private val lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> PageMainScreen()
            1 -> PageTransferScreen()
            2 -> PageHistoryScreen()
            else -> PageProfile()
        }
    }
}