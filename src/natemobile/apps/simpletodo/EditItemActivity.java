package natemobile.apps.simpletodo;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

/**
 * EditItemActivity Class
 * @author nkemavaha
 *
 */
public class EditItemActivity extends ActionBarActivity {
	
	String TEXT_VALUE_FIELD = "itemValue";
	String POS_VALUE_FIELD = "itemPos";
	
	/** Value of the item */
	String valueItem;
	
	/** Position of the item in the list view from previous activity */
	int posItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		// Fetch data from previous activity
		valueItem = getIntent().getStringExtra( TEXT_VALUE_FIELD );
        posItem = getIntent().getIntExtra( POS_VALUE_FIELD, -1 );
        
        // Populate data to view
        EditText etItem = (EditText) findViewById(R.id.etItem );
        etItem.setText( valueItem );
	}
	
	/**
	 * Callback when submit button is clicked.
	 * @param v
	 */
	public void onSubmit( View v ) {
		Intent data = new Intent();
		
		EditText etItem = (EditText) findViewById(R.id.etItem );
		valueItem = etItem.getText().toString();
		data.putExtra( TEXT_VALUE_FIELD , valueItem );
		data.putExtra( POS_VALUE_FIELD, posItem );
		
		setResult( RESULT_OK, data );
		this.finish();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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

}
