package io.apaaja.carbonsync.ui.adddailyactivity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import io.apaaja.carbonsync.MainActivity
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.data.DailyActivitiesCarbonReduction
import io.apaaja.carbonsync.data.TravelActivitiesCarbonReduction

class AddDailyActivityFragment : Fragment() {

    companion object {
        fun newInstance() = AddDailyActivityFragment()
    }

    private lateinit var viewModel: AddDailyActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[AddDailyActivityViewModel::class.java]
        return inflater.inflate(R.layout.fragment_add_daily_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addDailyActivityButton: MaterialButton = view.findViewById(R.id.add_daily_activity_button)
        val dailyActivityDropdown: TextInputLayout =
            view.findViewById(R.id.daily_activity_dropdown)
        val autoCompleteTextView = dailyActivityDropdown.editText as? AutoCompleteTextView

        viewModel.selectedDailyActivity.observe(viewLifecycleOwner) { activity ->
            addDailyActivityButton.isEnabled = !activity.isNullOrEmpty()
        }

        autoCompleteTextView?.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                DailyActivitiesCarbonReduction.listString()
            )
        )

        autoCompleteTextView?.setOnItemClickListener { _, _, position, _ ->
            val selectedMode = DailyActivitiesCarbonReduction.listString()[position]
            viewModel.setDailyActivity(selectedMode)
        }

        addDailyActivityButton.setOnClickListener {
            if (autoCompleteTextView == null || autoCompleteTextView.text.toString() == "" || viewModel.selectedDailyActivity.value == null) return@setOnClickListener

            val mainActivity = activity as MainActivity
            mainActivity.addDailyActivityData(DailyActivitiesCarbonReduction.fromString(viewModel.selectedDailyActivity.value!!)!!)

            (activity as MainActivity).onBackPressed();
        }
    }

}