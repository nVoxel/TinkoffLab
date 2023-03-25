package com.voxeldev.tinkofflab.domain.models.expressapi

data class OrderModel(
    val address: AddressModel,
    val paymentMethod: String,
    val deliverySlot: TimeSlotModel,
    val items: List<ItemModel>,
    val comment: String,
    val id: Int
)
