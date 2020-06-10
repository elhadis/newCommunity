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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rgbat.mycommunity.Holders.PrroductsHolder;
import com.rgbat.mycommunity.Model.Products;
import com.squareup.picasso.Picasso;

public class Productsctivity extends AppCompatActivity {

    private RecyclerView productList;
    private LinearLayoutManager linearLayoutManager;
    private Dialog loadingDialog;
    private DatabaseReference Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsctivity);
        productList = findViewById(R.id.product_main_list);
        linearLayoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        productList.setHasFixedSize(true);


        Ref = FirebaseDatabase.getInstance().getReference().child("Products");






        loadingDialog = new Dialog(Productsctivity.this);
        loadingDialog.setContentView(R.layout.loading_progress);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadingDialog.show();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(Ref,Products.class).build();
        FirebaseRecyclerAdapter<Products, PrroductsHolder> adapter = new
                FirebaseRecyclerAdapter<Products, PrroductsHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PrroductsHolder holder, int position, @NonNull Products model) {
                        final String id = getRef(position).getKey();
                        holder.productPrice.setText("Price = " +model.getPrice());
                        holder.productPhone.setText("phone " +model.getPhone());
                      //  holder.place.setText("Place "+ model.getPlace());
                        holder.productTime.setText(model.getDate()+ " " +model.getTime());

                        Picasso.get().load(model.getProductimage()).into(holder.productImage);
                        loadingDialog.dismiss();
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence option[] = new CharSequence[]{
                                        "Yes",
                                        "NO"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Productsctivity.this);
                                builder.setTitle("Do You Want Delete Item ? ");
                                builder.setItems(option, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == 0){
                                            deleteItem(id);
                                        }
//                                        else {
//                                            finish();
//                                        }


                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public PrroductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
                        return new PrroductsHolder(view);
                    }
                };
        productList.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteItem(String id) {
        Ref.child(id).removeValue();

    }
}
