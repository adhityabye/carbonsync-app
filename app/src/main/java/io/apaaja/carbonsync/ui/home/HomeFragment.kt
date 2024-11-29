package io.apaaja.carbonsync.ui.home

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.data.DailyCarbonData
import io.apaaja.carbonsync.databinding.FragmentHomeBinding
import io.apaaja.carbonsync.repository.CarbonActivitiesRepository
import io.apaaja.carbonsync.ui.dashboard.DashboardViewModel
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModel
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModelFactory
import java.time.LocalDate

class HomeFragment : Fragment() {

    private lateinit var carbonDataViewModel: CarbonDataViewModel
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!hasUsageStatsPermission(requireContext())) requestUsageStatsPermission(requireContext())

        carbonDataViewModel = ViewModelProvider(
            requireActivity(),
            CarbonDataViewModelFactory((requireActivity() as MainActivity).getCarbonActivitiesRepository())
        )[CarbonDataViewModel::class.java]
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(requireContext())
        )[HomeViewModel::class.java]

        setupObservers()

        val carbonReductionCard: MaterialCardView = view.findViewById(R.id.card_carbon_reduction)
        val activitiesCard: MaterialCardView = view.findViewById(R.id.card_activities)
        val historyCard: MaterialCardView = view.findViewById(R.id.card_history)
        val achievementsCard: MaterialCardView = view.findViewById(R.id.card_achievements)

        carbonReductionCard.setOnClickListener {
            val currentDate = LocalDate.now().toString() // Get current date in ISO-8601 format
            val action =
                HomeFragmentDirections.actionNavigationHomeToFragmentHistoryDetails(date = currentDate)
            findNavController().navigate(action)
        }
        historyCard.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_fragment_history)
        }
        achievementsCard.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_achievementFragment)
        }
        // activitiesCard.setOnClickListener(getCarbonReductionLayoutOnClickListener())

        startEntryAnimation(carbonReductionCard, activitiesCard, historyCard, achievementsCard)
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        carbonDataViewModel.currentDayTotalCarbonReduction.observe(viewLifecycleOwner) { total ->
            binding.textviewCarbonView.text = IntegerNumberFormatter.condense(total)
        }
        homeViewModel.displayName.observe(viewLifecycleOwner) { displayName ->
            binding.textviewDisplayName.text = displayName
        }
        homeViewModel.screenTime.observe(viewLifecycleOwner) { screenTime ->
            binding.textviewScreenTimeView.text =
                getString(
                    R.string.home_activity_screen_time_view_format,
                    screenTime / (1000 * 60 * 60),
                    (screenTime / (1000 * 60)) % 60
                )
        }
        homeViewModel.batteryLevel.observe(viewLifecycleOwner) { batteryLevel ->
            binding.textviewBatteryView.text =
                getString(R.string.home_activity_battery_level_view_format, batteryLevel)
            binding.batteryProgress.progress = batteryLevel
        }

        MediatorLiveData<Pair<Int?, Int?>>().apply {
            addSource(carbonDataViewModel.currentDayTotalCarbonReduction) { currentDateData ->
                value = Pair(currentDateData, carbonDataViewModel.dailyCarbonTarget.value)
            }
            addSource(carbonDataViewModel.dailyCarbonTarget) { dailyTarget ->
                value = Pair(carbonDataViewModel.currentDayTotalCarbonReduction.value, dailyTarget)
            }
        }.observe(viewLifecycleOwner) { (currentDateData, dailyTarget) ->
            val target = dailyTarget ?: 0
            binding.progressCarbonView.progress =
                if (target == 0) 0 else (currentDateData ?: 0) * 100 / target
        }
    }

    private fun getCarbonReductionLayoutOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_fragment_history)
        }
    }

    private fun hasUsageStatsPermission(context: Context): Boolean {
        val appOpsManager = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOpsManager.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageStatsPermission(context: Context) {
        if (!hasUsageStatsPermission(context)) {
            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    private fun startEntryAnimation(vararg cards: MaterialCardView) {
        val delay = 100L

        cards.forEach { it.visibility = View.INVISIBLE }

        Handler(Looper.getMainLooper()).apply {
            cards.forEachIndexed { index, card ->
                postDelayed({
                    card.visibility = View.VISIBLE
                    card.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in))
                }, delay * index)
            }
        }
    }

}