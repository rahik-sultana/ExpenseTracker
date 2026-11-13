package com.example.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DashboardFragment : Fragment() {

    private var selectedType: String = "EXPENSE"
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Personalized Greeting
        val tvGreeting = view.findViewById<TextView>(R.id.tvDashboardGreeting)
        val tvAvatar = view.findViewById<TextView>(R.id.tvHeaderAvatar)
        val fullName = UserManager.getUserName(requireContext())
        val firstName = fullName.split(" ").firstOrNull() ?: fullName

        if (firstName.isNotEmpty()) {
            tvGreeting.text = "Welcome Back, $firstName!"
            // Set initials in header avatar
            val initials = fullName.split(" ")
                .filter { it.isNotEmpty() }
                .take(2)
                .map { it.first().uppercase() }
                .joinToString("")
            tvAvatar.text = if (initials.isNotEmpty()) initials else "ET"
        } else {
            tvGreeting.text = "Welcome Back!"
            tvAvatar.text = "ET"
        }

        // 2. Form Views
        val etAmount = view.findViewById<EditText>(R.id.etAmount)
        val btnTypeExpense = view.findViewById<TextView>(R.id.btnTypeExpense)
        val btnTypeIncome = view.findViewById<TextView>(R.id.btnTypeIncome)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)
        val layoutDatePicker = view.findViewById<LinearLayout>(R.id.layoutDatePicker)
        val tvSelectedDate = view.findViewById<TextView>(R.id.tvSelectedDate)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val btnSaveTransaction = view.findViewById<MaterialButton>(R.id.btnSaveTransaction)

        // 3. Set today's date initially
        updateDateLabel(tvSelectedDate)

        // 4. Date Picker Dialog
        layoutDatePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateLabel(tvSelectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // 5. Segmented Type Selector Toggle
        btnTypeExpense.setOnClickListener {
            selectedType = "EXPENSE"
            btnTypeExpense.setBackgroundResource(R.drawable.bg_segment_expense_selected)
            btnTypeExpense.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            btnTypeIncome.setBackgroundResource(R.drawable.bg_segment_unselected)
            btnTypeIncome.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        }

        btnTypeIncome.setOnClickListener {
            selectedType = "INCOME"
            btnTypeIncome.setBackgroundResource(R.drawable.bg_segment_income_selected)
            btnTypeIncome.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            btnTypeExpense.setBackgroundResource(R.drawable.bg_segment_unselected)
            btnTypeExpense.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        }

        // 6. Save Transaction Click
        btnSaveTransaction.setOnClickListener {
            val amountText = etAmount.text.toString().trim()

            if (amountText.isEmpty()) {
                etAmount.error = "Please enter an amount"
                etAmount.requestFocus()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                etAmount.error = "Enter a valid amount"
                etAmount.requestFocus()
                return@setOnClickListener
            }

            val category = spinnerCategory.selectedItem?.toString() ?: "General"

            Toast.makeText(
                requireContext(),
                "Saved $selectedType: ৳ $amountText ($category)",
                Toast.LENGTH_SHORT
            ).show()

            etAmount.text.clear()
            etDescription.text.clear()
        }
    }

    private fun updateDateLabel(textView: TextView) {
        val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        textView.text = dateFormat.format(calendar.time)
    }
}
