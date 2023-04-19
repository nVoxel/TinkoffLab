package com.voxeldev.tinkofflab.data.local

import android.content.Context
import androidx.core.content.edit
import com.voxeldev.tinkofflab.domain.models.config.AddressInputMode
import com.voxeldev.tinkofflab.domain.repository.config.AddressInputModeRepository
import com.voxeldev.tinkofflab.utils.functional.Either
import java.io.IOException
import javax.inject.Inject

class AddressInputModeRepositoryImpl @Inject constructor(
    private val context: Context
) : AddressInputModeRepository {

    override fun getValue(): Either<IOException, AddressInputMode> =
        try {
            Either.Right(
                getPrefs()
                    .getBoolean(ADDRESS_INPUT_KEY, true)
                    .toAddressInputMode()
            )
        } catch (e: IOException) {
            Either.Left(e)
        }

    override fun setValue(value: AddressInputMode): Either<IOException, Unit> =
        try {
            Either.Right(
                getPrefs().edit(true) {
                    putBoolean(ADDRESS_INPUT_KEY, value.toBoolean())
                }
            )
        } catch (e: IOException) {
            Either.Left(e)
        }

    private fun getPrefs() =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    companion object {

        private const val ADDRESS_INPUT_KEY = "ADDRESS_INPUT_KEY"

        fun Boolean.toAddressInputMode() =
            if (this) AddressInputMode.AUTOFILL else AddressInputMode.MANUAL

        fun AddressInputMode.toBoolean() =
            when (this) {
                AddressInputMode.AUTOFILL -> true
                AddressInputMode.MANUAL -> false
            }
    }
}
