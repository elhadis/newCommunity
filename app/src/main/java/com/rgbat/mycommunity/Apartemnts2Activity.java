package com.rgbat.mycommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rgbat.mycommunity.Holders.HouseHolder;
import com.rgbat.mycommunity.Model.Houses;
import com.squareup.picasso.Picasso;

public class Apartemnts2Activity extends AppCompatActivity {

    private RecyclerView apartmentList;
    private LinearLayoutManager linearLayoutManager;
    private Dialog loadingDialog;
    private DatabaseReference Ref;
    private Button buttonApartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartemnts2);

        apartmentList = findViewById(R.id.apartment_list);
        linearLayoutManager = new LinearLayoutManager(this);
        apartmentList.setLayoutManager(linearLayoutManager);
       linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        apartmentList.setHasFixedSize(true);

      //  buttonApartment= findViewById(R.id.add_apartment);

        Ref = FirebaseDatabase.getInstance().getReference().child("Houses");

        loadingDialog = new Dialog(Apartemnts2Activity.this);
        loadingDialog.setContentView(R.layout.loading_progress);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


//        buttonApartment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getBaseContext(),PostApartmentActivity.class);
//                startActivity(intent);
//            }
//        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        loadingDialog.show();
        loadingData();
    }


    private void loadingData() {



        FirebaseRecyclerOptions<Houses> options = new
                FirebaseRecyclerOptions.Builder<Houses>()
                .setQuery(Ref,Houses.class).build();
        FirebaseRecyclerAdapter<Houses, HouseHolder> adapter = new
                FirebaseRecyclerAdapter<Houses, HouseHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull HouseHolder holder, int position, @NonNull Houses model) {
                        final String id = getRef(position).getKey();
                        holder.description.setText(model.getDescription());
                        holder.price.setText(" Rent price = "+ model.getPrice());
                        holder.place.setText("Apartment City " + model.getPlace());

                        holder.Phone.setText(" Phone "+model.getPhone());
                        holder.date.setText(model.getDate()+ "  "  + model.getTime());


                        Picasso.get().load(model.getHouseimage()).into(holder.imageView);
                        loadingDialog.dismiss();
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options [] = new CharSequence[]{
                                        "YES",
                                        "NO"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Apartemnts2Activity.this);
                                builder.setTitle("Do You Want Delete ");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == 0){
                                            Ref.child(id).removeValue();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });




                    }

                    @NonNull
                    @Override
                    public HouseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_layout,parent,
                                false);
                        return new HouseHolder(view);
                    }
                };
        apartmentList.setAdapter(adapter);
        adapter.startListening();


    }




}
