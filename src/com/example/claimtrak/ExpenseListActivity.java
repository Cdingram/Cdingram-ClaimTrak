/*
 * Copyright Cody Ingram 2015
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * For full license details and acknowledgements, please refer to the README-LICENSE file
 * 
 * github.com/Cdingram/Cdingram-ClaimTrak
*/
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
		
		// set text view for totals
		TextView textView = (TextView) findViewById(R.id.expenseTextView);
		String text = "Totals" + "\n" + GlobalClaim.claim.getTotalCAD() + " CAD" +"\n" +  GlobalClaim.claim.getTotalUSD() + " USD" + "\n" + GlobalClaim.claim.getTotalEUR() + " EUR" + "\n" +  GlobalClaim.claim.getTotalGBP() + " GBP";
		textView.setText(text);
		
		// set onclick for view
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// send to summary activity for expense that was clicked
				final int finalPosition = position;
				String expenseName = list2.get(finalPosition);
				Expense viewExpense = new Expense();
				Collection<Expense> expenses = GlobalClaim.claim.getExpenses();
				final ArrayList<Expense> list3 = new ArrayList<Expense>(expenses);
				for (Expense item: list3) {
					if (item.getCategory().equals(expenseName)) {
						viewExpense = item;
					}
				}
				
				Intent intent = new Intent(ExpenseListActivity.this, ExpenseSummaryActivity.class);
				GlobalClaim.expense = viewExpense;
				startActivity(intent);
				
			}
		});
		
		//set longclick for edit/delete
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// create dialog
				AlertDialog.Builder adb = new AlertDialog.Builder(ExpenseListActivity.this);
				adb.setMessage("Change " + list2.get(position).toString()+ "?");
				adb.setCancelable(true);
				final int finalPosition = position;
				// delete button
				adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// delete selected expense
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
						
						// update listView. Bit of a hack but it works for now (adapter not in this scope for .notifyDataSetChanged()
						// realized this was unnecessary to late to change :( 
						ListView listView = (ListView) findViewById(R.id.expenseListView);
						Collection<Expense> expenses1 = GlobalClaim.claim.getExpenses();
						final ArrayList<Expense> list1 = new ArrayList<Expense>(expenses1);
						final ArrayList<String> list2 = new ArrayList<String>();
						for (Expense item: list1) {
							list2.add(item.getCategory());
						}
						final ArrayAdapter<String> expenseAdapter = new ArrayAdapter<String>(ExpenseListActivity.this, android.R.layout.simple_expandable_list_item_1, list2);
						listView.setAdapter(expenseAdapter);
						
						// update total currencies text view. Same story as above
						TextView textView = (TextView) findViewById(R.id.expenseTextView);
						String text = "Totals" + "\n" + GlobalClaim.claim.getTotalCAD() + " CAD" +"\n" +  GlobalClaim.claim.getTotalUSD() + " USD" + "\n" + GlobalClaim.claim.getTotalEUR() + " EUR" + "\n" +  GlobalClaim.claim.getTotalGBP() + " GBP";
						textView.setText(text);
						
						ClaimController.saveClaimList();
						
						
					}
				});
				// edit button
				adb.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String expenseName = list2.get(finalPosition);
						Expense editExpense = new Expense();
						Collection<Expense> expenses = GlobalClaim.claim.getExpenses();
						final ArrayList<Expense> list3 = new ArrayList<Expense>(expenses);
						for (Expense item:list3) {
							if (item.getCategory().equals(expenseName)){
								editExpense = item;
							}
						}
						// send to edit expense activity
						Intent intent = new Intent(ExpenseListActivity.this, EditExpenseActivity.class);
						GlobalClaim.expense = editExpense;
						startActivity(intent);
						
						
						
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
	// method to send to add expense activity on button push
	public void addExpense(MenuItem menu) {
    	Toast.makeText(this, "Add Expense", Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(ExpenseListActivity.this, AddExpenseActivity.class);
    	startActivity(intent);
    }
	// method to send to send email activity on button push
	public void emailClaim(MenuItem menu) {
		Toast.makeText(this, "Email Claim", Toast.LENGTH_SHORT).show();
    	Intent intent = new Intent(ExpenseListActivity.this, EmailActivity.class);
    	startActivity(intent);
	}
}
