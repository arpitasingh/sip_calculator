package com.example.myapplication;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFirstBinding;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class FirstFragment extends Fragment {

    private DBHelper mydb ;
    private FragmentFirstBinding binding;
    private String goalName = "";
    private Integer goalByWhenYear = 0;
    private Double goalExpectedReturns = 0.00;
    private int optionValue = 0;
    private Double calculatedFutureValue, calculatedMonthlyReturns, calculatedAnnualReturns, calculatedQuarterlyReturns;
    private String monthlyVal, quarterlyVal, annualVal, futureVal;
    private String filePath = "";
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mydb = new DBHelper(getContext());
//        List<FinanceGoal> goals = mydb.getAllGoals();
//        for (FinanceGoal g: goals) {
//            mydb.deleteContact(g.id);
//        }

        binding.buttonThird.setEnabled(false);
        binding.buttonFourth.setEnabled(false);



        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.editTextPicture.setOnClickListener(v -> {
            openGallery();
        });

        binding.btnCalculate.setOnClickListener(v -> {
            if (checkForEmptyFields()) {
                binding.textFutureValue.setText(calculateFutureValue());
                binding.editTextMonthly.setText(calculateMonthlyReturns());
                binding.editTextQuarterly.setText(calculateQuarterlyReturns());
                binding.editTextAnnually.setText(calculateAnnualReturns());
                availableOptions();
                binding.buttonThird.setEnabled(true);
                binding.buttonFourth.setEnabled(true);
            }
        });

        binding.buttonFourth.setOnClickListener(v -> {
            if(mydb.insertContact(monthlyVal, quarterlyVal, annualVal, optionValue + "", goalName, filePath)){
                Toast.makeText(getContext(), "Goal added",
                        Toast.LENGTH_SHORT).show();
                clearAllFields();
            }
        });

        binding.buttonThird.setOnClickListener(v -> {
//            if(mydb.insertContact(monthlyVal, quarterlyVal, annualVal, optionValue + "", goalName, filePath)){
//                Toast.makeText(getContext(), "Goal added",
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getContext(), "Goal not added",
//                        Toast.LENGTH_SHORT).show();
//            }

            clearAllFields();
        });

        binding.buttonSecond.setOnClickListener(v -> {
            getActivity().finish();
            System.exit(0);
        });
    }

    private void clearAllFields() {
        binding.textFutureValue.setText("");
        binding.editTextMonthly.setText("");
        binding.editTextQuarterly.setText("");
        binding.editTextAnnually.setText("");
        binding.editTextOption.setText("");
        binding.editTextTodayCost.setText("");
        binding.editTextGoalName.setText("");
        binding.editTextByWhen.setText("");
        binding.editTextExpectedReturns.setText("");
        binding.editTextInflation.setText("");
        binding.editTextPicture.setText("");
        binding.buttonThird.setEnabled(false);
        binding.buttonFourth.setEnabled(false);
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void availableOptions() {
        if (goalExpectedReturns <= 5) {
            optionValue = 5;
            binding.editTextOption.setText("FD, Liquid Fund, Debt Fund, Hybrid Fund, EQUITY");
        } else if (goalExpectedReturns <= 6.5) {
            optionValue = 4;
            binding.editTextOption.setText("Liquid Fund, Debt Fund, Hybrid Fund, EQUITY");
        } else if (goalExpectedReturns <= 8) {
            optionValue = 3;
            binding.editTextOption.setText("Debt Fund, Hybrid Fund, EQUITY");
        } else if (goalExpectedReturns <= 12) {
            optionValue = 2;
            binding.editTextOption.setText("Hybrid Fund, EQUITY");
        } else if (goalExpectedReturns <= 16) {
            optionValue = 1;
            binding.editTextOption.setText("EQUITY");
        }
    }

    private String calculateAnnualReturns() {
        double r = goalExpectedReturns/100;
        int n = goalByWhenYear - 2021;
        double temp1 = (Math.pow((1 + r), n) - 1);
        double temp2 = calculatedFutureValue / temp1;
        double temp3 = (1+r) / r;
        calculatedAnnualReturns = temp2 / temp3;
        annualVal = formatToCurrency(calculatedAnnualReturns);
        return annualVal;
    }

    private String calculateQuarterlyReturns() {
        double r = goalExpectedReturns/100;
        int n = goalByWhenYear - 2021;
        double temp1 = (Math.pow((1 + (r/4)), (n*4)) - 1);
        double temp2 = calculatedFutureValue / temp1;
        double temp3 = (1+(r/4)) / (r/4);
        calculatedQuarterlyReturns = temp2 / temp3;
        quarterlyVal = formatToCurrency(calculatedQuarterlyReturns);
        return quarterlyVal;
    }

    private String calculateMonthlyReturns() {
        double r = goalExpectedReturns/100;
        int n = goalByWhenYear - 2021;
        double temp1 = (Math.pow((1 + (r/12)), (n*12)) - 1);
        double temp2 = calculatedFutureValue / temp1;
        double temp3 = (1+(r/12)) / (r/12);
        calculatedMonthlyReturns = temp2 / temp3;
        monthlyVal = formatToCurrency(calculatedMonthlyReturns);
        return monthlyVal;
    }

    private String calculateFutureValue() {
        goalName = binding.editTextGoalName.getText().toString();
        goalByWhenYear = Integer.parseInt(binding.editTextByWhen.getText().toString());
        goalExpectedReturns = Double.parseDouble(binding.editTextExpectedReturns.getText().toString());
        double goalTodayCost = Double.parseDouble(binding.editTextTodayCost.getText().toString());
        double goalInflation = Double.parseDouble(binding.editTextInflation.getText().toString());

        int numberOfYears = goalByWhenYear - 2021;

        calculatedFutureValue = goalTodayCost * (Math.pow((1 + (goalInflation /100)), numberOfYears));
        futureVal = formatToCurrency(calculatedFutureValue);
        return futureVal;
    }

    private String formatToCurrency(Double calculatedValue) {
        String strRet;
        Currency currency = Currency.getInstance("INR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(currency);
        numberFormat.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        numberFormat.setMinimumFractionDigits(currency.getDefaultFractionDigits());

//        if (currency.getDefaultFractionDigits() == 1) {
//            strRet = numberFormat.format(calculatedValue / 10.0);
//        } else if (currency.getDefaultFractionDigits() == 2) {
//            strRet = numberFormat.format(calculatedValue / 100.00);
//        } else if (currency.getDefaultFractionDigits() == 3) {
//            strRet = numberFormat.format(calculatedValue / 1000.000);
//        } else if (currency.getDefaultFractionDigits() == 4) {
//            strRet = numberFormat.format(calculatedValue / 10000.0000);
//
//        } else if (currency.getDefaultFractionDigits() == 5) {
//            strRet = numberFormat.format(calculatedValue / 100000.00000);
//
//        } else {
//            strRet = numberFormat.format(calculatedValue);
//        }
        strRet = numberFormat.format(calculatedValue);

        return strRet;
    }

    private boolean checkForEmptyFields() {
        if (binding.editTextGoalName.getText().toString().equals("")) {
            binding.editTextGoalName.setError("Field is mandatory");
            return false;
        }
        if (binding.editTextByWhen.getText().toString().equals("") || Integer.parseInt(binding.editTextByWhen.getText().toString()) < 2021) {
            binding.editTextByWhen.setError("Field is mandatory");
            return false;
        }
        if (binding.editTextTodayCost.getText().toString().equals("")) {
            binding.editTextTodayCost.setError("Field is mandatory");
            return false;
        }
        if (binding.editTextExpectedReturns.getText().toString().equals("")) {
            binding.editTextExpectedReturns.setError("Field is mandatory");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            filePath = imageUri.getPath();
            binding.editTextPicture.setText(imageUri.getPath());
//            imageView.setImageURI(imageUri);
        }
    }
}