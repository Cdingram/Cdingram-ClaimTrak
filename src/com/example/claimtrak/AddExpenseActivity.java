package com.example.claimtrak;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);
		
		ClaimListManager.initManager(this.getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressLint("SimpleDateFormat")
	public void addExpenseAction(View v) {
		Toast.makeText(this, "Adding An Expense", Toast.LENGTH_SHORT).show();
		
		EditText expenseCat = (EditText) findViewById(R.id.addExpenseCategoryEditText);
		EditText expenseDate = (EditText) findViewById(R.id.addExpenseDateEditText);
		EditText expenseDes = (EditText) findViewById(R.id.addExpenseDescriptionEditText);
		EditText expenseAmount = (EditText) findViewById(R.id.addExpenseAmountEditText);
		
		// get all input
		String cat = expenseCat.getText().toString();
		String date = expenseDate.getText().toString();
		String des = expenseDes.getText().toString();
		String amount = expenseAmount.getText().toString();
		
		//add checks 
		if (cat.length() == 0 || date.length() == 0 || des.length() == 0 || amount.length() == 0) {
			Toast.makeText(this, "Ensure all fields are filled", Toast.LENGTH_SHORT).show();
			return;
		}
		//date check
		String dateFormat = "DD/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date dateD = null;
		try {
			dateD = format.parse(date);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "Ensure dates are in format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Expense expense = new Expense(dateD, cat, des, amount, "CAN");
		GlobalClaim.claim.addExpense(expense);
		ClaimController.saveClaimList();

	}
}
