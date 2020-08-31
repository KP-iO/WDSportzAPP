package com.example.wdsportz.MainFragments;

/**
 * Created by khrishawn
 */

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.wdsportz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.widget.MotionScene.TAG;
//import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class frag_Register extends Fragment {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private OnFragmentInteractionListener mListener;
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;
    private static final int IMAGE_PICK_GALLERY_CODE =300;
    private static final int IMAGE_PICK_CAMERA_CODE  =400;
    //arrays of permissions to be requested
    String cameraPermissions[];
    String storagePermissions[];
    ImageView avatar;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    //uri of picked image
    Uri image_uri;
    Uri image;
   Uri image1;
    Uri noIMG = Uri.parse("app/src/main/res/drawable/ic_profileimg.xml");
    //for checking profile or cover photo
    String profileOrCoverPhoto;
    ProgressDialog pd;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ImageView avatarREG;
    public boolean imageSelected;
    String downloadUri;

//check
    //storage
//    StorageReference storageReference;
    //path where images of user profile will be stored
    String storagePath = "Users_Profile_Cover_Imgs/";
    String uid;


    public frag_Register() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);

    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        final EditText _txtpass = view.findViewById(R.id.originalPassword);
        final EditText _txtemail = view.findViewById(R.id.Email);

        EditText _txtfname = view.findViewById(R.id.Name);
        Button button = view.findViewById(R.id.btnCreateAcc);
        String username = _txtfname.getText().toString();
        ImageView avatar = view.findViewById(R.id.avatarReg);
        CheckBox checkBox = view.findViewById(R.id.ChkBoxTC);
        EditText confirm = view.findViewById(R.id.confirmText);
        avatarREG = view.findViewById(R.id.avatarReg);
        imageSelected = false;

        //progress dialog
        pd = new ProgressDialog(getActivity());

        databaseReference = firebaseDatabase.getReference("Users");
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storageReference = FirebaseStorage.getInstance().getReference();
//        image1 =storageReference.child("Users_Profile_Cover_Imgs/Linkedin.jpeg").getDownloadUrl();

//        storageReference.child("Users_Profile_Cover_Imgs/Linkedin.jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(view)
//                        .load(Uri.parse(String.valueOf(uri)))
//                        .into(avatarREG);
//                Log.d("Read", "Success");
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
//        Uri path = Uri.parse("https://firebasestorage.googleapis.com/v0/b/wdsportz-3e91f.appspot.com/o/Users_Profile_Cover_Imgs%2FLinkedin.jpeg?alt=media&token=56f8a94b-8db6-4c4d-8237-8b1feb193cee");
//        image = path;
//        avatarREG.setIm(path);

//        avatarREG.setImageBitmap(yourImageNotFoundBitmap);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog();

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            ProgressDialog pd;
            boolean passwordConfirmed = false;
            boolean checkBoxConfirm = false;

            @Override
            public void onClick(final View view) {
                Context context;
                pd = new ProgressDialog(getContext());
                pd.setTitle("Authenticating");
                pd.show();

                final String userName = _txtfname.getText().toString();
                final String email = _txtemail.getText().toString();
                final String password = _txtpass.getText().toString();
                final String confirmPass = confirm.getText().toString();
//                final String image = avatarReg.g

                passwordConfirmation(password, confirmPass);

                checkCheckBox();

                //   progressBar.setVisibility(View.VISIBLE);
                if (passwordConfirmed && checkBoxConfirm) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)      // code used to create user
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    //progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                       user = FirebaseAuth.getInstance().getCurrentUser();
                                        String email = user.getEmail();
                                         uid = user.getUid();
                                        Log.d("Uri Sent", String.valueOf(image));


//                                        String uri =


                                        HashMap<Object, String> hashMap = new HashMap<>();

                                        hashMap.put("email", email);
                                        hashMap.put("uid", uid);
                                        hashMap.put("name", userName);
                                        hashMap.put("phone", "");


                                        // firebase datatabase instance
                                        FirebaseFirestore database = FirebaseFirestore.getInstance();

                                        database.collection("Users").document(uid)
                                          .set(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error writing document", e);
                                                    }
                                                });
                                        Log.w("ImageBoolean", String.valueOf(imageSelected));

                                                if(!imageSelected){

                                                    storageReference.child("Users_Profile_Cover_Imgs/Linkedin.jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                                        @Override

                                                        public void onSuccess(Uri uri) {
                                                            image = uri;
                                                            downloadUri = image.toString();
                                                            HashMap<String, Object> results = new HashMap<>();
                                                            results.put("image", downloadUri);


                                                            FirebaseFirestore database = FirebaseFirestore.getInstance();

                                                            database.collection("Users").document(uid)
                                                                    .set(results, SetOptions.merge())
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                                                            pd.dismiss();
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {

                                                                            Log.w(TAG, "Error writing document", e);
                                                                            pd.dismiss();
                                                                        }
                                                                    });
//                                                            sendPicToDatabase();

                                                            Log.d("Dummy Sent", String.valueOf(uri));
                                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                                    .setDisplayName(userName)
                                                                    .setPhotoUri(uri)
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
                                                            Log.d("Read", "Success");

                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception exception) {
                                                            Log.d("`Dummy", "Fail");

                                                        }
                                                    });












                                                }else {// Used to make FirebaseProfile for user
                                                    sendPicToDatabase();
                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(userName)
                                                            .setPhotoUri(Uri.parse(String.valueOf(image)))
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


                                                }



                                        Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                        _txtemail.setText("");
                                        _txtpass.setText("");
                                        Bundle bundle = new Bundle();
                                        bundle.putString("image", String.valueOf(image));

                                        pd.dismiss();
                                        Navigation.findNavController(view).navigate(R.id.action_frag_Register_to_frag_IniTeamSelection2,bundle);


                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(getActivity(), task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }


                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthWeakPasswordException) {
                                notifyUser(((FirebaseAuthWeakPasswordException) e).getReason());
                            } else if (e instanceof FirebaseAuthUserCollisionException) {

                                String errorCode =
                                        ((FirebaseAuthUserCollisionException) e).getErrorCode();
                                if (errorCode.equals("ERROR_EMAIL_ALREADY_IN_USE")) {
                                    notifyUser("Email already in use");
                                }else {
                                    notifyUser(e.getLocalizedMessage());
                                }}
                        }

                        private void notifyUser(String toast) {
                            Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();

                        }
                    });


                } else {

                }

            }

            private void checkCheckBox() {
                if (checkBox.isChecked()) {
                    checkBoxConfirm = true;
                } else {
                    checkBoxConfirm = false;
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Please read terms and conditions and tick", Toast.LENGTH_SHORT).show();
                }

            }

            private void passwordConfirmation(String password, String confirmPass) {
                if (password.equals(confirmPass)) {
                    passwordConfirmed = true;
                } else {
                    passwordConfirmed = false;
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Password does not match re-enter", Toast.LENGTH_SHORT).show();
                }
            }

        });





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

            String options [] = {"Edit Profile Picture"};
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

                    }


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
                    showImageInView(image_uri);

                }
                if (requestCode == IMAGE_PICK_CAMERA_CODE){
                    //image is picked from camera, get uri of image
                    showImageInView(image_uri);


                }
            }



            super.onActivityResult(requestCode, resultCode, data);
        }

    public void showImageInView(Uri uri) {


            image = uri;
        avatarREG.setImageURI(image);
        imageSelected = true;




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

    public void sendPicToDatabase(){
        {


            //path and name of image to be stored in firebase storage

            String filePathAndName = storagePath+ ""+ profileOrCoverPhoto +"_"+ user.getUid();


            StorageReference storageReference2nd = storageReference.child(filePathAndName);
            storageReference2nd.putFile(Uri.parse(String.valueOf(image)))
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


                                FirebaseFirestore database = FirebaseFirestore.getInstance();

                                database.collection("Users").document(uid)
                                        .set(results, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                                pd.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Log.w(TAG, "Error writing document", e);
                                                pd.dismiss();
                                            }
                                        });


//                                databaseReference.child(user.getUid()).updateChildren(results)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                //url in database of user is added successfully
//                                                //dismiss progress bar
//                                                pd.dismiss();
//                                                Toast.makeText(getActivity(), "Image Updated. . .", Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                //url in database of user is added successfully
//                                                //dismiss progress bar
//                                                pd.dismiss();
//                                                Toast.makeText(getActivity(), "Error Updating Image ...", Toast.LENGTH_SHORT).show();
//
//
//                                            }
//                                        });

                            }
                            else {
                                //error

                                Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
                            }


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


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