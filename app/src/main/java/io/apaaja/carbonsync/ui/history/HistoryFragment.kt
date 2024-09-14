package io.apaaja.carbonsync.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import java.time.LocalDate
import java.util.*

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
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

        currentCarbonReductionTextView = view.findViewById(R.id.textview_carbon_view_center)
        recyclerView = view.findViewById(R.id.history_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val historyList = arrayListOf(
            HistoryItem(LocalDate.now(), 5.5f),
            HistoryItem(LocalDate.now().minusDays(1), 10.3f),
            HistoryItem(LocalDate.now().minusDays(2), 7.8f)
        )
        historyAdapter = HistoryItemAdapter(historyList) { item ->
            val action = HistoryFragmentDirections.actionFragmentHistoryToFragmentHistoryDetails(
                date = HistoryItemAdapter.parseHistoryItemDate(item.date),
                value = item.value
            )
            findNavController().navigate(action)
        }

        recyclerView.adapter = historyAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        viewModel.currentCarbonReduction.observe(viewLifecycleOwner, { data ->
            currentCarbonReductionTextView.text = String.format("%.1fg", data)
        })
        viewModel.historyData.observe(viewLifecycleOwner, { data ->
            historyAdapter.updateData(data)
        })
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

    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

}