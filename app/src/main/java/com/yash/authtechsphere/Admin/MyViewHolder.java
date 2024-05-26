package com.yash.authtechsphere.Admin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.authtechsphere.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView mName;
    TextView mLink;
    ImageView mImage;
    Button mDownload;
    Button mDelete;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        mName=itemView.findViewById(R.id.name);
        mLink=itemView.findViewById(R.id.link);
        mDownload=itemView.findViewById(R.id.down);
        mDelete=itemView.findViewById(R.id.delete);
        mImage=itemView.findViewById(R.id.image);


    }
}
