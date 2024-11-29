package io.apaaja.carbonsync.ui.historydetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MediatorLiveData
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.data.CarbonReductionActivity
import io.apaaja.carbonsync.databinding.FragmentDashboardBinding
import io.apaaja.carbonsync.databinding.FragmentHistoryDetailsBinding
import io.apaaja.carbonsync.utils.converter.LocalDateConverter
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModel
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryDetailsFragment : Fragment() {
    companion object {
        fun newInstance() = HistoryDetailsFragment()
    }

    private lateinit var historyDetailsViewModel: HistoryDetailsViewModel
    private lateinit var carbonDataViewModel: CarbonDataViewModel
    private var _binding: FragmentHistoryDetailsBinding? = null
    private val binding get() = _binding!!
    private val dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyDetailsViewModel = ViewModelProvider(this, HistoryDetailsViewModelFactory(requireContext()))[HistoryDetailsViewModel::class.java]
        carbonDataViewModel = ViewModelProvider(
            requireActivity(),
            CarbonDataViewModelFactory((requireActivity() as MainActivity).getCarbonActivitiesRepository())
        )[CarbonDataViewModel::class.java]

        MediatorLiveData<Triple<LocalDate, Int, Int?>>().apply {
            addSource(historyDetailsViewModel.date) { date ->
                value = Triple(
                    date,
                    carbonDataViewModel.getTotalForDate(date),
                    carbonDataViewModel.dailyCarbonTarget.value
                )
            }
            addSource(carbonDataViewModel.activities) {
                val date = historyDetailsViewModel.date.value
                val target = carbonDataViewModel.dailyCarbonTarget.value
                if (date != null && target != null)
                    value = Triple(date, carbonDataViewModel.getTotalForDate(date), target)
            }
            addSource(carbonDataViewModel.dailyCarbonTarget) { target ->
                val date = historyDetailsViewModel.date.value
                if (date != null)
                    value = Triple(date, carbonDataViewModel.getTotalForDate(date), target)
            }
        }.observe(viewLifecycleOwner) { (date, carbonTotal, dailyCarbonTarget) ->
            binding.textviewCarbonViewDate.text = dateFormat.format(date)
            binding.textviewCarbonViewTargetValue.text = IntegerNumberFormatter.condense(dailyCarbonTarget ?: 0)
            binding.screenTimeSection.visibility = if (date == LocalDate.now()) View.VISIBLE else View.INVISIBLE
            binding.textviewCarbonViewValue.text = IntegerNumberFormatter.condense(carbonTotal)
            binding.progressCarbonView.progress = if (dailyCarbonTarget != null) carbonTotal * 100 / dailyCarbonTarget else 0
        }

        historyDetailsViewModel.screenTime.observe(viewLifecycleOwner) { screenTime ->
            binding.textviewScreenTimeView.text = getString(
                R.string.home_activity_screen_time_view_format,
                screenTime / (1000 * 60 * 60),
                (screenTime / (1000 * 60)) % 60
            )
        }

//        historyDetailsViewModel.date.observe(viewLifecycleOwner) { date ->
//            val carbonTotal = carbonDataViewModel.getTotalForDate(date)
//
//            binding.textviewCarbonViewDate.text = dateFormat.format(date)
//            binding.textviewCarbonViewValue.text = IntegerNumberFormatter.condense(carbonTotal)
//            binding.progressCarbonView.progress = if (carbonDataViewModel.dailyCarbonTarget.value != null) carbonTotal * 100 / carbonDataViewModel.dailyCarbonTarget.value!! else 0
//        }

        arguments?.let {
            val dateString = it.getString("date") ?: return
            val date = LocalDateConverter.toLocalDate(dateString) ?: return
            historyDetailsViewModel.setDate(date)
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