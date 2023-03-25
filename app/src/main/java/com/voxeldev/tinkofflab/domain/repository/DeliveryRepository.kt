package com.voxeldev.tinkofflab.domain.repository

import com.voxeldev.tinkofflab.domain.functional.Either
import com.voxeldev.tinkofflab.domain.models.expressapi.AddressModel
import com.voxeldev.tinkofflab.domain.models.expressapi.TimeSlotModel

interface DeliveryRepository {

    /**
     * Gets user's favorite addresses
     * @return List of addresses or exception
     */
    fun getAddresses(): Either<Exception, List<AddressModel>>

    /**
     * Searches for address
     * @param address Address to search
     * @return List of addresses or exception
     */
    fun searchAddress(address: AddressModel): Either<Exception, List<AddressModel>>

    /**
     * Gets time slots for desired date
     * @param date Date to get slots for, format: YYYY-MM-DD
     * @return List of time slots or exception
     */
    fun getSlots(date: String): Either<Exception, List<TimeSlotModel>>
}
