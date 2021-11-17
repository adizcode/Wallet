package com.github.adizcode.wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.adizcode.wallet.database.AppDatabase
import com.github.adizcode.wallet.viewmodel.WalletViewModel
import com.github.adizcode.wallet.viewmodel.WalletViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val databaseDao = AppDatabase.getInstance(this).transactionDao()
        val factory = WalletViewModelFactory(databaseDao, application)
        ViewModelProvider(this, factory)[WalletViewModel::class.java]
    }
}