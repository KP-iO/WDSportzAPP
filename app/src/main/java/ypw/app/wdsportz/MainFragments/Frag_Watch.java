package ypw.app.wdsportz.MainFragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.example.wdsportz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ypw.app.wdsportz.Adapters.LiveStreamAdapter;
import ypw.app.wdsportz.Adapters.WatchViewAdapter;
import ypw.app.wdsportz.SpacesItemDecoration;
import ypw.app.wdsportz.ViewModels.CategoriesModel;
import ypw.app.wdsportz.ViewModels.WatchViewModel;


public class Frag_Watch extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Frag_Watch fragwatch;
    private static final String TAG = "Video Activity";
    FirebaseFirestore fireStoreDB = FirebaseFirestore.getInstance();
    ChipGroup chipGroupSort;
    private RecyclerView bottomRecyclerView;
    private RecyclerView topRecyclerView;
    List<WatchViewModel> mainVideoList;

    Context context;

    Menu menu;

    private WatchViewAdapter watchViewAdapter;
    private LiveStreamAdapter liveStreamAdapter;

    BillingClient billingClient;
    SkuDetails skuDetails;


    public Frag_Watch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////////////////////////////////////PAYMENT
        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                        && purchases != null) {
                } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    // Handle an error caused by a user cancelling the purchase flow.
                } else {
                    // Handle any other error codes.
                }

            }
        };

        billingClient = billingClient.newBuilder(getContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                Log.e("billing setup", String.valueOf(billingResult.getResponseCode()));
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.e("billing setup", "Billing sETUP: ok --> Grab profuycts ");
                    //grabProducts();

                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.e("billing setup", "Disconneted");
            }
        });

    }

    private void grabProducts() {

        List<String> skuList = new ArrayList<>();
        skuList.add("test_video_product_1");

        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, List<SkuDetails> list) {
                        Log.e("BILLING Result", String.valueOf(billingResult.getResponseCode()));
                        Log.e("Sku Details", String.valueOf(list.get(0)));
                        skuDetails = list.get(0);

                        Log.e("Sku Details: Price check", skuDetails.getPrice());

                    }
                });

        Log.e("billingClient", String.valueOf(billingClient.isReady()));
    }

    public void startPurchasing() {
        Log.e("StartPurchasing", "hehe");
        //Check if skudetails null?

        // An activity reference from which the billing flow will be launched.
        Activity activity = this.getActivity();
        Log.e("StartPurchasing2", activity.toString());
        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();

        String responseCode = billingClient.launchBillingFlow(activity, billingFlowParams).getDebugMessage();

        Log.e("launchBillingFlow CODE:", responseCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_watch, container, false);
    }


    @Override
    public void setSharedElementReturnTransition(@Nullable Object transition) {
    }

    //This initializes homepage filter menu in the activity's toolbar
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        //Filter initizaled here!
        inflater.inflate(R.menu.homepage_filter, menu);

        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.filter_btn:
                Log.d("ORE", "LOOOOOK HERE1");
//                DialogFragment DialogFragment_WatchSort = new DialogFragment_WatchSort ();
//                DialogFragment_WatchSort.show(getParentFragmentManager(), "createDialog");

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.context = getContext();

        Button btnPaymentTest = view.findViewById(R.id.btnPayment);
        btnPaymentTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("test", "btnPaymentTest");
                startPurchasing();


                }
            });

        MotionLayout motionLayout = view.findViewById(R.id.Watch_MotionLayout);

        chipGroupSort = getView().findViewById(R.id.chipGroupSort);
        chipGroupSort.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if(chip != null) {
                    //show selection
                    List<WatchViewModel> sortedVideoList = new ArrayList<>();

                    for (int x = 0; x < mainVideoList.size(); x++) {

                        String str = mainVideoList.get(x).getCategory();

                        if (str.startsWith(chip.getText().toString())) {
                            sortedVideoList.add(mainVideoList.get(x));
                        }
                    }

                        if (sortedVideoList.size() == 0) {

                                Toast.makeText(context, "No Videos Available...Please Select Another Category", Toast.LENGTH_SHORT).show();

                            } else {
                                watchViewAdapter = new WatchViewAdapter(context, sortedVideoList);
                                bottomRecyclerView.setAdapter(watchViewAdapter);
                                motionLayout.transitionToEnd();
                            }

                } else {
                    //Else show normal
                    BottomRecycler(getContext());
                    motionLayout.transitionToStart();
                }
            }
        });

        PopulateChipGroupSort(context);

        topRecyclerView = getView().findViewById(R.id.RecyclerViewVM);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        bottomRecyclerView = getView().findViewById(R.id.RecyclerViewV);
        bottomRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.default_gap);
        bottomRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

    // Implement error handling for all cases e.g (Name/ Logo not accessible) ------>
        BottomRecycler(context);
        TopRecycler(context);
    }

    private void PopulateChipGroupSort(Context context) {

        Task<QuerySnapshot> docRef = fireStoreDB.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            List<CategoriesModel> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());
                                list.add(new CategoriesModel(document.get("Name").toString(),document.getId()));

                                Chip chip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_choice, chipGroupSort, false);
                                chip.setText(list.get(i).getTitle());
                                chipGroupSort.addView(chip);

                                i = i +1;
                            }


                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });

    }

    private void TopRecycler(final Context context) {
        Task<QuerySnapshot> docRef = fireStoreDB.collection("Live Stream")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<WatchViewModel> list = new ArrayList<>();

////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {

//                                this.title = title;
//                                this.videoimageURL = videoimageURL;
//                                this.videoURL = videoURL;
//                                this.chatBox_ID = chatBox_ID;
//                                this.video_desc = video_desc;
//                                this.live = live;
//                                this.date = date;
//                                this.accessPassword = accessPassword;

                                list.add(new WatchViewModel(
                                        document.get("Match_Name").toString(),
                                        document.get("Match_Image").toString(),
                                        document.get("Match_Video").toString(),
                                        document.get("Chatbox_ID").toString(),
                                        document.get("Video_desc").toString(),
                                        ((Boolean) document.get("Live")),
                                        document.get("Date").toString(),
                                        document.get("Access_Password").toString()));

                                liveStreamAdapter = new LiveStreamAdapter(context, list, getParentFragmentManager());
                                topRecyclerView.setAdapter(liveStreamAdapter);

                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });
    }

    private void BottomRecycler(final Context context) {


        Task<QuerySnapshot> docRef = fireStoreDB.collection("Videos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            mainVideoList = new ArrayList<>();


////// Change FROM download url to stroage url in firestore?

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, "DOCUMENT PRINT :" + document.getData().toString());

                                mainVideoList.add(new WatchViewModel(
                                        document.get("Match_Name").toString(),
                                        document.get("Match_Image").toString(),
                                        document.get("Match_Video").toString(),
                                        document.get("Chatbox_ID").toString(),
                                        document.get("Video_desc").toString(),
                                        String.valueOf(document.get("Category")),
                                        document.get("Video_desc").toString()));

                                watchViewAdapter = new WatchViewAdapter(context, mainVideoList);
                                bottomRecyclerView.setAdapter(watchViewAdapter);
                            }


                        } else {
                            Log.d(TAG, "No such document");
                        }

                    }

                });


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
