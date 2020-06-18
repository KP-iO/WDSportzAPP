package com.example.wdsportz.SideNav;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wdsportz.Adapters.FavouriteTeamsAdapter;
import com.example.wdsportz.R;
import com.example.wdsportz.ViewModels.FavouriteTeamsViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.widget.MotionScene.TAG;
//import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Frag_Profile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Frag_Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayList<String> teamIds =  new ArrayList<String>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    RecyclerView RvTeams;
    //storage
    StorageReference storageReference;
    //path where images of user profile will be stored
    String storagePath = "Users_Profile_Cover_Imgs/";
    String USERS = "Users";
    FavouriteTeamsViewModel favouriteTeamsViewModel;
    public static ArrayList<String> listFavourite = new ArrayList<String>();
    public static ArrayList<String> listFavourite1 = new ArrayList<String>();
    ArrayList<FavouriteTeamsViewModel> listTeams = new ArrayList<FavouriteTeamsViewModel>();

    //views
    Button fab;
    ImageView avatarIv, coverIv;
    TextView nameTv, emailTv, phoneTv;
    ProgressDialog pd;
    //permissions constants
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;
    private static final int IMAGE_PICK_GALLERY_CODE =300;
    private static final int IMAGE_PICK_CAMERA_CODE  =400;
    //arrays of permissions to be requested
    String cameraPermissions[];
    String storagePermissions[];
    //uri of picked image
    Uri image_uri;
    //for checking profile or cover photo
    String profileOrCoverPhoto;
    FavouriteTeamsAdapter favouriteTeamsAdapter;
    String uid;
    FirebaseFirestore database = FirebaseFirestore.getInstance();




 private OnFragmentInteractionListener mListener;

    public Frag_Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Profile newInstance(String param1, String param2) {
        Frag_Profile fragment = new Frag_Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        listFavourite = new ArrayList<>();
        listFavourite1 = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("Users");
        //init arrays of permissions
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storageReference = FirebaseStorage.getInstance().getReference();

        avatarIv = view.findViewById(R.id.UserImg);
        nameTv = view.findViewById(R.id.nameTv);
        emailTv = view.findViewById(R.id.emailTv);
        fab = view.findViewById(R.id.Edit_Profile);
        coverIv = view.findViewById(R.id.coverIv);
        RvTeams = view.findViewById(R.id.favouriteTeamRV);
        uid = user.getUid();

        //progress dialog
        pd = new ProgressDialog(getActivity());

        //permissions constants
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.get("name").toString();
                                String email = document.get("email").toString();
                                String image = document.get("image").toString();
//                                String cover = document.get("cover").toString();
                                Log.d(TAG, image);

                                // Set to user
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(Uri.parse(image))
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                    }
                                }
                            });

                    //set data
                    nameTv.setText(name);
                    emailTv.setText(email);

                    try {

                        Glide.with(view)
                                .load(image)
                                .into(avatarIv);
//                        Glide.with(view)
//                                .load(cover)
//                                .into(coverIv);
                    }
                    catch (Exception e) {
                        Glide.with(view)
                                .load(R.drawable.ic_add_image)
                                .into(avatarIv);

                    }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
//                                String name = document.get("Match_Name").toString();
//                                String email = document.get("Match_Name").toString();
//                                String image = document.get("Match_Name").toString();
//                    String cover = document.get("Match_Name").toString();






//        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    //get data
//
//                    String name = "" + ds.child("name").getValue();
//                    String email = "" + ds.child("email").getValue();
//                    String image = "" + ds.child("image").getValue();
//                    String cover = "" + ds.child("cover").getValue();
//
//                    // Set to user
//                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                            .setPhotoUri(Uri.parse(image))
//                            .build();
//
//                    user.updateProfile(profileUpdates)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Log.d(TAG, "User profile updated.");
//                                    }
//                                }
//                            });
//
//                    //set data
//                    nameTv.setText(name);
//                    emailTv.setText(email);
//
//                    try {
//
//                        Glide.with(view)
//                                .load(image)
//                                .into(avatarIv);
//                        Glide.with(view)
//                                .load(cover)
//                                .into(coverIv);
//                    }
//                    catch (Exception e){
//                        Glide.with(view)
//                                .load(R.drawable.ic_add_image)
//                                .into(avatarIv);
//
//                    }
//
//
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//            Edit Profile
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog();
            }
        });
        favouriteLoader();
//        league1();
//        Log.d ("myTeams", String.valueOf(listFavourite));

        return view;
    }

    private boolean checkStoragePermission(){
        //check
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_DENIED);
        return result;


    }

    private void requestStoragePermission(){
        //request runtime storage permission
        requestPermissions(storagePermissions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        //check if storage permission is enabled or not
        //return true if enabled
        //return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==(PackageManager.PERMISSION_DENIED);

        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_DENIED);






        return result && result1 ;

    }

    private void requestCameraPermission(){
        //request runtime storage permission
        requestPermissions( cameraPermissions,CAMERA_REQUEST_CODE);
    }
    private void showEditProfileDialog() {
        /* Show dialog containing options
        1) Edit Profile Picture
        2) Edit Cover photo
        3) Edit Name
        4) Edit Phone *

         */

        String options [] = {"Edit Profile Picture", "Edit Cover photo", "Edit Name", "Edit Phone"};
        //alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // set title
        builder.setTitle("Choose Action");
        // set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        pd.setMessage("Updating Profile Picture");
                        profileOrCoverPhoto = "image";

                        showImagePicDiolog();
                        break;
                    case 1:
                        pd.setMessage("Updating Cover Photo");
                        profileOrCoverPhoto = "cover";
                        showImagePicDiolog();
                        break;
                    case 2:
                        //name clicked
                        pd.setMessage("Updating Name");
                        showNamePhoneUpdateDialog("name");
                        break;
                    case 3:
                        //phone clicked
                        pd.setMessage("Updating Phone");
                        showNamePhoneUpdateDialog("phone");
                        break;
                }


            }
        });
        builder.create().show();

    }

    private void showNamePhoneUpdateDialog(final String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update " + key);

        //set layout of dialog
        Context context;
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10,10);
        //add edit text
        final EditText editText = new EditText(getActivity());
        editText.setHint("Enter"+key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //add buttons in dialog
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input text from edit text
                String value = editText.getText().toString().trim();
                //validate if user has entered something or not
                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), "Updated...", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                }
                else {
                    Toast.makeText(getActivity(), "Please enter "+key,Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.create().show();

    }

    private void showImagePicDiolog() {
        // show dialog containing options camera and Gallery to the image

        String options [] = {"Camera", "Gallery"};
        //alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // set title
        builder.setTitle("Pick Image From");
        // set items to dialog
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //Camera clicked
                     if(!checkCameraPermission()){
                         requestCameraPermission();
                     }
                     else {pickFromCamera();
                     }
                } else if (which == 1){
                    //Gallery clicked
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }

                }




            }
        });
        builder.create().show();
    }

    @Override
    public void  onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // method called when user allow or deny from permission request dialog

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                // PICKING FROM CAMERA, FIRST CHECK IF CAMERA AND STORAGE PERMISSIONS ALLOWED OR NOT
                if (grantResults.length >0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        pickFromCamera();
                    } else {
                        //permission denied
                        Toast.makeText(getActivity(), "Please enable camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                //picking from gallery
                if (grantResults.length >0){
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted){
                        pickFromGallery();
                    } else {
                        //permission denied
                        Toast.makeText(getActivity(), "Please enable camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }




        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // method will be called after picking image from Camera or Gallery
        if (resultCode == RESULT_OK ){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                //image is picked from gallery, get uri of image
                image_uri = data.getData();
                uploadProfileCoverPhoto(image_uri);

            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //image is picked from camera, get uri of image
                uploadProfileCoverPhoto(image_uri);


            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCoverPhoto(Uri uri) {
        pd.show();
        //path and name of image to be stored in firebase storage

        String filePathAndName = storagePath+ ""+ profileOrCoverPhoto +"_"+ user.getUid();

        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        //check if image is uploaded or not and url is received
                        if ((uriTask.isSuccessful())){

                            HashMap<String, Object> results = new HashMap<>();
                            results.put(profileOrCoverPhoto, downloadUri.toString());


                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //url in database of user is added successfully
                                            //dismiss progress bar
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), "Image Updated. . .", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //url in database of user is added successfully
                                            //dismiss progress bar
                                            pd.dismiss();
                                            Toast.makeText(getActivity(), "Error Updating Image ...", Toast.LENGTH_SHORT).show();


                                        }
                                    });

                        }
                        else {
                            //error
                            pd.dismiss();
                            Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }



    private void pickFromGallery() {
        //pick from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK );
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        //Intent of picking image from device camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //put image uri
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }



    private void favouriteLoader() {
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        RvTeams.setLayoutManager(new LinearLayoutManager(getContext()));

        String userID = user.getUid();



        DocumentReference docRef = database.collection("Users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
//                    List<String> list = new ArrayList<>();
                    if (document.exists()) {

                        teamIds = (ArrayList<String>)document.get("Favourite Teams");
                        Log.d(TAG, String.valueOf(teamIds));
                        Log.d(TAG, String.valueOf(teamIds.size()));


                        listFavourite.addAll(teamIds);
                        Log.d("listFavourite array", String.valueOf(listFavourite));
                        for (int i = 0; i <listFavourite.size()-1; i++ ){
                            Log.d("TEAM ID", listFavourite.get(i));
                            int finalI = i;
                           database.collection("Leagues").document("Non League Div One - Isthmian North").collection("Teams")
                                    .whereEqualTo("teamName", listFavourite.get(i))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

//                                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                                    Log.d("Looking for data", "looking for data league 1");
                                                    listTeams.add(new FavouriteTeamsViewModel(document.get("teamName").toString(), document.get("teamLogo").toString()));

                                                    favouriteTeamsAdapter = new FavouriteTeamsAdapter(getContext(), listTeams);
                                                    RvTeams.setAdapter(favouriteTeamsAdapter);

                                                    for (int j = 0; j < listTeams.size() - 1; j++) {

                                                        Log.d(TAG, (" Team Name = " + listTeams.get(j).getTeam()));

                                                    }

                                                }
                                            } else {
                                                Log.d(TAG, "No such document");


                                            }
                                        }
                                    });

                            database.collection("Leagues").document("Non League Div One - Isthmian South").collection("Teams")
                                    .whereEqualTo("teamName", listFavourite.get(i))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

//                                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                                    Log.d("Looking for data", "looking for data league 2");
                                                    listTeams.add(new FavouriteTeamsViewModel(document.get("teamName").toString(), document.get("teamLogo").toString()));

                                                    favouriteTeamsAdapter = new FavouriteTeamsAdapter(getContext(), listTeams);
                                                    RvTeams.setAdapter(favouriteTeamsAdapter);

                                                    for (int j = 0; j < listTeams.size() - 1; j++) {

                                                        Log.d(TAG, (" Team Name = " + listTeams.get(j).getTeam()));

                                                    }

                                                }
                                            } else {
                                                Log.d(TAG, "No such document");


                                            }
                                        }
                                    });

                            database.collection("Leagues").document("Non League Div One - Northern North").collection("Teams")
                                    .whereEqualTo("teamName", listFavourite.get(i))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

//                                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                                    Log.d("Looking for data", "looking for data league 3");
                                                    listTeams.add(new FavouriteTeamsViewModel(document.get("teamName").toString(), document.get("teamLogo").toString()));

                                                    favouriteTeamsAdapter = new FavouriteTeamsAdapter(getContext(), listTeams);
                                                    RvTeams.setAdapter(favouriteTeamsAdapter);

                                                    for (int j = 0; j < listTeams.size() - 1; j++) {

                                                        Log.d(TAG, (" Team Name = " + listTeams.get(j).getTeam()));

                                                    }

                                                }
                                            } else {
                                                Log.d(TAG, "No such document");


                                            }
                                        }
                                    });

                            database.collection("Leagues").document("Non League Premier - Isthmian").collection("Teams")
                                    .whereEqualTo("teamName", listFavourite.get(i))
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {

//                                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());

                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                                    Log.d("Looking for data", "looking for data league 4");
                                                    listTeams.add(new FavouriteTeamsViewModel(document.get("teamName").toString(), document.get("teamLogo").toString()));

                                                    favouriteTeamsAdapter = new FavouriteTeamsAdapter(getContext(), listTeams);
                                                    RvTeams.setAdapter(favouriteTeamsAdapter);

                                                    for (int j = 0; j < listTeams.size() - 1; j++) {

                                                        Log.d(TAG, (" Team Name = " + listTeams.get(j).getTeam()));

                                                    }

                                                }
                                            } else {
                                                Log.d(TAG, "No such document");


                                            }
                                        }
                                    });



                        }







//                        Log.d(TAG, "DocumentSnapshot data: " + document.get("Favourite Teams"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }

    public static ArrayList<String> getArrayList()
    {
        return listFavourite;
    }


    public void league1() {
        Log.d("METHOD ENTERED", "league 1");
//        Log.d("Access Id's", String.valueOf(listFavourite));
//
//        listFavourite1 = getArrayList();
        Log.d("Access Id's", String.valueOf(listFavourite));

        for (int i = 0; i <listFavourite.size(); i++ ){
            Task<QuerySnapshot> docRef = database.collection("Leagues").document("Non League Div One - Isthmian North").collection("Teams")
                    .whereEqualTo("teamId", listFavourite.get(i))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Log.d("Looking for data", "looking for data league 1");
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    listTeams.add(new FavouriteTeamsViewModel(document.get("teamLogo").toString(), document.get("teamName").toString()));

                                    favouriteTeamsAdapter = new FavouriteTeamsAdapter(getContext(), listTeams);
                                    RvTeams.setAdapter(favouriteTeamsAdapter);


                                }
                            } else {
                                Log.d(TAG, "No such document");


                            }
                        }
                    });

        }

    }



//myAdapter=new MyAdapter(this,firebaseHelper.retrieve());
//rvOrder.setAdapter(myAdapter);

//        teamLocation.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//
//                listFavourite = new ArrayList<>();
//
//
//                for (DataSnapshot snap :  dataSnapshot.child("Favourite Teams").getChildren()) {
//                    Log.d("Snap TEST", String.valueOf(snap));
//
////                    FavouriteTeamsViewModel favouriteTeamsViewModel = new FavouriteTeamsViewModel snap.getValue(FavouriteTeamsViewModel.class);
////                    Log.d("Favourite ViewModel", String.valueOf(favouriteTeamsViewModel));
//
//                    String teamname = snap.getValue().toString();
//                    String teamlogo = snap.getValue().toString();
//                    Log.d("teamname TEST",teamname);
//                   //listFavourite.add(teamname);
//
//                    FavouriteTeamsViewModel results = new FavouriteTeamsViewModel (teamname,teamlogo);
//                    listFavourite.add(results);
//
//                }
//                favouriteTeamsAdapter = new FavouriteTeamsAdapter(getContext(), listFavourite);
//                RvTeams.setAdapter(favouriteTeamsAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//
//        });
//    }




















    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
