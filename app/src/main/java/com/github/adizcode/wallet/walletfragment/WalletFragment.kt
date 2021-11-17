package com.github.adizcode.wallet.walletfragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.adizcode.wallet.R
import com.github.adizcode.wallet.wallethistoryfragment.WalletHistoryFragment
import com.github.adizcode.wallet.viewmodel.WalletViewModel

class WalletFragment : Fragment(R.layout.fragment_wallet) {
    private lateinit var amountEditText: EditText
    private lateinit var descriptionEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[WalletViewModel::class.java]

        val currentBalance = view.findViewById<TextView>(R.id.currentBalanceValue)
        val depositButton = view.findViewById<Button>(R.id.depositButton)
        val withdrawButton = view.findViewById<Button>(R.id.withdrawButton)
        amountEditText = view.findViewById(R.id.amountInput)
        descriptionEditText = view.findViewById(R.id.descriptionInput)

        viewModel.currentBalance.observe(viewLifecycleOwner, {
            Log.i("WalletFragment", "Observer Notified")
            currentBalance.text = it.toString()
        })

        depositButton.setOnClickListener {
            Log.i("WalletFragment", "Deposit Clicked")

            // Read the contents of the amountEditText
            val amount = amountEditText.text.toString()
            val description = descriptionEditText.text.toString()

            // Deposit the amount
            viewModel.deposit(amount, description)

            // Clear input fields
            clearInputFields()
        }

        withdrawButton.setOnClickListener {
            Log.i("WalletFragment", "Withdraw Clicked")

            // Read the contents of the amountEditText
            val amount = amountEditText.text.toString()
            val description = descriptionEditText.text.toString()

            // Withdraw the amount
            viewModel.withdraw(amount, description)

            // Clear input fields
            clearInputFields()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.wallet_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.history) {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, WalletHistoryFragment())
                .addToBackStack(null)
                .commit()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun clearInputFields() {
        amountEditText.setText("")
        descriptionEditText.setText("")
    }
}