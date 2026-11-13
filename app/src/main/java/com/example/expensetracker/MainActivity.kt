package com.example.expensetracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Track current selected transaction type ("EXPENSE" or "INCOME")
    private var selectedType: String = "EXPENSE"

    // Calendar instance to hold selected date
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Initialize Views
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnTypeExpense = findViewById<TextView>(R.id.btnTypeExpense)
        val btnTypeIncome = findViewById<TextView>(R.id.btnTypeIncome)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val layoutDatePicker = findViewById<LinearLayout>(R.id.layoutDatePicker)
        val tvSelectedDate = findViewById<TextView>(R.id.tvSelectedDate)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val btnSaveTransaction = findViewById<MaterialButton>(R.id.btnSaveTransaction)

        // 2. Set today's date initially
        updateDateLabel(tvSelectedDate)

        // 3. Date Picker Click Listener
        layoutDatePicker.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
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

        // 4. Segmented Capsule Toggle: Expense vs Income
        btnTypeExpense.setOnClickListener {
            selectedType = "EXPENSE"
            btnTypeExpense.setBackgroundResource(R.drawable.bg_segment_expense_selected)
            btnTypeExpense.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnTypeIncome.setBackgroundResource(R.drawable.bg_segment_unselected)
            btnTypeIncome.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
        }

        btnTypeIncome.setOnClickListener {
            selectedType = "INCOME"
            btnTypeIncome.setBackgroundResource(R.drawable.bg_segment_income_selected)
            btnTypeIncome.setTextColor(ContextCompat.getColor(this, R.color.white))

            btnTypeExpense.setBackgroundResource(R.drawable.bg_segment_unselected)
            btnTypeExpense.setTextColor(ContextCompat.getColor(this, R.color.text_secondary))
        }

        // 5. Save Transaction Button
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

            val selectedCategory = spinnerCategory.selectedItem?.toString() ?: "General"
            val description = etDescription.text.toString().trim()

            // Feedback to the user
            Toast.makeText(
                this,
                "Saved $selectedType: ৳ $amountText ($selectedCategory)",
                Toast.LENGTH_SHORT
            ).show()

            // Clear inputs for quick new entry
            etAmount.text.clear()
            etDescription.text.clear()
        }
    }

    // Helper function to format and show date cleanly (e.g., "13 Nov, 2026")
    private fun updateDateLabel(textView: TextView) {
        val dateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        textView.text = dateFormat.format(calendar.time)
    }
}
