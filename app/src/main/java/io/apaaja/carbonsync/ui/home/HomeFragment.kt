package io.apaaja.carbonsync.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.databinding.FragmentHomeBinding
import io.apaaja.carbonsync.ui.dashboard.DashboardViewModel
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModel

class HomeFragment : Fragment() {

    private lateinit var carbonDataViewModel: CarbonDataViewModel
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        carbonDataViewModel = ViewModelProvider(requireActivity())[CarbonDataViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupObservers()

        val carbonReductionCard: MaterialCardView = view.findViewById(R.id.card_carbon_reduction)
        carbonReductionCard.setOnClickListener(getCarbonReductionLayoutOnClickListener())
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
        carbonDataViewModel.currentDateCarbonData.observe(viewLifecycleOwner) {
            carbonData -> binding.textviewCarbonView.text = getString(R.string.home_carbon_view_format, carbonData.total())
        }
        homeViewModel.screenTime.observe(viewLifecycleOwner) {
            screenTime -> binding.textviewScreenTimeView.text =
                getString(R.string.home_activity_screen_time_view_format, screenTime.toHours(), screenTime.toMinutes() % 60)
        }
        homeViewModel.achievements.observe(viewLifecycleOwner) {
            achievements -> binding.textviewAchievementsView.text =
                getString(R.string.home_activity_achievements_view_format, achievements.first, achievements.second)
        }
    }

    private fun getCarbonReductionLayoutOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_fragment_history)
        }
    }

}