package com.lb.richardk.lbfour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UpdateDetailsActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User");

    public EditText fName;
    //public EditText lName;
    public EditText email;
    //public EditText mob;

    public String uid;

    public String fn;
    //public String ln;
    public String em;
    //public String mb;
    public String refreshToken;

    private FirebaseUser user;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);

        fName = (EditText) findViewById(R.id.FirstName);
        //lName = (EditText) findViewById(R.id.LastName);
        email = (EditText) findViewById(R.id.Email);
        //mob = (EditText) findViewById(R.id.Mobile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        uid = user.getUid();

        Query query = myRef.child(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {

                        String key = user.getKey();
                        String value = user.getValue().toString();

                        if(key.equals("firstName"))
                        {
                            fName.setText(value, TextView.BufferType.EDITABLE);
                        }
//                        else if(key.equals("lastName"))
//                        {
//                            lName.setText(value, TextView.BufferType.EDITABLE);
//                        }
//                        else if(key.equals("number"))
//                        {
//                            mob.setText(value, TextView.BufferType.EDITABLE);
//                        }
                        else if(key.equals("email"))
                        {
                            email.setText(value, TextView.BufferType.EDITABLE);
                            userEmail = value;
                        }
                        else if(key.equals("device_token"))
                        {
                            refreshToken = value;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button updateBtn = (Button)findViewById(R.id.Updatebutton);
        updateBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fn = fName.getText().toString();
                //ln = lName.getText().toString();
                em = email.getText().toString();
                //mb = mob.getText().toString();

                if(em != userEmail)
                {
                    user.updateEmail(em)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Success", "User email address updated.");
                                    }
                                }
                            });
                }

                NewUser user = new NewUser(fn, em);

                myRef.child(uid).setValue(user);
                myRef.child(uid).child("device_token").setValue(refreshToken);

                Intent startIntent = new Intent (getApplicationContext(), AccountActivity.class);
                startActivity(startIntent);
            }
        });
    }
}
