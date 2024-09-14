package io.apaaja.carbonsync.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // listen to changes in carbon value
        homeViewModel.carbonReduction.observe(viewLifecycleOwner, getCarbonReductionValueObserver())

        // make it clickable
        binding.layoutCarbonViewTop.setOnClickListener(getCarbonReductionLayoutOnClickListener())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCarbonReductionValueObserver(): Observer<Float> {
        return Observer<Float> {
            binding.textviewCarbonViewCenter.text = String.format("%.1fg", it)
        }
    }

    private fun getCarbonReductionLayoutOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_fragment_history)
        }
    }

}