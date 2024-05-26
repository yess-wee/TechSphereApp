package com.yash.authtechsphere.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yash.authtechsphere.LoginActivity;
import com.yash.authtechsphere.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocLock extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    private TextView buttonChoose;
    private TextView buttonUpload;
    private ImageView imageView;

    private Uri filePath;
    private CardView cardView;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_lock);

        buttonChoose = (TextView) findViewById(R.id.buttonChoose);
        buttonUpload = (TextView) findViewById(R.id.buttonUpload);
        cardView = (CardView) findViewById(R.id.cardView);
        imageView = (ImageView) findViewById(R.id.imageView);


        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


        imageView.setVisibility(View.VISIBLE);


        fAuth = FirebaseAuth.getInstance();
        Drawable myDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
        imageView.setImageDrawable(myDrawable);


        if (fAuth.getCurrentUser() == null || (!fAuth.getCurrentUser().isEmailVerified())) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else {

            askPermission();
        }


    }


    private void askPermission() {

        PermissionListener permissionListener = new PermissionListener() {

            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
            }
        };


        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String retrieveFileName = null;


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            retrieveFileName = data.getData().getLastPathSegment().toLowerCase();



            if (retrieveFileName.contains("image")) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (retrieveFileName.contains(".ai")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.ai);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".apk")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.apk);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".avi")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.avi);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".css")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.css);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".csv")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.csv);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".dbf")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.dbf);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".doc")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.doc);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".exe")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.exe);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".fla")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.fla);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".gif")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.gif);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".htm")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.html);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".html")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.html);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".jpg")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.jpg);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".json")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.jsn);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".htm")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.html);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".mp3")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.mp3);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".mp4")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.mp4);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".ods")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.ods);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".odt")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.odt);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".pdf")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.pdf);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".png")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.png);
                imageView.setImageDrawable(myDrawable);

            } else if (retrieveFileName.contains(".ppt")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.ppt);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".pptx")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.ppt);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".svg")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.svg);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".txt")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.txt);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".xls")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.xls);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".xlsx")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.xls);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".xml")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.xml);
                imageView.setImageDrawable(myDrawable);
            } else if (retrieveFileName.contains(".zip")) {
                Drawable myDrawable = getResources().getDrawable(R.drawable.zip);
                imageView.setImageDrawable(myDrawable);
            }
        } else {
            Drawable myDrawable = getResources().getDrawable(R.drawable.question);
            imageView.setImageDrawable(myDrawable);
        }


        cardView.setVisibility(View.VISIBLE);


        EditText choosenFileName = findViewById(R.id.fileNameToBeSaved);
        if (retrieveFileName.contains("image")) {
            choosenFileName.setText(retrieveFileName + ".jpg");
        } else {
            choosenFileName.setText(retrieveFileName);
        }
    }


    private void uploadFile() {

        EditText fileName = findViewById(R.id.fileNameToBeSaved);

        if (fileName.getText().toString().contains(".jpg") || fileName.getText().toString().contains(".mp3") || fileName.getText().toString().contains(".mp4")
                || fileName.getText().toString().contains(".pdf") || fileName.getText().toString().contains(".txt") || fileName.getText().toString().contains(".doc")
                || fileName.getText().toString().contains(".docx") || fileName.getText().toString().contains(".ppt") || fileName.getText().toString().contains(".pptx")
                || fileName.getText().toString().contains(".apk") || fileName.getText().toString().contains(".xls") || fileName.getText().toString().contains(".xlsx")
                || fileName.getText().toString().contains(".zip") || fileName.getText().toString().contains(".gif") || fileName.getText().toString().contains(".htm")
                || fileName.getText().toString().contains(".html") || fileName.getText().toString().contains(".jpeg") || fileName.getText().toString().contains(".jpg")
                || fileName.getText().toString().contains(".ods") || fileName.getText().toString().contains(".odt") || fileName.getText().toString().contains(".svg")
                || fileName.getText().toString().contains(".ai") || fileName.getText().toString().contains(".exe") || fileName.getText().toString().contains(".fla")
                || fileName.getText().toString().contains(".json") || fileName.getText().toString().contains(".xml") || fileName.getText().toString().contains(".png")
                || fileName.getText().toString().contains(".exe")) {


            if (filePath != null) {

                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading");
                progressDialog.show();

                String name = fileName.getText().toString().trim();
                StorageReference riversRef = storageReference.child(fAuth.getCurrentUser().getEmail()).child(name);

                riversRef.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                                        Map<String, Object> user = new HashMap<>();
                                        user.put("name", riversRef.getName());
                                        user.put("link", uri.toString());

                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        DocumentReference documentReference = db.collection(fAuth.getCurrentUser().getEmail()).document();
                                        documentReference.set(user);
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // If the upload is not successfull, hiding the progress dialog
                                progressDialog.dismiss();

                                // And displaying error message
                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                // Calculating progress percentage
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                // Displaying percentage in progress dialog
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            }
                        });
            }

            else {
                Toast.makeText(getApplicationContext(), "File not Selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            fileName.setError("File Extension required!");
            return;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonChoose) {
            showFileChooser();
        }
        else if (view == buttonUpload) {
            uploadFile();
        } else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

//            case R.id.logout_id:
//                fAuth.signOut();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                finish();
//                break;
            default:
                Toast.makeText(DocLock.this, "Error", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public void showUploads(View view) {

        Intent i = new Intent(getApplicationContext(), DownloadFiles.class);
        startActivity(i);
        finish();
    }

}








