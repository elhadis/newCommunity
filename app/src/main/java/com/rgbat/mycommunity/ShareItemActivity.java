package com.rgbat.mycommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rgbat.mycommunity.Model.Houses;
import com.rgbat.mycommunity.Model.Products;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ShareItemActivity extends AppCompatActivity {
    private ImageView itemShare;
    private EditText editTextPlace,editTextPrice,editTextPhone;
    private Button buttonShare;
    private static final int PickGallery= 1;
    private Uri ImageUri;

    private StorageReference ItemRef;
    private DatabaseReference Ref;







    private String saveCurrentDate,saveCurrentTime,apartmentRandomKey,downLoadImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_item);
        itemShare = findViewById(R.id.item_picture);
        editTextPhone = findViewById(R.id.item_phone);
       // editTextPlace = findViewById(R.id.item_pace);
        editTextPrice = findViewById(R.id.item_price);
        buttonShare = findViewById(R.id.item_share);
        ItemRef = FirebaseStorage.getInstance().getReference().child("Items");
        Ref = FirebaseDatabase.getInstance().getReference().child("Products");
        itemShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,PickGallery);

            }
        });
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String place = editTextPlace.getText().toString();
                String price= editTextPrice.getText().toString();
                String phone = editTextPhone.getText().toString();

                if (price.isEmpty()){
                    editTextPrice.setError("Write The Item Price");
                    return;
                }else if (phone.isEmpty()){
                    editTextPhone.setError("Please Enter The Phone ");
                    return;
                }else {
                    storeApartmentImageInfo();
                }

            }
        });
    }

    private void storeApartmentImageInfo() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MMM,yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        apartmentRandomKey = saveCurrentDate + saveCurrentTime;


        final StorageReference filePath= ItemRef.child(ImageUri.getLastPathSegment()+apartmentRandomKey+ ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getBaseContext(),"Apartment Image Uploaded Successfully",Toast.
                        LENGTH_LONG).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downLoadImageUrl =filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        downLoadImageUrl = task.getResult().toString();
                        Toast.makeText(getBaseContext(),"Apartment Image Saved Successful",Toast.
                                LENGTH_LONG).show();

                        SaveApartmentInfInDateBase();
                    }


                });

            }
        });

    }

    private void SaveApartmentInfInDateBase() {
        HashMap<String,Object> apartmentMap =new HashMap<>();
        apartmentMap.put("productimage",downLoadImageUrl);
        //  apartmentMap.put("id",apartmentRandomKey);
        apartmentMap.put("phone",editTextPhone);

       // apartmentMap.put("place",editTextPlace);
        apartmentMap.put("price",editTextPrice);



        apartmentMap.put("date",saveCurrentDate);
        apartmentMap.put("time",saveCurrentTime);

        Ref.push().setValue(new Products( downLoadImageUrl,editTextPrice.getText().toString(),
                editTextPhone.getText().toString(),saveCurrentTime,saveCurrentDate))

                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    itemShare.setImageURI(null);
                    editTextPhone.setText("");
                   // editTextPlace.setText("");
                    editTextPrice.setText("");
                    //apartmentPrice.setText("");
                    Toast.makeText(getBaseContext(),"Apartment Image Add Successful",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PickGallery && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            itemShare.setImageURI(ImageUri);
        }
    }
}
