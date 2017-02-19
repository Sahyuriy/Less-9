package com.example.sah.less_9;

import android.content.ClipData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    private EditText firstName, lastName, etID;

    private String[] columns = null;
    private String selection = null;
    private String[] selectionArgs = null;
    private ArrayList<String> items = new ArrayList<>();
    private String[] strings;
    private String str;
    private int ke = 0;


    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etID = (EditText) findViewById(R.id.id);
        firstName = (EditText) findViewById(R.id.fstName);
        lastName = (EditText) findViewById(R.id.lstName);


        dbHelper = new DBHelper(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = etID.getText().toString();
                String fstName = firstName.getText().toString();
                String lstName = lastName.getText().toString();

                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                if (fstName.equals("")){
                    if (lstName.equals("")){
                        //
                    }
                    else {
                        contentValues.put(DBHelper.KEY_LAST_NAME, lstName);
                        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                    }
                }
                else if (lstName.equals("")){
                    contentValues.put(DBHelper.KEY_FIRST_NAME, fstName);
                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                }
                else {
                    contentValues.put(DBHelper.KEY_FIRST_NAME, fstName);
                    contentValues.put(DBHelper.KEY_LAST_NAME, lstName);

                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                }

                etID.setText("");
                lastName.setText("");
                firstName.setText("");

            }
        });

            btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = etID.getText().toString();
                String fstName = firstName.getText().toString();
                String lstName = lastName.getText().toString();
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Cursor cursor;


                ContentValues contentValues = new ContentValues();
                ke = 0;

                if (!_id.equals("")) {
                    cursor = database.query(DBHelper.TABLE_CONTACTS, null, DBHelper.KEY_ID + "=" + _id, null, null, null, null);
                }
                else if (!fstName.equals("")){
                    selection = "fstName = ?";
                    selectionArgs = new String[] { fstName };
                    cursor = database.query(DBHelper.TABLE_CONTACTS, null, selection, selectionArgs, null, null, null);

                }
                else if (!lstName.equals("")){
                    selection = "lstName = ?";
                    selectionArgs = new String[] { lstName };
                    cursor = database.query(DBHelper.TABLE_CONTACTS, null, selection, selectionArgs, null, null, null);
                }
                else {

                    cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                }
                cursor.moveToFirst();
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int fst = cursor.getColumnIndex(DBHelper.KEY_FIRST_NAME);
                    int lst = cursor.getColumnIndex(DBHelper.KEY_LAST_NAME);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", fstName = " + cursor.getString(fst) +
                                ", lstName = " + cursor.getString(lst));
                        ke++;
                        items.add(cursor.getString(idIndex));
                        items.add(cursor.getString(fst));
                        items.add(cursor.getString(lst));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();


                etID.setText("");
                lastName.setText("");
                firstName.setText("");



                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragm, new BlankFragment(items))
                        .commit();
            }
        });

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                String _id = etID.getText().toString();
                String fstName = firstName.getText().toString();
                String lstName = lastName.getText().toString();
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Cursor cursor= database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                contentValues.put(DBHelper.KEY_FIRST_NAME, fstName);
                contentValues.put(DBHelper.KEY_LAST_NAME, lstName);
                database.update(DBHelper.TABLE_CONTACTS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {_id});


                etID.setText("");
                lastName.setText("");
                firstName.setText("");
            }
        });

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = etID.getText().toString();
                String fstName = firstName.getText().toString();
                String lstName = lastName.getText().toString();
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                if (_id.equals("")){
                    if (fstName.equals("")) {
                        database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_LAST_NAME + " = ?", new String[]{lstName});
                    }
                    else {
                        database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_FIRST_NAME + " = ?", new String[]{fstName});
                    }
                }
                else {
                    database.delete(DBHelper.TABLE_CONTACTS, DBHelper.KEY_ID + "=" + _id, null);
                }


                etID.setText("");
                lastName.setText("");
                firstName.setText("");
            }
        });

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_CONTACTS, null, null);

                etID.setText("");
                lastName.setText("");
                firstName.setText("");
            }
        });




    }




}
