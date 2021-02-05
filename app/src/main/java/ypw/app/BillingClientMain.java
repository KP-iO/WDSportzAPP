package ypw.app;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.PriceChangeConfirmationListener;
import com.android.billingclient.api.PriceChangeFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

public class BillingClientMain extends BillingClient {


    BillingClient billingClient;
    Activity activity;

    public BillingClientMain(BillingClient billingClient, Activity activity) {
        this.billingClient = billingClient;
        this.activity  = activity;

    }



    @Override
    public BillingResult isFeatureSupported(@NonNull String s) {
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void startConnection(@NonNull BillingClientStateListener billingClientStateListener) {

    }

    @Override
    public void endConnection() {

    }

    @Override
    public BillingResult launchBillingFlow(@NonNull Activity activity, @NonNull BillingFlowParams billingFlowParams) {
        return null;
    }

    @Override
    public void launchPriceChangeConfirmationFlow(@NonNull Activity activity, @NonNull PriceChangeFlowParams priceChangeFlowParams, @NonNull PriceChangeConfirmationListener priceChangeConfirmationListener) {

    }

    @Override
    public Purchase.PurchasesResult queryPurchases(@NonNull String s) {
        return null;
    }

    @Override
    public void querySkuDetailsAsync(@NonNull SkuDetailsParams skuDetailsParams, @NonNull SkuDetailsResponseListener skuDetailsResponseListener) {

    }

    @Override
    public void consumeAsync(@NonNull ConsumeParams consumeParams, @NonNull ConsumeResponseListener consumeResponseListener) {

    }

    @Override
    public void queryPurchaseHistoryAsync(@NonNull String s, @NonNull PurchaseHistoryResponseListener purchaseHistoryResponseListener) {

    }

    @Override
    public void acknowledgePurchase(@NonNull AcknowledgePurchaseParams acknowledgePurchaseParams, @NonNull AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {

    }
}