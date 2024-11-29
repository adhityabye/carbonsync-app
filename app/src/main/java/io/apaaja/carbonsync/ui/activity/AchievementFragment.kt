package io.apaaja.carbonsync.ui.activity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModel
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModelFactory

class AchievementFragment : Fragment() {

    companion object {
        fun newInstance() = AchievementFragment()
    }

    private lateinit var viewModel: AchievementViewModel
    private lateinit var carbonDataViewModel: CarbonDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achievement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AchievementViewModel::class.java]
        carbonDataViewModel = ViewModelProvider(
            requireActivity(),
            CarbonDataViewModelFactory((requireActivity() as MainActivity).getCarbonActivitiesRepository())
        )[CarbonDataViewModel::class.java]

        val totalValue: TextView = view.findViewById(R.id.textview_achievement_total_value)
        val totalProgress: ProgressBar = view.findViewById(R.id.progressbar_achievement_total)
        val highestValue: TextView = view.findViewById(R.id.textview_achievement_highest_value)
        val highestProgress: ProgressBar = view.findViewById(R.id.progressbar_achievement_highest_progress)

        val combinedTotalData = MediatorLiveData<Pair<Int, Int?>>()

        combinedTotalData.addSource(viewModel.totalTarget) { totalTarget ->
            combinedTotalData.value = Pair(totalTarget ?: 0, carbonDataViewModel.getTotal())
        }

        combinedTotalData.addSource(carbonDataViewModel.totalData) { totalData ->
            combinedTotalData.value = Pair(viewModel.totalTarget.value ?: 0, totalData)
        }

        combinedTotalData.observe(viewLifecycleOwner) { (totalTarget, totalData) ->
            totalValue.text = String.format(getString(R.string.achievements_progress_format),
                IntegerNumberFormatter.condense(totalData ?: 0),
                IntegerNumberFormatter.condense(totalTarget ?: 0))
            totalProgress.progress = if (totalTarget > 0) (totalData ?: 0) * 100 / totalTarget else 0
        }

        val combinedHighestData = MediatorLiveData<Pair<Int, Int?>>()

        combinedHighestData.addSource(viewModel.highestTarget) { totalTarget ->
            combinedHighestData.value = Pair(totalTarget ?: 0, carbonDataViewModel.highestDailyReduction.value)
        }

        combinedHighestData.addSource(carbonDataViewModel.highestDailyReduction) { totalData ->
            combinedHighestData.value = Pair(viewModel.highestTarget.value ?: 0, totalData)
        }

        combinedHighestData.observe(viewLifecycleOwner) { (highestTarget, highestData) ->
            highestValue.text = String.format(getString(R.string.achievements_progress_format),
                IntegerNumberFormatter.condense(highestData ?: 0),
                IntegerNumberFormatter.condense(highestTarget ?: 0))
            highestProgress.progress = if (highestData ?: 0 > 0) (highestData ?: 0) * 100 / highestTarget else 0
        }
    }



}