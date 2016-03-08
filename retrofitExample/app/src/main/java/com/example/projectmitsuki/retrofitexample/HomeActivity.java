package com.example.projectmitsuki.retrofitexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectmitsuki.retrofitexample.API.EmployeesAPI;
import com.example.projectmitsuki.retrofitexample.model.Employee;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeActivity extends AppCompatActivity {
    int x;

    private List<Employee> employees;
    public static final String KEY_EMPLOYEE_ID = "key_book_id";
    public static final String LOG_TAG = "HomeActivity";

    Button fetch_all_button , create_user_button , Delete_all_user , verify_button;
    Button fetch_id_button , update_user_button , Delete_user_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setListeneronFetchAll(fetch_all_button);
        setListeneronFetchwithID(fetch_id_button);
        setListeneronCreateuser(create_user_button);
        setListeneronUpdateuser(update_user_button);
        setListeneronDeleteuser(Delete_user_button);
        setListeneronDeleteAlluser(Delete_all_user);
        setlistenerOnVerifyButton(verify_button);
    }

    private void setlistenerOnVerifyButton(Button verify_button) {
        verify_button = (Button)findViewById(R.id.verify_delete);
        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListeneronDeleteAlluser(Button delete_all_user) {
        delete_all_user = (Button)findViewById(R.id.Delete_all_user);
        delete_all_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Send request to delete" , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setListeneronDeleteuser(Button delete_user_button) {
        delete_user_button = (Button)findViewById(R.id.Delete_user);
        delete_user_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("ID input");
                alert.setMessage("Enter the user ID");
                final EditText input = new EditText(HomeActivity.this);
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = input.getText().toString();
                        if(!value.equalsIgnoreCase("")) {
                            x = Integer.parseInt(value);
                            Toast.makeText(getApplicationContext(), "value is " + x, Toast.LENGTH_LONG).show();

                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }

    private void setListeneronUpdateuser(Button update_user_button) {
        update_user_button = (Button)findViewById(R.id.update_user);
        update_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                alert.setTitle("User details");
                alert.setMessage("Enter the following details");
                final EditText name_field = new EditText(v.getContext());
                name_field.setHint("Name");
                layout.addView(name_field);
                final EditText age_field = new EditText(v.getContext());
                age_field.setHint("Age");
                layout.addView(age_field);
                final EditText salary_field = new EditText(v.getContext());
                salary_field.setHint("Salary");
                layout.addView(salary_field);
                alert.setView(layout);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = name_field.getText().toString();
                        String age = age_field.getText().toString();
                        String salary = salary_field.getText().toString();
                        if(!(name.equalsIgnoreCase("") || age.equalsIgnoreCase("") || salary.equalsIgnoreCase(""))) {
                            int age_int = Integer.parseInt(age);
                            int salary_int = Integer.parseInt(salary);
                            Toast.makeText(getApplicationContext(), name + " " + age + " " + salary + " " , Toast.LENGTH_LONG).show();


                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }

    private void setListeneronCreateuser(Button create_user_button) {
        create_user_button = (Button) findViewById(R.id.create_user);
        create_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                LinearLayout layout = new LinearLayout(v.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                alert.setTitle("User details");
                alert.setMessage("Enter the following details");
                final EditText name_field = new EditText(v.getContext());
                name_field.setHint("Name");
                layout.addView(name_field);
                final EditText age_field = new EditText(v.getContext());
                age_field.setHint("Age");
                layout.addView(age_field);
                final EditText salary_field = new EditText(v.getContext());
                salary_field.setHint("Salary");
                layout.addView(salary_field);
                alert.setView(layout);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = name_field.getText().toString();
                        String age = age_field.getText().toString();
                        String salary = salary_field.getText().toString();
                        if(!(name.equalsIgnoreCase("") || age.equalsIgnoreCase("") || salary.equalsIgnoreCase(""))) {
                            int age_int = Integer.parseInt(age);
                            int salary_int = Integer.parseInt(salary);
                            Toast.makeText(getApplicationContext(), name + " " + age + " " + salary + " " , Toast.LENGTH_LONG).show();

                            HashMap<String, String> body = new HashMap<String, String>();
                            body.put("name", name);
                            body.put("age", age);
                            body.put("salary", salary);

                            //Creating a rest adapter
                            RestAdapter adapter = new RestAdapter.Builder()
                                    .setEndpoint(MainActivity.ROOT_URL)
                                    .build();

                            //Creating an object of our api interface
                            EmployeesAPI api = adapter.create(EmployeesAPI.class);

                            //Defining the method
                            api.postUser( body, new Callback<Employee>() {

                                @Override
                                public void success(Employee employee, Response response) {
                                    Log.d(LOG_TAG, "New user added.");
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d(LOG_TAG, "New user failed.");
                                    error.printStackTrace();
                                    //you can handle the errors here
                                }
                            });
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();


            }
        });
    }

    private void setListeneronFetchwithID(Button fetch_id_button) {
        fetch_id_button = (Button)findViewById(R.id.fetch_id);
        fetch_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("ID input");
                alert.setMessage("Enter the user ID");
                final EditText input = new EditText(HomeActivity.this);
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = input.getText().toString();
                        if(!value.equalsIgnoreCase("")) {
                            x = Integer.parseInt(value);
                            Toast.makeText(getApplicationContext(), "value is " + x, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(HomeActivity.this, ShowBookDetails.class);
                            intent.putExtra(KEY_EMPLOYEE_ID, x);
                            startActivity(intent);

                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }

        });

    }

    private void setListeneronFetchAll(Button fetch_all_button) {
        fetch_all_button = (Button)findViewById(R.id.fetch_all);
        fetch_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this , MainActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
}
