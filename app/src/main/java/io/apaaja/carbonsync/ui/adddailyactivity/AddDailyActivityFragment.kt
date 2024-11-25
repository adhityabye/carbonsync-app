package io.apaaja.carbonsync.ui.adddailyactivity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.apaaja.carbonsync.R

class AddDailyActivityFragment : Fragment() {

    companion object {
        fun newInstance() = AddDailyActivityFragment()
    }

    private lateinit var viewModel: AddDailyActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_daily_activity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddDailyActivityViewModel::class.java]
    }

}