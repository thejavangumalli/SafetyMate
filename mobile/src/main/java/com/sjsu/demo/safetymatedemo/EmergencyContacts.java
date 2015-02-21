package com.sjsu.demo.safetymatedemo;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;
import android.widget.ListView;

import java.util.List;


public class EmergencyContacts extends ActionBarActivity implements SaveContactDialog.SaveContactListener {

    private static final int CONTACT_PICKER_RESULT = 1001;
    private static final String DEBUG_TAG = "Emergency";
    private String phoneNumber = "";
    private String displayName = "";
    private String contactId = "";
    private SimpleCursorAdapter dataAdapter;
    DBConnection dbConn;
    private boolean found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        dbConn=new DBConnection(this);
        displayListView();
    }

    private void displayListView(){

        String[] columns = new String[] {
                "displayName","phoneNumber"
        };

        int[] to = new int[] {
                R.id.contactName,
                R.id.phoneNumber
        };

        Cursor cursor = dbConn.getContactsForLV();


        if(cursor!=null) {
            dataAdapter = new SimpleCursorAdapter(
                    this, R.layout.contacts_info,
                    cursor,
                    columns,
                    to,
                    0);
        }

        ListView listView = (ListView) findViewById(R.id.listview_contacts);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_emergency_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_new:
                addNewContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addNewContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;

                    try {
                        Uri result = data.getData();

                        contactId = result.getLastPathSegment();

                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { contactId },
                                null);

                        int phoneNumIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                        int displayNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                        if (cursor.moveToFirst()) {
                            phoneNumber = cursor.getString(phoneNumIndex);
                            displayName = cursor.getString(displayNameIndex);

                            showNoticeDialog();

                        } else {
                            Log.d(DEBUG_TAG, "No results");
                        }
                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to get number", e);
                    } finally {
                        if (cursor != null)
                        {
                            cursor.close();
                        }

                    }

                    break;
            }

        }
        else {
            Log.w("debug", "Warning: activity result not ok");
        }
    }

    private void saveContactToDB(){
        found=false;
        if (phoneNumber!=null && !phoneNumber.equals(""))
        {
            List<String> phoneNumbers = dbConn.getContacts();
            for (String s : phoneNumbers){
                if(s.equals(phoneNumber))
                {
                    found=true;
                }
            }
            if(found)
            {
                Toast.makeText(this, "Emergency Contact already exists",Toast.LENGTH_SHORT).show();
            }
            else
            {
                dbConn.addContactsToDB(contactId, displayName, phoneNumber);
                Toast.makeText(this,displayName +" saved successfully as emergency contact",Toast.LENGTH_SHORT).show();
                Cursor cursor = dbConn.getContactsForLV();
                dataAdapter.changeCursor(cursor);
            }
        }
        else
        {
            Toast.makeText(this, "Please choose a contact",Toast.LENGTH_SHORT).show();
        }
    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SaveContactDialog();
        Bundle args = new Bundle();
        args.putString("displayName", displayName);
        dialog.setArguments(args);
        dialog.show(getFragmentManager(),"SaveContact");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.d(DEBUG_TAG,"Dialog Positive Name "+displayName);
        saveContactToDB();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
