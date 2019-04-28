package com.lb.richardk.lbfour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Car");
    DatabaseReference myRegRef = database.getReference("Registration");

    public EditText vRegistration;
    //public EditText model;
    //public EditText make;
    //public EditText colour;
    public EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        vRegistration = (EditText) findViewById(R.id.ViewVehicleRegeditText);
        //make = (EditText) findViewById(R.id.ViewCityeditText);
        //model = (EditText) findViewById(R.id.ViewModeleditText2);
        //colour = (EditText) findViewById(R.id.ViewColoureditText2);
        city = (EditText) findViewById(R.id.ViewCityeditText);

        vRegistration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable arg) {

                String s = arg.toString();
                if(!s.equals(s.toUpperCase())){
                    s = s.toUpperCase();
                    vRegistration.setText(s);
                    vRegistration.setSelection(vRegistration.getText().length());

                }

            }
        });

        Button registerbtn1 = (Button) findViewById(R.id.Registerbtn1);
        registerbtn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(vRegistration.getText()))
                {
                    vRegistration.setError("Vehicle registration is required");
                }
//                else if (TextUtils.isEmpty(make.getText()))
//                {
//                    make.setError("Car make is required");
//                }
//                else if (TextUtils.isEmpty(model.getText()))
//                {
//                    model.setError("Model is required");
//                }
//                else if (TextUtils.isEmpty(colour.getText()))
//                {
//                    colour.setError("Colour is required");
//                }
                else if (TextUtils.isEmpty(city.getText()))
                {
                    city.setError("Borough is required");
                }
                else
                {
                    Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(startIntent);

                    String reg = vRegistration.getText().toString();
                    //String mod = model.getText().toString();
                    //String mk = make.getText().toString();
                    //String col = colour.getText().toString();
                    String cit = city.getText().toString();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    String uid = user.getUid();

                    NewCar newCar = new NewCar(reg, cit);

                    myRef.child(uid).push().setValue(newCar);
                    myRegRef.child(reg).child("uid").setValue(uid);
                }
            }
        });

        Button skip = (Button) findViewById(R.id.skipButton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(startIntent);
            }
        });

        Button contact = (Button) findViewById(R.id.contactUs);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(getApplicationContext(), RegisterHelpEnquireActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
