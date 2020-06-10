package com.rgbat.mycommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rgbat.mycommunity.Model.PostingWork;

import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class postJobsActivity extends AppCompatActivity {

    private EditText workPlace,workType,workShift,workEmployer;
    private Button buttonPostWork;
    private DatabaseReference Ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jobs);
        workEmployer= findViewById(R.id.work_employer);
        workPlace = findViewById(R.id.work_place);
        workType = findViewById(R.id.work_Type);
        workShift = findViewById(R.id.work_shift);
        buttonPostWork = findViewById(R.id.work_posting);
        Ref = FirebaseDatabase.getInstance().getReference().child("Posting");

        buttonPostWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = workPlace.getText().toString();
                String type = workType.getText().toString();
                String shift = workShift.getText().toString();
                String employer = workEmployer.getText().toString();

                if (place.isEmpty()){
                    workPlace.setError("Enter work Place");
                    return;
                }else
                if (type.isEmpty()){
                    workType.setError("Enter work Type");
                    return;
                }else
                if (shift.isEmpty()){
                    workShift.setError("Enter Shift Time");
                    return;
                }else
                if (employer.isEmpty()){
                    workEmployer.setError("Enter Employer Number");
                    return;
                }
                else {
                    FindData();






                }

            }
        });
    }

    private void FindData() {



        final String currentTime,currentDate;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd,MM,yyyy");
        currentDate = date.format(calendar.getTime());
        SimpleDateFormat time = new SimpleDateFormat("HH:mm,a");
        currentTime = time.format(calendar.getTime());
        Map<String,Object> doc = new ArrayMap<>();
        doc.put("workPlace",workPlace);
        doc.put("workCity",workType);
        doc.put("shift",workShift);
        doc.put("workEmployerNum",workEmployer);
        doc.put("date",currentDate);
        doc.put("time",currentTime);
        Ref.push().setValue(new PostingWork(workPlace.getText().toString(),workType.getText().toString(),workEmployer.getText().toString(),
                workShift.getText().toString(),currentDate,currentTime))
                .addOnCompleteListener(new  OnCompleteListener<Void>() {
                    @Override


                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

//                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
//                                    startActivity(intent);
                                    workEmployer.setText("");
                                    workShift.setText("");
                                    workPlace.setText("");
                                    workType.setText("");

                                    Toast.makeText(getBaseContext(), "Sending Information is Successful", Toast.LENGTH_LONG).show();

                                }
                            }, 2000);

                        }


                    }




                });
    }

}
