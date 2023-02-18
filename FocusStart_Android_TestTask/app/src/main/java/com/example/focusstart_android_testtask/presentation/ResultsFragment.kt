package com.example.focusstart_android_testtask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.focusstart_android_testtask.databinding.FragmentResultBinding
import com.example.focusstart_android_testtask.di.DaggerAppComponent

class ResultsFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultsViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = requireArguments().getString("key")!!
        val prepaidYes = "Yes"
        val prepaidNo = "No"
        val luhnYes = "Yes"
        val luhnNo = "No"

        viewModel.reloadBinActivity(query)

        with(binding) {
            tvOutputBin.text = query
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.bin.collect {
                    tvOutputLength.text = it?.number?.length.toString()
                    if (it?.number?.luhn == false) {
                        tvOutputLuhn.text = luhnNo
                    } else tvOutputLuhn.text = luhnYes
                    tvOutputSchemeNetwork.text = it?.scheme
                    tvOutputType.text = it?.type
                    tvOutputBrand.text = it?.brand
                    if (it?.prepaid == false) {
                        tvOutputPrepaid.text = prepaidNo
                    } else tvOutputPrepaid.text = prepaidYes
                    tvNameCountry.text = it?.country?.name
                    tvOutputLatitude.text = it?.country?.latitude.toString()
                    tvOutputLongitude.text = it?.country?.longitude.toString()
                    tvOutputNameBank.text = it?.bank?.name
                    tvOutputUrl.text = it?.bank?.url
                    tvOutputPhone.text = it?.bank?.phone
                    tvOutputCity.text = it?.bank?.city
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}