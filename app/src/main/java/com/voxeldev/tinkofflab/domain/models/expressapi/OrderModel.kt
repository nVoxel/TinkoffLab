package com.voxeldev.tinkofflab.domain.models.expressapi

data class OrderModel(
    val address: AddressModel,
    val paymentMethod: String,
    val deliverySlot: TimeSlotModel,
    val items: List<ItemModel>,
    val comment: String,
    val status: String,
    val id: Int
) {
    companion object {
        const val NEW_ORDER_STATUS = "NEW"
        const val CANCELED_ORDER_STATUS = "CANCELED"
    }

    class Builder {
        private var address: AddressModel? = null
        private var paymentMethod: String? = null
        private var deliverySlot: TimeSlotModel? = null
        private var items: List<ItemModel>? = null
        private var comment: String? = null
        private var status: String? = null
        private var id: Int? = null

        fun address(address: AddressModel) = apply { this.address = address }
        fun paymentMethod(paymentMethod: String) = apply { this.paymentMethod = paymentMethod }
        fun deliverySlot(deliverySlot: TimeSlotModel) = apply { this.deliverySlot = deliverySlot }
        fun items(items: List<ItemModel>) = apply { this.items = items }
        fun comment(comment: String) = apply { this.comment = comment }
        fun status(status: String) = apply { this.status = status }
        fun id(id: Int) = apply { this.id = id }

        fun build() = OrderModel(
            address = address!!,
            paymentMethod = paymentMethod!!,
            deliverySlot = deliverySlot!!,
            items = items!!,
            comment = comment!!,
            status = status!!,
            id = id ?: 0
        )
    }
}
