package com.github.adizcode.wallet.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.adizcode.wallet.model.Transaction

@Dao
interface TransactionDao {

    @Insert
    fun addTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions_table ORDER BY transaction_id DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions_table ORDER BY transaction_id DESC LIMIT 1")
    fun getLastTransaction(): Transaction?
}