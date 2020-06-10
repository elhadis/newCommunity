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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PostApartmentActivity extends AppCompatActivity {
    private ImageView apartmentPicture;
    private Button ApartmentSubmit;
    private StorageReference apartmentRef;
    private DatabaseReference Ref;




    private EditText apartmentDescription,apartmentPlace,apartmentPhone,apartmentPrice;
    private  static final int GalleryPick = 1;
    private Uri ImageUri;
    private String saveCurrentDate,saveCurrentTime,apartmentRandomKey,downLoadImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_apartment);
        apartmentPicture = findViewById(R.id.btn_picture);
        ApartmentSubmit = findViewById(R.id.apartment_Post);
        apartmentDescription = findViewById(R.id.apartment_description);
        apartmentPlace = findViewById(R.id.apartment_city);
        apartmentPrice = findViewById(R.id.apartment_price);
        apartmentPhone = findViewById(R.id.apartment_phone);
        apartmentRef= FirebaseStorage.getInstance().getReference().child("Houses");
        Ref = FirebaseDatabase.getInstance().getReference().child("Houses");

        apartmentPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }
        });
        ApartmentSubmit .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateApartment();
            }
        });
    }

    private void ValidateApartment() {

        String description = apartmentDescription.getText().toString();
        String price = apartmentPrice.getText().toString();
        String place = apartmentPlace.getText().toString();
        String phone = apartmentPhone.getText().toString();
        if (ImageUri == null){
            Toast.makeText(getBaseContext(),"Apartment Image Is Madatory",Toast.LENGTH_LONG).show();


        }else if (description.isEmpty()){
            apartmentDescription.setError("Write Apartment Description");
            return;
        }else if (price.isEmpty()){
            apartmentPrice.setError("Write Rent Price");
            return;
        }
        else if (place.isEmpty()){
            apartmentPlace.setError("Write Apartment Place");
            return;
        }
        else if (phone.isEmpty()){
            apartmentPhone.setError("Write Phone Number");
            return;
        }else {

            storeApartmentImageInfo();
        }



    }

    private void storeApartmentImageInfo() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MMM,yyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        apartmentRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath= apartmentRef.child(ImageUri.getLastPathSegment()+apartmentRandomKey+ ".jpg");

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
        apartmentMap.put("houseimage",downLoadImageUrl);
      //  apartmentMap.put("id",apartmentRandomKey);
        apartmentMap.put("place",apartmentPlace);

        apartmentMap.put("description",apartmentDescription);
        apartmentMap.put("price",apartmentPrice);


        apartmentMap.put("phone",apartmentPhone);
        apartmentMap.put("date",saveCurrentDate);
        apartmentMap.put("time",saveCurrentTime);

        Ref.push().setValue(new Houses(downLoadImageUrl,apartmentDescription.getText().toString(),apartmentPlace.getText()
        .toString(),apartmentPrice.getText().toString(),apartmentPhone
                .getText().toString(),saveCurrentDate,
                saveCurrentTime)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    apartmentPicture.setImageURI(null);
                    apartmentDescription.setText("");
                    apartmentPhone.setText("");
                    apartmentPlace.setText("");
                    apartmentPrice.setText("");
                    Toast.makeText(getBaseContext(),"Apartment Image Add Successful",Toast.LENGTH_LONG).show();
                }
            }
       });
//       Ref .child(apartmentRandomKey).updateChildren(apartmentMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//                if (task.isSuccessful()){
//                    Toast.makeText(getBaseContext(),"Apartment Image Add Successful",Toast.LENGTH_LONG).show();
//
//        apartmentPicture.setImageURI(null);
//        apartmentDescription.setText("");
//        apartmentPhone.setText("");
//        apartmentPlace.setText("");
//        apartmentPrice.setText("");
//                }
//                else {
//                    String message = task.toString();
//                    Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//        });
//
//



    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode ==RESULT_OK && data != null){
            ImageUri = data.getData();
            apartmentPicture.setImageURI(ImageUri);

        }
    }
}

