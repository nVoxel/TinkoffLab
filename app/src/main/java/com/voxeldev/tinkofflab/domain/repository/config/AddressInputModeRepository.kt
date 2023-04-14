package com.voxeldev.tinkofflab.domain.repository.config

import com.voxeldev.tinkofflab.data.local.datasource.SharedPrefsApi
import com.voxeldev.tinkofflab.domain.models.config.AddressInputMode

interface AddressInputModeRepository : SharedPrefsApi<AddressInputMode>
