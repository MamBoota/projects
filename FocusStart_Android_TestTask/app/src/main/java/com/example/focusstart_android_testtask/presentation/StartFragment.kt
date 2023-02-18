package com.example.focusstart_android_testtask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.focusstart_android_testtask.R
import com.example.focusstart_android_testtask.data.App
import com.example.focusstart_android_testtask.databinding.FragmentStartBinding
import com.example.focusstart_android_testtask.entity.Bins

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StartViewModel by viewModels {
        StartViewModel.StartViewModelFactory((context?.applicationContext as App).db.dictionaryDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            bSearch.setOnClickListener {
                val bundle = bundleOf("key" to binding.etSearch.text.toString())
                if (!viewModel.onSearchBtn(etSearch.text.toString())) {
                    etSearch.error = "Incorrect Input"
                } else {
                    findNavController().navigate(R.id.start_fragment_to_result_fragment, bundle)
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        viewModel.state.collect { state ->
                            when (state) {
                                State.Loading -> {
                                    pbProgress.isVisible = true
                                    bSearch.isEnabled = false
                                    etSearch.error = null
                                }
                                State.Success -> {
                                    pbProgress.isVisible = false
                                    bSearch.isEnabled = true
                                    etSearch.error = null
                                }
                            }
                        }
                    }
                }
            }
            bDelete.setOnClickListener { viewModel.onDeleteBtn() }

            lifecycleScope.launchWhenCreated {
                viewModel.allBin
                    .collect { bins: List<Bins> ->
                        val text = bins.map { it.bin to it.count }
                        tvHistoryOutput.text = text.joinToString(separator = "\r\n")
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}