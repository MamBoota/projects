package com.example.currencyrate.presentation.recyclerview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyrate.databinding.FragmentPopularBinding
import com.example.currencyrate.presentation.recyclerview.adapter.RecyclerAdapter
import com.example.currencyrate.presentation.recyclerview.viewmodel.RecyclerViewModel
import com.example.currencyrate.datasource.remotedatasource.data.AdapterData
import com.example.currencyrate.presentation.mainactivity.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FragmentPopular : Fragment(), RecyclerAdapter.RecyclerListener {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RecyclerViewModel by activityViewModels()

    private val viewModelActivity: MainActivityViewModel by activityViewModels()

    private val adapter = RecyclerAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listCurrency.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModelActivity.adapterDataStateFlow.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.popularStateFlow.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModelActivity.successSort.collect { sort ->
                if (sort) {
                    delay(300)
                    binding.listCurrency.scrollToPosition(0)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(data: AdapterData) {
        viewModel.favoriteExists(data)
    }

    companion object{
        @JvmStatic
        fun newInstance() = FragmentPopular()
    }
}







