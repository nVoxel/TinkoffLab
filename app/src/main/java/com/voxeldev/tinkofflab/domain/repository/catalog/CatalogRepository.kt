package com.voxeldev.tinkofflab.domain.repository.catalog

import com.voxeldev.tinkofflab.data.local.datasource.ReadOnlyApi
import com.voxeldev.tinkofflab.domain.models.catalog.CatalogItemModel

interface CatalogRepository : ReadOnlyApi<List<CatalogItemModel>>
