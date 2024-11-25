package io.apaaja.carbonsync.ui.addtransportationactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.data.TravelActivitiesCarbonReduction
import io.apaaja.carbonsync.ui.adddailyactivity.AddDailyActivityViewModel

class AddTransportationActivityFragment : Fragment() {
    private lateinit var viewModel: AddTransportationActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AddTransportationActivityViewModel::class.java]
        return inflater.inflate(R.layout.fragment_add_transportation_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startTransportButton: MaterialButton = view.findViewById(R.id.start_transport_button)
        val transportationModeDropdown: TextInputLayout =
            view.findViewById(R.id.transportation_mode_dropdown)
        val autoCompleteTextView = transportationModeDropdown.editText as? AutoCompleteTextView

        viewModel.selectedTransportMode.observe(viewLifecycleOwner) { mode ->
            startTransportButton.isEnabled = !mode.isNullOrEmpty()
        }

        autoCompleteTextView?.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                TravelActivitiesCarbonReduction.listString()
            )
        )

        autoCompleteTextView?.setOnItemClickListener { _, _, position, _ ->
            val selectedMode = TravelActivitiesCarbonReduction.listString()[position]
            viewModel.setTransportMode(selectedMode)
        }

        startTransportButton.setOnClickListener {
            if (autoCompleteTextView == null || autoCompleteTextView.text.toString() == "") return@setOnClickListener

            val mainActivity = activity as MainActivity
            val intent = mainActivity.getIntentForMapsActivity()
            intent.putExtra("TRANSPORT_MODE", autoCompleteTextView.text.toString())
            mainActivity.startMapsActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddTransportationActivityFragment()
    }
}