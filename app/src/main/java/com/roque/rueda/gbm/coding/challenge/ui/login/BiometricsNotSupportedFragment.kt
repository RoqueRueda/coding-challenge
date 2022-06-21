package com.roque.rueda.gbm.coding.challenge.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.roque.rueda.gbm.coding.challenge.databinding.FragmentBiometricsNotSupportedBinding

/**
 * Fragment that is used to show not biometrics support to user
 */
class BiometricsNotSupportedFragment : Fragment() {

    private var _binding: FragmentBiometricsNotSupportedBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBiometricsNotSupportedBinding.inflate(inflater, container, false)
        hideHomeAsUp()
        return binding.root
    }

    private fun hideHomeAsUp() {
        val appCompatActivity: AppCompatActivity? = activity as? AppCompatActivity
        appCompatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}