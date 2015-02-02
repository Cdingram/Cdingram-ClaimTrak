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

public class EditClaim extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_claim);
		Toast.makeText(this, "Edit Claim", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_claim, menu);
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
	public void editClaimAction(View v) {
		Toast.makeText(this, "Editing Claim", Toast.LENGTH_SHORT).show();
		EditText claimCat = (EditText) findViewById(R.id.editClaimCategoryEditText);
		EditText claimTo = (EditText) findViewById(R.id.editClaimToEditText);
		EditText claimFrom = (EditText) findViewById(R.id.editClaimFromEditText);
		EditText claimStat = (EditText) findViewById(R.id.editClaimStatEditText);
		
		String cat = claimCat.getText().toString();
		String to = claimTo.getText().toString();
		String from = claimFrom.getText().toString();
		String stat = claimStat.getText().toString();
		
		//add checking for proper date/status/etc
		Date toD = null;
		Date fromD = null;
		if (to.length() != 0 || from.length() != 0) {
			String dateFormat = "DD/MM/yyyy";
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			
			try {
				toD = format.parse(to);
				fromD = format.parse(from);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(this, "Ensure dates are in format dd/mm/yyyy", Toast.LENGTH_SHORT).show();
				return;
			}
		}	
		Claim claim = GlobalClaim.claim;
		
		if (cat.length() != 0 ) {
			claim.setCategory(cat);
		}
		if (to.length() != 0 ) {
			claim.setToDate(toD);
		}
		if (from.length() != 0 ) {
			claim.setToDate(fromD);
		}
		if (stat.length() != 0 ) {
			if (stat.equals("Submitted") || stat.equals("Returned") || stat.equals("Approved")){
				claim.setCategory(stat);
			} else {
				Toast.makeText(this, "Ensure status is Submitted or Returned or Approved", Toast.LENGTH_SHORT).show();
			}
		}
		
		ClaimController.saveClaimList();
		GlobalClaim.claim = null;
	}
}
