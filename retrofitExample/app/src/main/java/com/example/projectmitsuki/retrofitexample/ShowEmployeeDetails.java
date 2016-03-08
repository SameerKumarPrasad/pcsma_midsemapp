package com.example.projectmitsuki.retrofitexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.projectmitsuki.retrofitexample.API.EmployeesAPI;
import com.example.projectmitsuki.retrofitexample.model.Employee;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowEmployeeDetails extends AppCompatActivity {

    private final String LOG_TAG = "ShowEmployeeDetails";
    //Defining views
    private TextView textViewBookId;
    private TextView textViewBookName;
    private TextView textViewBookPrice;
    private TextView textViewBookInStock;

    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_details);

        //Initializing Views
        textViewBookId = (TextView) findViewById(R.id.textViewBookId);
        textViewBookName = (TextView) findViewById(R.id.textViewBookName);
        textViewBookPrice = (TextView) findViewById(R.id.textViewBookPrice);
        textViewBookInStock = (TextView) findViewById(R.id.textViewBookInStock);

        //Getting intent
        Intent intent = getIntent();
        String employeeId = String.valueOf(intent.getIntExtra(MainActivity.KEY_EMPLOYEE_ID, 0));

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(MainActivity.ROOT_URL)
                .build();

        //Creating an object of our api interface
        EmployeesAPI api = adapter.create(EmployeesAPI.class);
        Log.d("Main Activity retro", "api");
        //Defining the method
        api.getUserInfo(employeeId, new Callback<Employee>() {
            @Override
            public void success(Employee list, Response response) {
                Log.d("Success", "success");

                //Storing the data in our list
                employee = list;

                //Calling a method to show the list
                Log.d(LOG_TAG, String.valueOf(employee.getId()));
                textViewBookId.setText(String.valueOf(employee.getId()));
                textViewBookName.setText(employee.getName());
                textViewBookPrice.setText(String.valueOf(employee.getAge()));
                textViewBookInStock.setText(String.valueOf(employee.getSalary()));
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
                Log.d(LOG_TAG, "failed calling");
            }
        });


        //Displaying values by fetching from intent
        //textViewBookId.setText(String.valueOf(intent.getIntExtra(MainActivity.KEY_EMPLOYEE_ID, 0)));
        //textViewBookName.setText(intent.getStringExtra(MainActivity.KEY_EMPLOYEE_NAME));
        //textViewBookPrice.setText(String.valueOf(intent.getIntExtra(MainActivity.KEY_EMPLOYEE_AGE, 0)));
        //textViewBookInStock.setText(String.valueOf(intent.getIntExtra(MainActivity.KEY_EMPLOYEE_SALARY,0)));
    }
}