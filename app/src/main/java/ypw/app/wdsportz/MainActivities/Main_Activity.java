package ypw.app.wdsportz.MainActivities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wdsportz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ypw.app.wdsportz.CheckLiveStreamPassword_DialogFragment;
import ypw.app.wdsportz.MainFragments.DialogFragment_WatchSort;
import ypw.app.wdsportz.MainFragments.Frag_HomePage;
import ypw.app.wdsportz.MainFragments.Frag_SubscribeMore;
import ypw.app.wdsportz.MainFragments.Frag_Test_1;
import ypw.app.wdsportz.MainFragments.Frag_iniTeamSelect;
import ypw.app.wdsportz.MainFragments.Frag_socialWebOpen;
import ypw.app.wdsportz.SideNav.Frag_About;
import ypw.app.wdsportz.SideNav.Frag_Explore;
import ypw.app.wdsportz.SideNav.Frag_LiveGuide;
import ypw.app.wdsportz.SideNav.Frag_Notifications;
import ypw.app.wdsportz.SideNav.Frag_Profile;
import ypw.app.wdsportz.SideNav.Frag_Settings;

public class Main_Activity extends AppCompatActivity implements DialogFragment_WatchSort.OnFragmentInteractionListener,Frag_Test_1.OnFragmentInteractionListener, Frag_iniTeamSelect.OnFragmentInteractionListener, Frag_HomePage.OnFragmentInteractionListener, Frag_Profile.OnFragmentInteractionListener, Frag_Notifications.OnFragmentInteractionListener, Frag_About.OnFragmentInteractionListener, Frag_Explore.OnFragmentInteractionListener, Frag_LiveGuide.OnFragmentInteractionListener, Frag_Settings.OnFragmentInteractionListener, Frag_socialWebOpen.OnFragmentInteractionListener, Frag_SubscribeMore.OnFragmentInteractionListener, CheckLiveStreamPassword_DialogFragment.OnFragmentInteractionListener {
    // Collect all listeners in one interface ^^^ and pass through to main activity?
public boolean connected = false;
    public Toolbar toolbar;
    public BottomNavigationView bottomNav;
    public NavController navController;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;

//    BillingClient billingClient;
//
//    SkuDetails skuDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//////////////////////////////////////PAYMENT
//        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
//            @Override
//            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
//
//            }
//        };
//
//        billingClient = billingClient.newBuilder(this)
//                .setListener(purchasesUpdatedListener)
//                .enablePendingPurchases()
//                .build();
//
//        billingClient.startConnection(new BillingClientStateListener() {
//            @Override
//            public void onBillingSetupFinished(BillingResult billingResult) {
//                Log.e("billing setup", String.valueOf(billingResult.getResponseCode()));
//                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
//                    // The BillingClient is ready. You can query purchases here.
//                    Log.e("billing setup", "Billing sETUP: ok --> Grab profuycts ");
//                    grabProducts();
//
//                }
//            }
//            @Override
//            public void onBillingServiceDisconnected() {
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//                Log.e("billing setup", "Disconneted");
//            }
//        });


        ////// Use the method????
        checkInternet();

                ///^^^^^^^^^>>>>> All of this >>>>>> to go inside 'checkInternet'?
                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                if(cm.getActiveNetwork()== null)
                {
                    Dialog  dialog = new Dialog(Main_Activity.this);
                    dialog.setContentView(R.layout.alert_dialog);

                    Log.d("NTWRK", "Entered");
                    dialog.setCanceledOnTouchOutside(false);

                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.getWindow().getAttributes().windowAnimations = R.style.Animation_Design_BottomSheetDialog;

                    Button tryAgain = dialog.findViewById(R.id.btn_try_again);

                    tryAgain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();

                        }

                    });
                    dialog.show();

                }

                Log.d("NTWRK", "Cleared");


        setupNavigation();
    }

//    private void grabProducts() {
//
//        List<String> skuList = new ArrayList<>();
//        skuList.add("test_video_product_1");
//
//        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
//        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
//        billingClient.querySkuDetailsAsync(params.build(),
//                new SkuDetailsResponseListener() {
//                    @Override
//                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, List<SkuDetails> list) {
//                        Log.e("BILLING Result", String.valueOf(billingResult.getResponseCode()));
//                        Log.e("Sku Details", String.valueOf(list.size()));
//                        Log.e("Sku Details", String.valueOf(list.get(0)));
//                        skuDetails = list.get(0);
//
//                    }
//                });
//
//        Log.e("billingClient", String.valueOf(billingClient.isReady()));
//    }

//    public void startPurchasing() {
//        Log.e("StartPurchasing", "");
//        //Check if skudetails null?
//
//        // An activity reference from which the billing flow will be launched.
//        Activity activity = getParent();
//
//        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
//        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
//                .setSkuDetails(skuDetails)
//                .build();
//
////        billingClient.startConnection(new BillingClientStateListener() {
////
////            @Override
////            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
////                Log.e("purchasingBillingRestart", String.valueOf(billingResult.getResponseCode()));
////            }
////
////            @Override
////            public void onBillingServiceDisconnected() {
////                Log.e("purchasingBillingRestart", "FAILED- DISCONNECTED");
////            }
////        });
//
//        String responseCode = billingClient.launchBillingFlow(activity, billingFlowParams).getDebugMessage();
//
//        Log.e("launchBillingFlow", responseCode);
//    }



    private void checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        ///show the user a message
    }


    public void setupNavigation() {

        databaseReference = firebaseDatabase.getReference("Users");

            toolbar = findViewById(R.id.main_feed_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();

            }
        }

        navController = Navigation.findNavController(this, R.id.NavHostFragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.frag_HomePage,R.id.frag_watch,R.id.frag_scores,R.id.frag_more).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        navController.navigate(R.id.action_global_frag_HomePage);

                        break;

                    case R.id.nav_watch:
                        Log.d("Bottom Nav Test", "nav_watch");
                        navController.navigate(R.id.action_global_frag_watch);

                        break;


                    case R.id.nav_score:
                        navController.navigate(R.id.action_global_scores);
                        Log.d("Bottom Nav Test", "nav_score");

                        break;

                    case R.id.nav_more:
                        navController.navigate(R.id.action_global_more);
                        Log.d("Bottom Nav Test", "nav_more");

                        break;
                }
                return true;
            }
        });

        bottomNav.setSelectedItemId(R.id.nav_home);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    }

