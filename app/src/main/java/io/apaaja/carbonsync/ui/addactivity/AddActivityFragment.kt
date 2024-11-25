package io.apaaja.carbonsync.ui.addactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import io.apaaja.carbonsync.R

class AddActivityFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dailyActivitiesCard: MaterialCardView = view.findViewById(R.id.card_daily_activities)
        val transportationCard: MaterialCardView = view.findViewById(R.id.card_transportation)

        transportationCard.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_navigation_add_activity_to_navigation_add_transportation_activity)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddActivityFragment()
    }
}