package com.yash.authtechsphere.Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.authtechsphere.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    DownloadFiles downloadFiles;
    ArrayList<DownModel> downModels;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    public MyAdapter(DownloadFiles downloadFiles, ArrayList<DownModel> downModels) {
        this.downloadFiles = downloadFiles;
        this.downModels = downModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(downloadFiles.getBaseContext());
        View view = layoutInflater.inflate(R.layout.elements, null, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {

        myViewHolder.mName.setText(downModels.get(i).getName());
        myViewHolder.mLink.setText(downModels.get(i).getLink());

        if (downModels.get(i).getName().endsWith(".ai") ){
            myViewHolder.mImage.setImageResource(R.drawable.ai);
        } else if (downModels.get(i).getName().endsWith(".apk")) {
            myViewHolder.mImage.setImageResource(R.drawable.apk);
        } else if (downModels.get(i).getName().endsWith(".avi")) {
            myViewHolder.mImage.setImageResource(R.drawable.avi);
        }  else if (downModels.get(i).getName().endsWith(".css")) {
            myViewHolder.mImage.setImageResource(R.drawable.css);
        } else if (downModels.get(i).getName().endsWith(".csv")) {
            myViewHolder.mImage.setImageResource(R.drawable.csv);
        } else if (downModels.get(i).getName().endsWith(".dbf")) {
            myViewHolder.mImage.setImageResource(R.drawable.dbf);
        } else if (downModels.get(i).getName().endsWith(".doc")) {
            myViewHolder.mImage.setImageResource(R.drawable.doc);
        } else if (downModels.get(i).getName().endsWith(".docx")) {
            myViewHolder.mImage.setImageResource(R.drawable.doc);
        } else if (downModels.get(i).getName().endsWith(".exe")) {
            myViewHolder.mImage.setImageResource(R.drawable.exe);
        } else if (downModels.get(i).getName().endsWith(".fla")) {
            myViewHolder.mImage.setImageResource(R.drawable.fla);
        } else if (downModels.get(i).getName().endsWith(".gif")) {
            myViewHolder.mImage.setImageResource(R.drawable.gif);
        } else if (downModels.get(i).getName().endsWith(".html")) {
            myViewHolder.mImage.setImageResource(R.drawable.html);
        } else if (downModels.get(i).getName().endsWith(".htm")) {
            myViewHolder.mImage.setImageResource(R.drawable.html);
        } else if (downModels.get(i).getName().endsWith(".jpeg")) {
            myViewHolder.mImage.setImageResource(R.drawable.jpg);
        } else if (downModels.get(i).getName().endsWith(".jpg")) {
            myViewHolder.mImage.setImageResource(R.drawable.jpg);
        } else if (downModels.get(i).getName().endsWith(".mp3")) {
            myViewHolder.mImage.setImageResource(R.drawable.mp3);
        } else if (downModels.get(i).getName().endsWith(".mp4")) {
            myViewHolder.mImage.setImageResource(R.drawable.mp4);
        } else if (downModels.get(i).getName().endsWith(".pdf")) {
            myViewHolder.mImage.setImageResource(R.drawable.pdf);
        } else if (downModels.get(i).getName().endsWith(".png")) {
            myViewHolder.mImage.setImageResource(R.drawable.png);
        } else if (downModels.get(i).getName().endsWith(".ppt")) {
            myViewHolder.mImage.setImageResource(R.drawable.ppt);
        } else if (downModels.get(i).getName().endsWith(".pptx")) {
            myViewHolder.mImage.setImageResource(R.drawable.ppt);
        } else if (downModels.get(i).getName().endsWith(".ods")) {
            myViewHolder.mImage.setImageResource(R.drawable.ods);
        } else if (downModels.get(i).getName().endsWith(".odt")) {
            myViewHolder.mImage.setImageResource(R.drawable.odt);
        } else if (downModels.get(i).getName().endsWith(".svg")) {
            myViewHolder.mImage.setImageResource(R.drawable.svg);
        } else if (downModels.get(i).getName().endsWith(".txt")) {
            myViewHolder.mImage.setImageResource(R.drawable.txt);
        } else if (downModels.get(i).getName().endsWith(".xls")) {
            myViewHolder.mImage.setImageResource(R.drawable.xls);
        } else if (downModels.get(i).getName().endsWith(".xlsx")) {
            myViewHolder.mImage.setImageResource(R.drawable.xls);
        } else if (downModels.get(i).getName().endsWith(".xml")) {
            myViewHolder.mImage.setImageResource(R.drawable.xml);
        } else if (downModels.get(i).getName().endsWith(".zip")) {
            myViewHolder.mImage.setImageResource(R.drawable.zip);
        } else {
            myViewHolder.mImage.setImageResource(R.drawable.question);
        }

        myViewHolder.mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference riversRef = storageReference.child(fAuth.getCurrentUser().getEmail()).child(downModels.get(i).getName());

                Task<Uri> url = riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    public void onSuccess(Uri uri) {

                        // Downloading the file
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setTitle(downModels.get(i).getName());
                        request.setDescription("Downloading file...");

                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downModels.get(i).getName());

                        DownloadManager manager = (DownloadManager) downloadFiles.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);

                        return;

                    }
                });

            }
        });

        myViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Create a dialog box to confirm the deletion

                Log.d("TAG", "Delete");

                AlertDialog.Builder builder = new AlertDialog.Builder(downloadFiles);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this file?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StorageReference riversRef = storageReference.child(fAuth.getCurrentUser().getEmail()).child(downModels.get(i).getName());
                        riversRef.delete();

                        FirebaseFirestore.getInstance().collection(fAuth.getCurrentUser().getEmail())
                                .whereEqualTo("name", downModels.get(i).getName())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot snapshot : snapshotList) {
                                            FirebaseFirestore.getInstance().collection(fAuth.getCurrentUser().getEmail()).document(snapshot.getId()).delete();
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(downloadFiles, "Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        myViewHolder.itemView.setVisibility(View.INVISIBLE);


                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }

                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }









//                Log.d("TAG","Delete");
//
//                StorageReference riversRef = storageReference.child(fAuth.getCurrentUser().getEmail()).child(downModels.get(i).getName());
//                riversRef.delete();
//
//                FirebaseFirestore.getInstance().collection(fAuth.getCurrentUser().getEmail())
//                        .whereEqualTo("name",downModels.get(i).getName())
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//
//                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
//                                for(DocumentSnapshot snapshot: snapshotList){
//                                    FirebaseFirestore.getInstance().collection(fAuth.getCurrentUser().getEmail()).document(snapshot.getId()).delete();
//                                }
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//
//                            }
//                        });
//
//                myViewHolder.itemView.setVisibility(View.INVISIBLE);
//
//            }
//        });
//
//
//    }

    public void downloadFile(Context context, String fileName,String destinationDirectory, String url) {

        File file = new File(Environment.getExternalStorageDirectory(),"Download/"+fileName);
        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri)

        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationUri(Uri.fromFile(file));
        downloadmanager.enqueue(request);
    }


    @Override
    public int getItemCount() {
        return downModels.size();
    }
}
