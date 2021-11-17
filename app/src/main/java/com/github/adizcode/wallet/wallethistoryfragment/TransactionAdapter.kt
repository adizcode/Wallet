package com.github.adizcode.wallet.wallethistoryfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.adizcode.wallet.R
import com.github.adizcode.wallet.viewmodel.WalletViewModel

class TransactionAdapter(private val viewModel: WalletViewModel) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
        val transactionImageView: ImageView = view.findViewById(R.id.depositOrWithdraw)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        val transaction = viewModel.transactionHistory.value!![position]
        holder.amountTextView.text = transaction.amount.toString()
        holder.descriptionTextView.text = transaction.description

        val imageResource =
            if (transaction.isDeposit) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        holder.transactionImageView.setImageResource(imageResource)
    }

    override fun getItemCount(): Int {
        return viewModel.transactionHistory.value?.size ?: 0
    }
}