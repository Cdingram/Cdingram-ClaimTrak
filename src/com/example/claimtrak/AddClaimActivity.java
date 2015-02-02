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

public class AddClaimActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_claim);
		
		ClaimListManager.initManager(this.getApplicationContext());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
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
	public void addClaimAction(View v) {	
		Toast.makeText(this, "Adding A Claim", Toast.LENGTH_SHORT).show();
		EditText claimCat = (EditText) findViewById(R.id.claimCategoryEditText);
		EditText claimTo = (EditText) findViewById(R.id.claimToEditText);
		EditText claimFrom = (EditText) findViewById(R.id.claimFromEditText);
		ClaimController cc = new ClaimController();
		// get all input
		String cat = claimCat.getText().toString();
		String to = claimTo.getText().toString();
		String from = claimFrom.getText().toString();
		if (cat.length() == 0 || to.length() == 0 || from.length() == 0) {
			Toast.makeText(this, "Ensure all fields are filled", Toast.LENGTH_SHORT).show();
			return;
		}
		// needs fix
		String dateFormat = "DD/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date toD = null;
		Date fromD = null;
		try {
			toD = format.parse(to);
			fromD = format.parse(from);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "Ensure dates are in format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
			return;
		}
		
		cc.addClaim(new Claim(cat, toD, fromD, "In Progress"));
		ClaimController.saveClaimList();
		GlobalClaim.claim = null;
		
	}
}
