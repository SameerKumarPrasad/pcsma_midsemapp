package com.example.projectmitsuki.retrofitexample;

import com.example.projectmitsuki.retrofitexample.API.EmployeesAPI;
import com.example.projectmitsuki.retrofitexample.model.Employee;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

//Class having OnItemClickListener to handle the clicks on list
public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    //Root URL of our web service
    public static final String ROOT_URL = "http://192.168.53.208:8080/";

    //Strings to bind with intent will be used to send data to other activity
    public static final String KEY_EMPLOYEE_ID = "key_book_id";
    public static final String KEY_EMPLOYEE_NAME = "key_book_name";
    public static final String KEY_EMPLOYEE_AGE = "key_book_price";
    public static final String KEY_EMPLOYEE_SALARY = "key_book_stock";
    private static final String LOG_TAG = "MainActivity";

    //List view to show data
    private ListView listView;

    //List of type employees this list will store type Employee which is our data model
    private List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the listview
        listView = (ListView) findViewById(R.id.listViewBooks);

        //Calling the method that will fetch data
        getBooks();

        //Setting onItemClickListener to listview
        listView.setOnItemClickListener(this);
    }

    private void getBooks(){
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Fetching Data","Please wait...",false,false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();

        //Creating an object of our api interface
        EmployeesAPI api = adapter.create(EmployeesAPI.class);

        //Defining the method
        api.getAllUsers(new Callback<List<Employee>>() {
            @Override
            public void success(List<Employee> list, Response response) {
                //Dismissing the loading progressbar
                loading.dismiss();

                //Storing the data in our list
                employees = list;

                //Calling a method to show the list
                showList();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });


    }

    //Our method to show list
    private void showList(){
        try {
            //String array to store all the book names
            String[] items = new String[employees.size()];


            //Traversing through the whole list to get all the names
            for (int i = 0; i < employees.size(); i++) {
                //Storing names to string array
                items[i] = employees.get(i).getName();
            }

            //Creating an array adapter for list view
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_list, items);

            //Setting adapter to listview
            listView.setAdapter(adapter);
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, "No user found");
        }
    }


    //This method will execute on listitem click
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Creating an intent
        Intent intent = new Intent(this, ShowBookDetails.class);

        //Getting the requested employee from the list
        Employee employee = employees.get(position);

        //Adding employee details to intent
        intent.putExtra(KEY_EMPLOYEE_ID, employee.getId());
        //intent.putExtra(KEY_EMPLOYEE_NAME,employee.getName());
        //intent.putExtra(KEY_EMPLOYEE_AGE,employee.getAge());
        //intent.putExtra(KEY_EMPLOYEE_SALARY,employee.getSalary());

        //Starting another activity to show employee details
        startActivity(intent);
    }
}