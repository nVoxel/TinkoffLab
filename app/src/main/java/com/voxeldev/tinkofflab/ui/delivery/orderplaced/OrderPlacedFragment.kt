package com.voxeldev.tinkofflab.ui.delivery.orderplaced

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.FragmentOrderPlacedBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.App
import com.voxeldev.tinkofflab.ui.Screens
import com.voxeldev.tinkofflab.ui.base.BaseFragment
import com.voxeldev.tinkofflab.ui.delivery.SharedOrderViewModel
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OrderPlacedFragment : BaseFragment<FragmentOrderPlacedBinding>() {

    private val sharedOrderViewModel: SharedOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderPlacedBinding.inflate(inflater, container, false)

        binding?.run {
            sharedOrderViewModel.getOrder()?.let {
                textviewWhere.text = it.address.address
                textviewWhen.text = timeSlotToString(it.deliverySlot, resources)
                textviewWhat.text = it.items.joinToString(", ") { itemModel ->
                    itemModel.name
                }
            }
            return root
        } ?: return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            buttonContinue.setOnClickListener {
                App.router.newRootScreen(Screens.Orders())
            }
        }
    }

    private fun timeSlotToString(timeSlotModel: TimeSlotModel, resources: Resources) =
        resources.getString(
            R.string.order_datetime_placeholder,
            LocalDate.parse(timeSlotModel.date, DateTimeFormatter.ISO_DATE)
                .toRelativeString(LocalDate.now(), resources),
            timeSlotModel.timeFrom,
            timeSlotModel.timeTo
        )
}
