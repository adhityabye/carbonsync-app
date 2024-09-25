package io.apaaja.carbonsync.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.databinding.FragmentHomeBinding
import io.apaaja.carbonsync.viewmodel.CarbonDataViewModel

class HomeFragment : Fragment() {

    private lateinit var carbonDataViewModel: CarbonDataViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        carbonDataViewModel = ViewModelProvider(requireActivity())[CarbonDataViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupObservers()

        // make it clickable
        binding.layoutCarbonViewTop.setOnClickListener(getCarbonReductionLayoutOnClickListener())
        binding.communityButton.setOnClickListener(View.OnClickListener { (activity as MainActivity).openCommunityActivity(requireView()); })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers() {
        carbonDataViewModel.currentDateCarbonData.observe(viewLifecycleOwner) {
            carbonData -> binding.textviewCarbonViewCenter.text = getString(R.string.home_carbon_view_center_format, carbonData.total())
        }
    }

    private fun getCarbonReductionLayoutOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_fragment_history)
        }
    }

}