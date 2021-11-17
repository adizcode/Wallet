package com.github.adizcode.wallet.wallethistoryfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.adizcode.wallet.R
import com.github.adizcode.wallet.viewmodel.WalletViewModel

class WalletHistoryFragment : Fragment(R.layout.fragment_wallet_history_fragment) {

    private lateinit var recyclerView: RecyclerView

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[WalletViewModel::class.java]

        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = TransactionAdapter(viewModel)
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.transactionHistory.observe(viewLifecycleOwner, {
            recyclerView.adapter!!.notifyDataSetChanged()
        })
    }
}