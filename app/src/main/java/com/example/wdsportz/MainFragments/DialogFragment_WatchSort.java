package com.example.wdsportz.MainFragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.wdsportz.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class DialogFragment_WatchSort extends DialogFragment {

    private static String TAG = "WatchSORTTT";
    private OnFragmentInteractionListener mListener;

    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();

    private Toolbar toolbar;

    public static DialogFragment_WatchSort display(FragmentManager fragmentManager) {
        DialogFragment_WatchSort createComment_Topic_DialogFragment = new DialogFragment_WatchSort();
        createComment_Topic_DialogFragment.show(fragmentManager, TAG);
        return createComment_Topic_DialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dialog__watch_sort, container, false);

        toolbar = view.findViewById(R.id.toolbar_CreateDialog);


        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationOnClickListener(v -> dismiss());

        toolbar.inflateMenu(R.menu.toolbar_dialog);

        toolbar.setTitle("Select A Category");

        toolbar.setOnMenuItemClickListener(item -> {

            createComment();

            return true;
        });

    }

    public void createComment(){

        this.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogFragment_WatchSort.OnFragmentInteractionListener) {
            mListener = (DialogFragment_WatchSort.OnFragmentInteractionListener) context;
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }



}
