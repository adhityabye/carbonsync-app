package io.apaaja.carbonsync.ui.historydetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.databinding.FragmentDashboardBinding
import io.apaaja.carbonsync.databinding.FragmentHistoryDetailsBinding
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter

class HistoryDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryDetailsFragment()
    }

    private lateinit var viewModel: HistoryDetailsViewModel
    private var _binding: FragmentHistoryDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryDetailsViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val date = it.getString("date") ?: ""
            val value = it.getInt("value")

            binding.textviewCarbonViewTop.text = date
            binding.textviewCarbonViewCenter.text = IntegerNumberFormatter.condense(value)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}