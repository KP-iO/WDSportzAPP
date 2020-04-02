package com.example.wdsportz.MainFragments;

/**
 * Created by khrishawn
 */

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    //uri of picked image
    Uri image_uri;
    //for checking profile or cover photo
    String profileOrCoverPhoto;
    ProgressDialog pd;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
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
        final EditText _txtpass =  view.findViewById(R.id.Password);
        final EditText _txtemail =  view.findViewById(R.id.Email);
        ImageButton _btnreg =  view.findViewById(R.id.btnCreateAcc);
        EditText _txtfname =  view.findViewById(R.id.Name);
        ImageButton button = view.findViewById(R.id.btnCreateAcc);
        String username = _txtfname.getText().toString();
        ImageView avatarReg = view.findViewById(R.id.avatarReg);
        CheckBox checkBox = view.findViewById(R.id.ChkBoxTC);

        //progress dialog
        pd = new ProgressDialog(getActivity());

        databaseReference = firebaseDatabase.getReference("Users");
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storageReference = FirebaseStorage.getInstance().getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                final String userName = _txtfname.getText().toString();
                final String email = _txtemail.getText().toString();
                final String password = _txtpass.getText().toString();

                //   progressBar.setVisibility(View.VISIBLE);
                if (checkBox.isChecked()){
                firebaseAuth.createUserWithEmailAndPassword(email,password)      // code used to create user
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    String email = user1.getEmail();
                                    String uid = user1.getUid();


                                    HashMap<Object, String> hashMap = new HashMap<>();

                                    hashMap.put("email", email);
                                    hashMap.put("uid", uid);
                                    hashMap.put("name", userName);
                                    hashMap.put("phone", "");
                                    hashMap.put("image", "");

                                    // firebase datatabase instance
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    //path to store data named "Users"
                                    DatabaseReference reference = database.getReference("Users");

                                    //put data within hashmap in database
                                    reference.child(uid).setValue(hashMap);

                                    // Used to make FirebaseProfile for user
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(userName)
//                                                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
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
                                    Navigation.findNavController(view).navigate(R.id.action_frag_Register_to_blankFragment);
                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else{
                    Toast.makeText(getActivity(), "Please read terms and conditions", Toast.LENGTH_SHORT).show();
                }
                        }

        });


//avatarReg.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        showEditProfileDialog();
//    }
//});
//
//
//    }
//
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    private void showEditProfileDialog() {
//        /* Show dialog containing options
//        1) Edit Profile Picture
//        2) Edit Cover photo
//        3) Edit Name
//        4) Edit Phone *
//
//         */
//
//        String options [] = {"Edit Profile Picture", "Edit Cover photo", "Edit Name", "Edit Phone"};
//        //alert dialog
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // set title
//        builder.setTitle("Choose Action");
//        // set items to dialog
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which){
//                    case 0:
//                        pd.setMessage("Updating Profile Picture");
//                        profileOrCoverPhoto = "image";
//
//                        showImagePicDiolog();
//                        break;
//                }
//
//
//            }
//        });
//        builder.create().show();
//
//    }
//
//
//    private void showImagePicDiolog() {
//        // show dialog containing options camera and Gallery to the image
//
//        String options [] = {"Camera", "Gallery"};
//        //alert dialog
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // set title
//        builder.setTitle("Pick Image From");
//        // set items to dialog
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (which == 0) {
//                    //Camera clicked
//                    if(!checkCameraPermission()){
//                        requestCameraPermission();
//                    }
//                    else {pickFromCamera();
//                    }
//                } else if (which == 1){
//                    //Gallery clicked
//                    if (!checkStoragePermission()){
//                        requestStoragePermission();
//                    }
//                    else {
//                        pickFromGallery();
//                    }
//
//                }
//
//
//
//
//            }
//        });
//        builder.create().show();
//    }
//
//
//
//    private void requestStoragePermission(){
//        //request runtime storage permission
//        requestPermissions(storagePermissions,STORAGE_REQUEST_CODE);
//    }
//
//    private void pickFromCamera() {
//        //Intent of picking image from device camera
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
//        //put image uri
//        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        //intent to start camera
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
//        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
//    }
//
//    private void requestCameraPermission(){
//        //request runtime storage permission
//        requestPermissions( cameraPermissions,CAMERA_REQUEST_CODE);
//    }
//
//    private boolean checkCameraPermission(){
//        //check if storage permission is enabled or not
//        //return true if enabled
//        //return false if not enabled
//        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==(PackageManager.PERMISSION_DENIED);
//
//        boolean result1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_DENIED);
//
//
//
//
//
//
//        return result && result1 ;
//
//    }
//    private boolean checkStoragePermission(){
//        //check
//        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==(PackageManager.PERMISSION_DENIED);
//        return result;
//
//    }
//
//    private void pickFromGallery() {
//        //pick from gallery
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK );
//        galleryIntent.setType("image/*");
//        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        // method will be called after picking image from Camera or Gallery
//        if (resultCode == RESULT_OK ){
//            if (requestCode == IMAGE_PICK_GALLERY_CODE){
//                //image is picked from gallery, get uri of image
//                image_uri = data.getData();
//                uploadProfileCoverPhoto(image_uri);
//
//            }
//            if (requestCode == IMAGE_PICK_CAMERA_CODE){
//                //image is picked from camera, get uri of image
//                uploadProfileCoverPhoto(image_uri);
//
//
//            }
//        }
//
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    private void uploadProfileCoverPhoto(Uri uri) {
//        pd.show();
//        //path and name of image to be stored in firebase storage
//
//        String filePathAndName = storagePath+ ""+ profileOrCoverPhoto +"_"+ user;
//
//        StorageReference storageReference2nd = storageReference.child(filePathAndName);
//        storageReference2nd.putFile(uri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                        while (!uriTask.isSuccessful());
//                        Uri downloadUri = uriTask.getResult();
//
//                        //check if image is uploaded or not and url is received
//                        if ((uriTask.isSuccessful())){
//
//                            HashMap<String, Object> results = new HashMap<>();
//                            results.put(profileOrCoverPhoto, downloadUri.toString());
//
//
//                            databaseReference.child(user.getUid()).updateChildren(results)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void aVoid) {
//                                            //url in database of user is added successfully
//                                            //dismiss progress bar
//                                            pd.dismiss();
//                                            Toast.makeText(getActivity(), "Image Updated. . .", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            //url in database of user is added successfully
//                                            //dismiss progress bar
//                                            pd.dismiss();
//                                            Toast.makeText(getActivity(), "Error Updating Image ...", Toast.LENGTH_SHORT).show();
//
//
//                                        }
//                                    });
//
//                        }
//                        else {
//                            //error
//                            pd.dismiss();
//                            Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        pd.dismiss();
//                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });


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