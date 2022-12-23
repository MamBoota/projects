package com.example.currencyrate.presentation.mainactivity.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.currencyrate.R
import com.example.currencyrate.app.listCurrencyName
import com.example.currencyrate.databinding.ActivityMainBinding
import com.example.currencyrate.datasource.remotedatasource.data.AdapterData
import com.example.currencyrate.presentation.mainactivity.adapter.ViewPagerAdapter
import com.example.currencyrate.presentation.mainactivity.viewmodel.MainActivityViewModel
import com.example.currencyrate.presentation.recyclerview.view.FragmentFavorite
import com.example.currencyrate.presentation.recyclerview.view.FragmentPopular
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val listFragments: List<Fragment> =
        listOf(FragmentPopular.newInstance(), FragmentFavorite.newInstance())

    private val viewModel: MainActivityViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getAllCurrency(binding.textSelectedCurrency.text.toString())


        var comparator =
            Comparator { p1: AdapterData, p2: AdapterData -> p1.name.compareTo(p2.name) }
        var checkedItemSort = 0

        binding.btnSort.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.sortBy)
                .setNeutralButton(R.string.cancel) { _, _ ->
                }
                .setSingleChoiceItems(R.array.sortingSelectionArray, checkedItemSort) { _, which ->
                    checkedItemSort = which
                    comparator = when (checkedItemSort) {
                        0 -> Comparator { p1: AdapterData, p2: AdapterData -> p1.name.compareTo(p2.name) }
                        1 -> Comparator { p1: AdapterData, p2: AdapterData -> p2.name.compareTo(p1.name) }
                        2 -> Comparator { p1: AdapterData, p2: AdapterData -> p1.value.compareTo(p2.value) }
                        else -> Comparator { p1: AdapterData, p2: AdapterData ->
                            p2.value.compareTo(
                                p1.value
                            )
                        }
                    }
                }
                .setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.sortDataBy(comparator)
                }.show()
        }


        var checkedItem = 0
        binding.textSelectedCurrency.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.selectCurrency)
                .setNeutralButton(R.string.cancel) { _, _ ->
                }
                .setPositiveButton(R.string.ok) { _, _ ->
                    viewModel.getAllCurrency(binding.textSelectedCurrency.text.toString())
                }
                .setSingleChoiceItems(listCurrencyName, checkedItem) { _, which ->
                    binding.textSelectedCurrency.text = listCurrencyName[which]
                    checkedItem = which
                }.show()
        }


        val adapter = ViewPagerAdapter(this, listFragments)
        binding.viewPager.adapter = adapter

        val listTab = listOf(R.string.all, R.string.favorite)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.setText(listTab[pos])
        }.attach()

        lifecycleScope.launchWhenStarted {
            viewModel.progressBarOn.collectLatest {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
            viewModel.requestError.collectLatest {
                if (it) {
                    binding.cardView.visibility = View.VISIBLE
                }
            }
        }

        binding.btnRepeatRequest.setOnClickListener {
            viewModel.getAllCurrency(binding.textSelectedCurrency.text.toString())
            binding.cardView.visibility = View.GONE
        }

        binding.btnUpdateList.setOnClickListener {
            viewModel.getAllCurrency(binding.textSelectedCurrency.text.toString())
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}