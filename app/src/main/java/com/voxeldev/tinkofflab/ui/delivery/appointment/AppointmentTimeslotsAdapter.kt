package com.voxeldev.tinkofflab.ui.delivery.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.voxeldev.tinkofflab.R
import com.voxeldev.tinkofflab.databinding.ItemTimeslotBinding
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel
import com.voxeldev.tinkofflab.ui.utils.toRelativeString
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AppointmentTimeslotsAdapter(
    private val timeslots: List<TimeSlotModel>,
    private val onItemClickCallback: (TimeSlotModel) -> Unit = {}
) : RecyclerView.Adapter<AppointmentTimeslotsAdapter.ViewHolder>() {

    private var selectedItemPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemTimeslotBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) {
            if (selectedItemPosition == it) return@ViewHolder

            val previousPosition = selectedItemPosition
            selectedItemPosition = it

            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedItemPosition)

            onItemClickCallback(timeslots[it])
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(timeslots[position], position)
    }

    override fun getItemCount(): Int = timeslots.size

    inner class ViewHolder(
        private val binding: ItemTimeslotBinding,
        private val onItemClickCallback: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(timeSlot: TimeSlotModel, position: Int) {
            with(binding) {
                textviewTime.text =
                    binding.root.resources.getString(
                        R.string.timeslot_time_placeholder,
                        timeSlot.timeFrom, timeSlot.timeTo
                    )
                textviewDate.text = LocalDate
                    .parse(timeSlot.date, DateTimeFormatter.ISO_DATE)
                    .toRelativeString(LocalDate.now(), root.resources)

                root.setOnClickListener {
                    onItemClickCallback(position)
                }

                root.strokeWidth = if (selectedItemPosition == position) 2 else 0
            }
        }
    }
}
