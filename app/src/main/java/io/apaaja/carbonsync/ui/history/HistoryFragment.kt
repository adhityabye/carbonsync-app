package io.apaaja.carbonsync.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.utils.converter.LocalDateConverter
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModel
import java.util.*

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var carbonDataViewModel: CarbonDataViewModel
    private lateinit var historyAdapter: HistoryItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var currentCarbonReductionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentCarbonReductionTextView = view.findViewById(R.id.textview_carbon_view_value)
        recyclerView = view.findViewById(R.id.history_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val mockDataButton: Button = view.findViewById(R.id.mock_data_button)
        mockDataButton.setOnClickListener { carbonDataViewModel.randomizeData() }

        historyAdapter = HistoryItemAdapter { item ->
            val action = LocalDateConverter.fromLocalDate(item.first)?.let {
                HistoryFragmentDirections.actionFragmentHistoryToFragmentHistoryDetails(
                    date = it
                )
            }
            if (action != null) findNavController().navigate(action)
        }

        recyclerView.adapter = historyAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        carbonDataViewModel = (requireActivity() as MainActivity).carbonDataViewModel
        carbonDataViewModel.currentDayTotalCarbonReduction.observe(viewLifecycleOwner) {
            carbonData -> currentCarbonReductionTextView.text = IntegerNumberFormatter.condense(carbonData)
        }
        carbonDataViewModel.historicalTotalCarbonReduction.observe(viewLifecycleOwner) { data ->
            historyAdapter.updateData(data)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? MainActivity)?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                navigateBack()
            }
        })
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

}