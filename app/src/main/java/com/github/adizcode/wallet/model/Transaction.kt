package com.github.adizcode.wallet.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transaction(
    @ColumnInfo
    val amount: Double,

    @ColumnInfo(name = "is_deposit")
    val isDeposit: Boolean,

    @ColumnInfo
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    var transactionId: Long = 0

    @ColumnInfo
    var balance: Double = 0.0
}
