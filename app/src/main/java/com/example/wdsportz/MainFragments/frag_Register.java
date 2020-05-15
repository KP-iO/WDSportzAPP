package com.example.wdsportz.MainFragments;

/**
 * Created by khrishawn
 */

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_Register.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_Register extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
    //uri of picked image
    Uri image_uri;
    Uri image;
    //for checking profile or cover photo
    String profileOrCoverPhoto;
    ProgressDialog pd;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ImageView avatarREG;

    //storage
    StorageReference storageReference;
    //path where images of user profile will be stored
    String storagePath = "Users_Profile_Cover_Imgs/";

    public frag_Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag_Register.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_Register newInstance(String param1, String param2) {
        frag_Register fragment = new frag_Register();
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


        //progress dialog
        pd = new ProgressDialog(getActivity());

        databaseReference = firebaseDatabase.getReference("Users");
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storageReference = FirebaseStorage.getInstance().getReference();

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
                                        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                        String email = user1.getEmail();
                                        String uid = user1.getUid();
//                                        String uri =


                                        HashMap<Object, String> hashMap = new HashMap<>();

                                        hashMap.put("email", email);
                                        hashMap.put("uid", uid);
                                        hashMap.put("name", userName);
                                        hashMap.put("phone", "");
                                        hashMap.put("image", image.toString());

                                        // firebase datatabase instance
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                                        //path to store data named "Users"
                                        DatabaseReference reference = database.getReference("Users");

                                        //put data within hashmap in database
                                        reference.child(uid).setValue(hashMap);

                                        // Used to make FirebaseProfile for user
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName)
                                                .setPhotoUri(Uri.parse(image.toString()))
                                                .build();

                                        user1.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User profile updated.");
                                                        }
                                                    }
                                                });


                                        Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();
                                        _txtemail.setText("");
                                        _txtpass.setText("");
                                        Navigation.findNavController(view).navigate(R.id.action_frag_Register_to_frag_IniTeamSelection2);
                                    } else {
                                        Toast.makeText(getActivity(), task.getException().getMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {

                }
                pd.show();
            }

            private void checkCheckBox() {
                if (checkBox.isChecked()) {
                    checkBoxConfirm = true;
                } else {
                    checkBoxConfirm = false;
                    Toast.makeText(getActivity(), "Please read terms and conditions and tick", Toast.LENGTH_SHORT).show();
                }

            }

            private void passwordConfirmation(String password, String confirmPass) {
                if (password.equals(confirmPass)) {
                    passwordConfirmed = true;
                } else {
                    passwordConfirmed = false;
                    Toast.makeText(getActivity(), "Password does not match re-enter", Toast.LENGTH_SHORT).show();
                }
            }

        });


//        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds: dataSnapshot.getChildren()){
//                    //get data
//
//
//                    String image = "" + ds.child("image").getValue();
//
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
//
//                    try {
//
//                        Glide.with(view)
//                                .load(image)
//                                .into(avatarREG);
//
//                    }
//                    catch (Exception e){
//                        Glide.with(view)
//                                .load(R.drawable.ic_add_image)
//                                .into(avatarREG);
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