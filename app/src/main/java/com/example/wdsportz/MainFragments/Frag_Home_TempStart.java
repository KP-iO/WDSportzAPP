package com.example.wdsportz.MainFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wdsportz.R;


/**
 * A simple {@link Fragment} subclass.
 */

public class Frag_Home_TempStart extends Fragment {


    private NavController navController;
    private DrawerLayout drawer;


    public Frag_Home_TempStart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //ActionBar toolbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        //toolbar.hide();

        return inflater.inflate(R.layout.fragment_home_tempstart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

          Button button = view.findViewById(R.id.button);
          button.setOnClickListener(new View.OnClickListener() {
            @Override
              public void onClick (View v) {
               NavController navController = Navigation.findNavController(getActivity(), R.id.NavHostFragment);
             //   navController.navigate(R.id.action_activity_Main_to_frag_HomePage);
            }


        //View v = inflater.inflate(R.layout.fragment_home, container, false);

    });

}
}
