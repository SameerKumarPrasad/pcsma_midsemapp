package com.example.projectmitsuki.retrofitexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.projectmitsuki.retrofitexample.API.EmployeesAPI;
import com.example.projectmitsuki.retrofitexample.model.Employee;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class employeeInput extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_employee_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submitUser(View v)
    {
        Log.d("hello", "hello");

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(MainActivity.ROOT_URL)
                .build();

        //Creating an object of our api interface
        EmployeesAPI api = adapter.create(EmployeesAPI.class);


        // "replace email with actual variable"
        String name = "name";
        String age = "23";
        String salary = "98";
        HashMap<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("age", age);
        body.put("salary", salary);
        api.postUser(body , new Callback<Employee>() {
            @Override
            public void success(Employee employee, Response response) {

                Log.d("Employee Input", String.valueOf(employee.getId()));
            }

            @Override
            public void failure(final RetrofitError error) {
                try {
                    Log.i("example", "Error, body: " + error.getBody().toString());
                } catch (JsonSyntaxException | IllegalStateException e) {
                    Log.i("Main Activity Retro", "error in parsing");
                }

            }
        });

    }
}
