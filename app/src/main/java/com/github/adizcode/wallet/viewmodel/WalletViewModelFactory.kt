package com.github.adizcode.wallet.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.adizcode.wallet.database.TransactionDao

class WalletViewModelFactory(
    private val databaseDao: TransactionDao,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WalletViewModel::class.java)) {
            return WalletViewModel(databaseDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}