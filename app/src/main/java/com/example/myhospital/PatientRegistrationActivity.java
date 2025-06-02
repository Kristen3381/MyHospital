package com.example.myhospital; // IMPORTANT: Replace with your actual package name

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PatientRegistrationActivity extends AppCompatActivity {
    private TextInputLayout tilPatientName;
    private TextInputEditText etPatientName;
    private TextInputLayout tilPatientAge;
    private TextInputEditText etPatientAge;
    private RadioGroup rgGender;
    private TextInputLayout tilPhoneNumber;
    private TextInputEditText etPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Patient Registration");
        }
        tilPatientName = findViewById(R.id.til_patient_name);
        etPatientName = findViewById(R.id.et_patient_name);
        tilPatientAge = findViewById(R.id.til_patient_age);
        etPatientAge = findViewById(R.id.et_patient_age);
        rgGender = findViewById(R.id.rg_gender);
        RadioButton rbMale = findViewById(R.id.rb_male);
        RadioButton rbFemale = findViewById(R.id.rb_female);
        tilPhoneNumber = findViewById(R.id.til_phone_number);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPatient();
            }
        });
    }
    private void registerPatient() {
        tilPatientName.setError(null);
        tilPatientAge.setError(null);
        tilPhoneNumber.setError(null);

        String name = etPatientName.getText().toString().trim();
        String ageStr = etPatientAge.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        String gender = "";
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        if (selectedGenderId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedGenderId);
            gender = selectedRadioButton.getText().toString();
        }

        boolean isValid = true;

        if (name.isEmpty()) {
            tilPatientName.setError("Patient Name is required");
            isValid = false;
        }

        if (ageStr.isEmpty()) {
            tilPatientAge.setError("Age is required");
            isValid = false;
        } else {
            try {
                int age = Integer.parseInt(ageStr);
                if (age <= 0 || age > 120) {
                    tilPatientAge.setError("Please enter a valid age (1-120)");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                tilPatientAge.setError("Invalid age format. Please enter a number.");
                isValid = false;
            }
        }

        if (phoneNumber.isEmpty()) {
            tilPhoneNumber.setError("Phone Number is required");
            isValid = false;
        } else if (phoneNumber.length() != 9) {
            tilPhoneNumber.setError("Phone number must be 9 digits long (e.g., 712345678)");
            isValid = false;
        } else if (!phoneNumber.matches("\\d+")) {
            tilPhoneNumber.setError("Invalid phone number format. Only digits allowed.");
            isValid = false;
        }

        if (gender.isEmpty()) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        String fullPhoneNumber = "+254 " + phoneNumber;

        String registrationDetails = "Patient Registered Successfully:\n" +
                "Name: " + name + "\n" +
                "Age: " + ageStr + "\n" +
                "Gender: " + gender + "\n" +
                "Phone: " + fullPhoneNumber;

        Toast.makeText(this, registrationDetails, Toast.LENGTH_LONG).show();

        clearForm();
    }
    private void clearForm() {
        etPatientName.setText("");
        etPatientAge.setText("");
        etPhoneNumber.setText("");
        rgGender.clearCheck();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}