package natemobile.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * TodoActivity Class
 * 
 * Main activity for ToDo apps
 * 
 * @author nkemavaha
 *
 */
public class TodoActivity extends ActionBarActivity {

	/** List of content in each item */
	ArrayList<String> items;
	
	/** This allows us to easily display contents of arrayList (items) */
	ArrayAdapter<String> itemsAdapter;
	
	/** Reference to Listview (i.e. list of items UI) */
	ListView lvItems;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        lvItems = (ListView) findViewById( R.id.lvItems );
        items = new ArrayList<String>(); 
        
        readItems();
        
        // Setting up ArrayAdapter
        itemsAdapter = new ArrayAdapter<String>( 
        		this, 	// context
        		android.R.layout.simple_list_item_1, // layout to use 
        		items	// Object this adapter binded to 
        );
        
        lvItems.setAdapter( itemsAdapter );
        
        // If nothing populated, add default items
        if ( items.size() == 0 ) {
        	items.add( "First Item" );
        	items.add( "Second Item" );
        }        
       
        
        setupListViewListener();
    }
    
    /**
     * Callback when Add button is clicked
     * @param v
     */
    public void addTodoItem(View v) {
    	EditText etNewItem = (EditText) findViewById( R.id.etNewItem );
    	
    	if ( etNewItem != null ) {
    		// Add text/item to Adapter
    		itemsAdapter.add( etNewItem.getText().toString() );
    		// Reset text on EditText field
    		etNewItem.setText("");	
    		
    		saveItems();
    	}
    }
    
    /**
     * Read items from file
     */
    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File ( filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>( FileUtils.readLines( todoFile ));
    	} catch ( IOException e ) {
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    /**
     * Write data (aka items) to file
     */
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File ( filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines( todoFile, items);
    	} catch ( IOException e ) {
    		e.printStackTrace();
    	}	
    }
    
    /**
     * Setup a callback when interact with listViewItems
     */
    private void setupListViewListener() {	
    	// This is for removing 
    	lvItems.setOnItemLongClickListener( new OnItemLongClickListener() { 
    		public boolean onItemLongClick( AdapterView<?> aView, View item, int position, long id ){
    			items.remove( position );
				itemsAdapter.notifyDataSetInvalidated();	// Refresh view
				saveItems();
				return true;	
    		}
    	});
    	
    	// This is for editing
    	lvItems.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int requestCode = 99;
				String itemValue = items.get( position );
				Intent i = new Intent( TodoActivity.this, EditItemActivity.class ); 
				i.putExtra("itemPos", position);
				i.putExtra("itemValue", itemValue);
				startActivityForResult(i, requestCode);
			}
    		
    		
    	});
   	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    		if ( requestCode == 99 && resultCode == RESULT_OK ) {
    			
    			String newValue = data.getStringExtra( "itemValue" );
    			int posItem = data.getIntExtra( "itemPos", -1);
    			items.set( posItem, newValue);
    			itemsAdapter.notifyDataSetInvalidated();
    			saveItems();
    			
    		}
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
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
