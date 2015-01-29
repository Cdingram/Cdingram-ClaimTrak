package com.example.claimtrak;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claim_summary);
		
		String name = GlobalClaim.claim.getClaimName();
		Toast.makeText(this, ""+ name, Toast.LENGTH_SHORT).show();

	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		// Display expenses in listview
		ListView listView = (ListView) findViewById(R.id.expenseListView);
		// set empty view
		TextView empty = (TextView)findViewById(R.id.empty1);
		listView.setEmptyView(empty);
		
		//set array adapter to display expenses
		Collection<Expense> expenses = GlobalClaim.claim.getExpenses();
		final ArrayList<Expense> list1 = new ArrayList<Expense>(expenses);
		final ArrayList<String> list2 = new ArrayList<String>();
		for (Expense item: list1) {
			list2.add(item.getCategory());
		}
		final ArrayAdapter<String> expenseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list2);
		listView.setAdapter(expenseAdapter);
		
		// do i need change observer?
		
		//set longclick for edit/delete
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder adb = new AlertDialog.Builder(ExpenseListActivity.this);
				adb.setMessage("Change " + list2.get(position).toString()+ "?");
				adb.setCancelable(true);
				final int finalPosition = position;
				
				adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String expenseName = list2.get(finalPosition);
						Expense deletedExpense = new Expense();
						Collection<Expense> expenses = GlobalClaim.claim.getExpenses();
						final ArrayList<Expense> list3 = new ArrayList<Expense>(expenses);
						for(Expense item: list3) {
							if (item.getCategory().equals(expenseName)){
								deletedExpense = item;
							}
						}
						GlobalClaim.claim.removeExpense(deletedExpense);
						
					}
				});
				
				adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				adb.show();
				return true;
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_list, menu);
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
	
	public void addExpense(MenuItem menu) {
    	Toast.makeText(this, "Add Expense", Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(ExpenseListActivity.this, AddExpenseActivity.class);
    	startActivity(intent);
    }
}
