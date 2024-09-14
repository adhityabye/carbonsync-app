package io.apaaja.carbonsync.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.history_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val historyList = arrayListOf(
            HistoryItem(LocalDate.now(), 5.5f),
            HistoryItem(LocalDate.now().minusDays(1), 10.3f),
            HistoryItem(LocalDate.now().minusDays(2), 7.8f)
        )
        historyAdapter = HistoryItemAdapter(historyList)

        recyclerView.adapter = historyAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        viewModel.historyData.observe(viewLifecycleOwner, { data ->
            historyAdapter.updateData(data)
        })
    }

}