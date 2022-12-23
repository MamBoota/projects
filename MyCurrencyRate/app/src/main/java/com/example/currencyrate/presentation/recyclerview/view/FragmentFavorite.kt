package com.example.currencyrate.presentation.recyclerview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyrate.databinding.FragmentFavoriteBinding
import com.example.currencyrate.datasource.remotedatasource.data.AdapterData
import com.example.currencyrate.presentation.mainactivity.viewmodel.MainActivityViewModel
import com.example.currencyrate.presentation.recyclerview.adapter.RecyclerAdapter
import com.example.currencyrate.presentation.recyclerview.viewmodel.RecyclerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class FragmentFavorite : Fragment(), RecyclerAdapter.RecyclerListener {

    private val adapter = RecyclerAdapter(this)

    val viewModel: RecyclerViewModel by activityViewModels()
    private val viewModelActivity: MainActivityViewModel by activityViewModels()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listFavorite.adapter = adapter

        viewModel.loadingData()

        lifecycleScope.launchWhenStarted {
            viewModelActivity.adapterDataStateFlow.collect { listNotNul ->
                adapter.submitList(listNotNul?.filter { it.isFavorite })
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.favoriteStateFlow.collect {
                adapter.submitList(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModelActivity.successSort.collect { sort ->
                if (sort) {
                    delay(300)
                    binding.listFavorite.scrollToPosition(0)
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

    companion object {
        @JvmStatic
        fun newInstance() = FragmentFavorite()
    }
}