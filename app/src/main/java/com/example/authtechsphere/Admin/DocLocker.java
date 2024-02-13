package com.example.authtechsphere.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.authtechsphere.R;
import com.example.authtechsphere.databinding.ActivityDocLockerBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class DocLocker extends AppCompatActivity {

    private static final String TAG = "ADD_PDF_TAG";
    ActivityDocLockerBinding binding;
    FirebaseAuth fAuth;

    private static final int PDF_PICK_CODE = 1000;
    private Uri pdfUri = null;

    private ArrayList<ModelCategory> categoryArrayList;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_locker);

        binding = ActivityDocLockerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();

        loadPdfCategories();

        pd = new ProgressDialog(this);
        pd.setTitle("Please wait");
        pd.setCanceledOnTouchOutside(false);


        binding.backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.attachIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPickPdf();
            }
        });

        //category choose
        binding.edtcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryPickDialog();
            }
        });

        binding.btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validData();

            }
        });


    }

    private String title="", description="", category="";

    private void validData(){

        Log.d(TAG,"validData: validating data");

        title = binding.edtbookn.getText().toString().trim();
        description = binding.edtbookdesc.getText().toString().trim();
        category = binding.edtcategory.getText().toString().trim();

        if(TextUtils.isEmpty(title)){
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(description)){
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(category)){
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
        }
        else if(pdfUri==null){
            Toast.makeText(this, "Pick pdf", Toast.LENGTH_SHORT).show();
        }
        else {
            uploadPdfToStorage();
        }

    }

    private void uploadPdfToStorage(){
        Log.d(TAG,"uploadPdfToStorage: uploading to storage");

        pd.setMessage("Uploading pdf");
        pd.show();

        long timestamp = System.currentTimeMillis();

        String filePathAndName = "Pdfs/"+timestamp;
        StorageReference sr = FirebaseStorage.getInstance().getReference(filePathAndName);
        sr.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG,"onSuccess: PDF uploaded successfully");
                        Log.d(TAG,"onSuccess: getting pdf url");

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        String uploadPdfUrl = ""+uriTask.getResult();

                        uploadPdfInfoToDb(uploadPdfUrl,timestamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();

                        Log.d(TAG,"onFailure: PDF uploading failed due to "+e.getMessage());
                        Toast.makeText(DocLocker.this, "PDF uploading failed due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadPdfInfoToDb(String uploadPdfUrl, long timestamp){
        Log.d(TAG,"uploadPdfInfoToDb: pdf uploaded to the db");
        pd.setMessage("Upload pdf info");

        String uid = fAuth.getUid();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("uid", ""+uid);
        hashMap.put("id", ""+timestamp);
        hashMap.put("title",""+title);
        hashMap.put("description",""+description);
        hashMap.put("category",""+category);
        hashMap.put("url", ""+uploadPdfUrl);
        hashMap.put("timestamp",timestamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pdf");
        ref.child(""+timestamp)
                .setValue(timestamp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.dismiss();
                        Log.d(TAG,"onSuccess: uploaded successfully");
                        Toast.makeText(DocLocker.this, "pdf uploaded successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Log.d(TAG,"onFailure: Failed to upload"+e.getMessage());
                        Toast.makeText(DocLocker.this, "Failed to upload pdf"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void loadPdfCategories(){
        Log.d(TAG, "loadPdfCategories: Loading pdf categories");
        categoryArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();

                for(DataSnapshot ds:snapshot.getChildren()){
                    ModelCategory model = ds.getValue(ModelCategory.class);
                    categoryArrayList.add(model);

                    Log.d(TAG,"onChange: "+model.getCategory());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void categoryPickDialog(){
        Log.d(TAG, "categoryPickDialog: showing category pick dialog");

        String[] categoriesArray = new String[categoryArrayList.size()];

        for(int i=0;i< categoryArrayList.size();i++){
            categoriesArray[i] = categoryArrayList.get(i).getCategory();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Category")
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String category = categoriesArray[i];
                        binding.edtcategory.setText(category);

                        Log.d(TAG, "onClick: Selected Category"+category);
                    }
                })
                .show();

    }

    private void pdfPickPdf(){
        Log.d(TAG, "pdfPickIntent: starting pdf pick intent");

        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i,"SELECT PDF"),PDF_PICK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_OK){
            if(requestCode == PDF_PICK_CODE){
                Log.d(TAG, "onActivityResult: PDF Picked");

                pdfUri = data.getData();

                Log.d(TAG, "onActivityResult: URI: "+pdfUri);
            }
        }
        else {

            Log.d(TAG, "onActivityResult: Cancelled picking pdf");
            Toast.makeText(this, "Cancelled picking pdf", Toast.LENGTH_SHORT).show();

        }
    }
}