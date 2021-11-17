package com.github.adizcode.wallet.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.adizcode.wallet.database.TransactionDao
import com.github.adizcode.wallet.model.Transaction
import kotlinx.coroutines.*

class WalletViewModel(private val databaseDao: TransactionDao, application: Application) :
    AndroidViewModel(application) {
    private val _currentBalance = MutableLiveData(0.0)
    val currentBalance: LiveData<Double>
        get() = _currentBalance

    private val _transactionHistory = databaseDao.getAllTransactions()
    val transactionHistory get() = _transactionHistory

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        initializeCurrentBalance()

        Log.i("ViewModel", "Initialized")
        Log.i("ViewModel", _transactionHistory.value?.size.toString())
    }

    private fun initializeCurrentBalance() {
        uiScope.launch {
            _currentBalance.value = getBalanceFromDatabase()
        }
    }

    private suspend fun getBalanceFromDatabase(): Double {
        return withContext(Dispatchers.IO) {
            databaseDao.getLastTransaction()?.balance ?: 0.0
        }
    }

    fun deposit(amount: String, description: String) {
        val amountInDouble = amount.toDoubleOrNull()

        if (amountInDouble != null && description.isNotBlank()) {
            deposit(Transaction(amountInDouble, true, description))
        }
    }

    private fun deposit(transaction: Transaction) {
        _currentBalance.value = _currentBalance.value!!.plus(transaction.amount)
        recordTransaction(transaction)
    }


    fun withdraw(amount: String, description: String) {
        val amountInDouble = amount.toDoubleOrNull()

        if (amountInDouble != null && description.isNotBlank())
            withdraw(Transaction(amountInDouble, false, description))
    }

    private fun withdraw(transaction: Transaction) {
        if (transaction.amount <= _currentBalance.value!!) {
            _currentBalance.value = _currentBalance.value!!.minus(transaction.amount)
            recordTransaction(transaction)
        }
    }

    private fun recordTransaction(transaction: Transaction) {
        transaction.balance = currentBalance.value!!

        uiScope.launch {
            addTransactionToDatabase(transaction)
        }
    }

    private suspend fun addTransactionToDatabase(transaction: Transaction) {
        withContext(Dispatchers.IO) {
            databaseDao.addTransaction(transaction)
            Log.i("ViewModel", "Added Transaction")
        }
    }
}