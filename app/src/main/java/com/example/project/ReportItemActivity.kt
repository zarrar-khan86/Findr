package com.example.project

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class ReportItemActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var spinnerStatus: Spinner
    private lateinit var etItemName: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var etLocation: EditText
    private lateinit var btnUseCurrentLocation: LinearLayout
    private lateinit var btnSelectDateTime: View
    private lateinit var tvDateTime: TextView
    private lateinit var etDescription: EditText
    private lateinit var btnCamera: ImageView
    private lateinit var btnGallery: ImageView
    private lateinit var btnChooseFile: Button
    private lateinit var btnSubmit: Button

    private var selectedDateTime: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_item)

        // Hide action bar
        supportActionBar?.hide()

        // Initialize views
        initializeViews()

        // Setup spinners
        setupSpinners()

        // Setup click listeners
        setupClickListeners()
    }

    private fun initializeViews() {
        btnBack = findViewById(R.id.btnBack)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        etItemName = findViewById(R.id.etItemName)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etLocation = findViewById(R.id.etLocation)
        btnUseCurrentLocation = findViewById(R.id.btnUseCurrentLocation)
        btnSelectDateTime = findViewById(R.id.btnSelectDateTime)
        tvDateTime = findViewById(R.id.tvDateTime)
        etDescription = findViewById(R.id.etDescription)
        btnCamera = findViewById(R.id.btnCamera)
        btnGallery = findViewById(R.id.btnGallery)
        btnChooseFile = findViewById(R.id.btnChooseFile)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun setupSpinners() {
        // Status spinner
        val statusAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.status_options,
            android.R.layout.simple_spinner_item
        )
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = statusAdapter

        // Category spinner
        val categoryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.category_options,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter
    }

    private fun setupClickListeners() {
        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Use current location
        btnUseCurrentLocation.setOnClickListener {
            // TODO: Get current location using GPS
            Toast.makeText(this, "Getting current location...", Toast.LENGTH_SHORT).show()
        }

        // Date & Time picker
        btnSelectDateTime.setOnClickListener {
            showDateTimePicker()
        }

        // Camera
        btnCamera.setOnClickListener {
            // TODO: Open camera
            Toast.makeText(this, "Opening camera...", Toast.LENGTH_SHORT).show()
        }

        // Gallery
        btnGallery.setOnClickListener {
            // TODO: Open gallery
            Toast.makeText(this, "Opening gallery...", Toast.LENGTH_SHORT).show()
        }

        // Choose file
        btnChooseFile.setOnClickListener {
            // TODO: Open file picker
            Toast.makeText(this, "Choose file...", Toast.LENGTH_SHORT).show()
        }

        // Submit button
        btnSubmit.setOnClickListener {
            submitReport()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        // Date picker
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                selectedDateTime.set(Calendar.YEAR, year)
                selectedDateTime.set(Calendar.MONTH, month)
                selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // Time picker
                val timePickerDialog = TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateTime.set(Calendar.MINUTE, minute)

                        // Update TextView
                        updateDateTimeDisplay()
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateDateTimeDisplay() {
        val dateFormat = SimpleDateFormat("MMMM d, yyyy 'at' h:mm a", Locale.getDefault())
        tvDateTime.text = dateFormat.format(selectedDateTime.time)
        tvDateTime.setTextColor(resources.getColor(android.R.color.black, null))
    }

    private fun submitReport() {
        // Validate fields
        val status = spinnerStatus.selectedItem.toString()
        val itemName = etItemName.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()
        val location = etLocation.text.toString().trim()
        val dateTime = tvDateTime.text.toString()
        val description = etDescription.text.toString().trim()

        // Check required fields
        if (itemName.isEmpty()) {
            Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show()
            return
        }

        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show()
            return
        }

        if (dateTime == getString(R.string.select_date_time)) {
            Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Submit report to backend
        Toast.makeText(this, "Report submitted successfully!", Toast.LENGTH_LONG).show()
        finish()
    }
}

