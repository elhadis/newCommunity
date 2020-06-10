package com.rgbat.mycommunity.Holders;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rgbat.mycommunity.R;

import java.util.Random;


public class FidWorkAdabter extends RecyclerView.ViewHolder {
     public TextView workCity,workType,workNumber,shift,dateTime;

    public FidWorkAdabter(@NonNull View itemView) {
        super(itemView);
        workCity = itemView.findViewById(R.id.editText_place);
        workType = itemView.findViewById(R.id.editText_type);
        workNumber = itemView.findViewById(R.id.editText_number);
        dateTime= itemView.findViewById(R.id.editText_dateTime);
        shift = itemView.findViewById(R.id.editText_shift);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255),
                rnd.nextInt(255));
        itemView.setBackgroundColor(color);


    }
}
