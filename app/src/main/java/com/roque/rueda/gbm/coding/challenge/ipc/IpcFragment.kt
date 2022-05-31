package com.roque.rueda.gbm.coding.challenge.ipc

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.android.material.snackbar.Snackbar
import com.roque.rueda.gbm.coding.challenge.R
import com.roque.rueda.gbm.coding.challenge.databinding.FragmentIpcBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class IpcFragment : Fragment() {

    private var _binding: FragmentIpcBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val ipcViewModel: IpcViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIpcBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        lifecycleScope.launchWhenResumed {
            ipcViewModel.fetchIpcIndex()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ipcViewModel.iuState.collect {
                    when (it) {
                        is IpcIndexUiState.Error -> showError(it)
                        IpcIndexUiState.Loading -> showLoading()
                        is IpcIndexUiState.Success -> showChartData(it)
                    }
                }
            }
        }
    }

    private fun showChartData(success: IpcIndexUiState.Success) {
        val data = success.ipcIndexList

        val anyChartView = binding.anyChartView
        anyChartView.setProgressBar(binding.loadingIndicator)

        val cartesian = AnyChart.line()
        cartesian.title(getString(R.string.ipc_chart_title))
        cartesian.yAxis(0).title(getString(R.string.price))
        cartesian.xAxis(0).title(getString(R.string.date))

        val chartData = data.map {
            ChartDataEntry(it.date.orEmpty(), it.price ?: 0.0)
        }

        cartesian.line(chartData)
        anyChartView.setChart(cartesian)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            anyChartView.layoutParams.width = windowMetrics.bounds.width() - 30
            anyChartView.layoutParams.height = windowMetrics.bounds.height() / 2
        } else {
            val displayMetrics = DisplayMetrics()
            val windowMetrics =
                requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            anyChartView.layoutParams.width = displayMetrics.widthPixels - 30
            anyChartView.layoutParams.height = displayMetrics.heightPixels / 2
        }
    }

    private fun showError(error: IpcIndexUiState.Error) {
        Snackbar.make(binding.anyChartView, error.errorResult.message, Snackbar.LENGTH_INDEFINITE)
            .setAnchorView(R.id.any_chart_view)
            .setAction("Retry") { ipcViewModel.fetchIpcIndex() }
            .show()
    }

    private fun showLoading() {
        binding.loadingIndicator.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private class ChartDataEntry(date: String, price: Number) : ValueDataEntry(date, price)
}