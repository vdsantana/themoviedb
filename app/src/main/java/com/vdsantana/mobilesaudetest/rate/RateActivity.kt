package com.vdsantana.mobilesaudetest.rate

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import com.vdsantana.mobilesaudetest.R
import kotlinx.android.synthetic.main.activity_rate.*
import java.util.*


class RateActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    @Suppress("DEPRECATION")
    private var progressDialog: ProgressDialog? = null

    private val calendar by lazy { Calendar.getInstance() }
    private var currentMonth = calendar.get(Calendar.MONTH)
    private var currentYear = calendar.get(Calendar.YEAR)
    private var currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate)
        setupView()
    }

    private fun setupView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btSend.setOnClickListener {
            if (progressDialog == null)
                progressDialog = buildProgressDialog(getString(R.string.sending))

            progressDialog?.show()

            Handler().postDelayed({
                progressDialog?.dismiss()
                Toast.makeText(this, getString(R.string.sent), Toast.LENGTH_LONG).show()
                finish()
            }, 2000)
        }

        val txtMonth = currentMonth + 1

        dateSelect.text = if (txtMonth.toString().length == 1)
            "$currentDay/0$txtMonth/$currentYear"
        else
            "$currentDay/$txtMonth/$currentYear"

        val datePickerDialog = DatePickerDialog(this, this, currentYear, currentMonth, currentDay)

        dateSelect.setOnClickListener {
            datePickerDialog.show();
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @Suppress("DEPRECATION")
    fun buildProgressDialog(message: String): ProgressDialog {

        val progressDialog = ProgressDialog(this)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setMessage(message)

        return progressDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        currentYear = year
        currentMonth = month
        currentDay = dayOfMonth

        val txtMonth = month + 1

        dateSelect.text = if (txtMonth.toString().length == 1)
            "$currentDay/0$txtMonth/$currentYear"
        else
            "$currentDay/$txtMonth/$currentYear"
    }
}