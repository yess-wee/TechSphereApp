// This class is used to hold the view of the recycler view item.

// Importing all the required packages
package com.example.authtechsphere.Admin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authtechsphere.R;

// Creating a class for MyViewHolder
public class MyViewHolder extends RecyclerView.ViewHolder {

    // Declaring all the variables
    TextView mName;
    TextView mLink;
    ImageView mImage;
    Button mDownload;
    Button mDelete;

    // Creating a constructor for MyViewHolder
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        // Assigning the variables to the respective views
        mName=itemView.findViewById(R.id.name);
        mLink=itemView.findViewById(R.id.link);
        mDownload=itemView.findViewById(R.id.down);
        mDelete=itemView.findViewById(R.id.delete);
        mImage=itemView.findViewById(R.id.image);


    }
}
