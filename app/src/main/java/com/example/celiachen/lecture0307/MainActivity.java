package com.example.celiachen.lecture0307;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editFirst, editLast, editGrade, editId;
    Button add, update, delete, viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editFirst = findViewById(R.id.firstname_editText);
        editLast = findViewById(R.id.lastname_editText);
        editGrade = findViewById(R.id.grade_editText);
        editId = findViewById(R.id.row_id_editText);

        add = findViewById(R.id.add);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        viewAll = findViewById(R.id.view);

        // insert data
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean isInserted = myDb.insertData(editFirst.getText().toString(),
                        editLast.getText().toString(), editGrade.getText().toString());
                if (isInserted == true){
                    // I inserted correctly
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });


        // view all data
        viewAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Cursor res = myDb.getAllData();
                // if there is nothing in res
                // no data in your table
                if (res.getCount() == 0){
                    // display a message: no data found.
                    showMessage("No data found.");
                    return;
                }
                // build a String to display
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){

                    // get the column name + data of that row
                    // add it to the string buffer
                    buffer.append("ID: " + res.getString(0) + "\n");
                    buffer.append("Firstname " + res.getString(1) + "\n");
                    buffer.append("Lastname " + res.getString(2) + "\n");
                    buffer.append("Grade " + res.getString(3)+ "\n");
                }

                // display the string buffer
                showMessage(buffer.toString());

            }
        });

        // update
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editId.getText().toString(),
                                editFirst.getText().toString(),
                                editLast.getText().toString(),editGrade.getText().toString());
                        if(isUpdate == true) {
                            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        // delete
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editId.getText().toString());
                        if(deletedRows > 0) {
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );
    }

    // write a method that takes a message
    // display it with an alart Dialog
    public void showMessage (String message){
        // alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the message to the dialog
        builder.setMessage(message);
        // set it so that you can cancel
        builder.setCancelable(true);
        // show the dialog
        builder.show();

    }

    // SQLite
    // create table
    // insert row
    // update row
    // delete row
    // database table
    // rows and column
    /*
    -------------Student Record Table---------------
    id  |  firstname  | lastname | grade
    1   |  Mark       | Whatever | 100

    each database -> primary key (unique key to each row that you use to refer to that row), foreign key
     */

}
