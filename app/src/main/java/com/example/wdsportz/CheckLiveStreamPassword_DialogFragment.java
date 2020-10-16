package com.example.wdsportz;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class CheckLiveStreamPassword_DialogFragment extends DialogFragment {

    private CheckLiveStreamPassword_DialogFragment.OnFragmentInteractionListener mListener;
    private Listener listener;

    public static final String TAG = "CheckLiveStreamPassword_DialogFragment";

    private Toolbar toolbar;
    public LinearLayout linearLayout;
    private TextView txtPassword;

    public static CheckLiveStreamPassword_DialogFragment display(FragmentManager fragmentManager) {
        CheckLiveStreamPassword_DialogFragment createPostCommunity_DialogFragment = new CheckLiveStreamPassword_DialogFragment();
        createPostCommunity_DialogFragment.show(fragmentManager, TAG);
        return createPostCommunity_DialogFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dialogfragment_live_accesspass, container, false);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolbar = view.findViewById(R.id.toolbar_CreateDialog);
        linearLayout = view.findViewById(R.id.linearLayout);
        txtPassword = view.findViewById(R.id.txtPassword);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setNavigationOnClickListener(v -> dismiss());

        toolbar.inflateMenu(R.menu.toolbar_dialog);

        toolbar.setTitle("Password Protected");

        toolbar.setOnMenuItemClickListener(item -> {

            //remove keyboard
//            InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            if (listener != null) {
                listener.returnData(txtPassword.getText().toString());
            }

            this.dismiss();

            return true;
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CheckLiveStreamPassword_DialogFragment.OnFragmentInteractionListener) {
            mListener = (CheckLiveStreamPassword_DialogFragment.OnFragmentInteractionListener) context;
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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void returnData(String result);
    }

}
