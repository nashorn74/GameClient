package com.nashorn.mac.gameclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.Constants;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InAppPurchaseActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private InAppPurchaseAdapter skusAdapter;
    private BillingProcessor bp;
    public static ArrayList<SkuDetails> products;
    public ArrayList<String> arrayListOfProductIds  = new ArrayList<String>();
    //private MaterialDialog purchaseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_purchase);

        arrayListOfProductIds.add("test_product_1");
        arrayListOfProductIds.add("test_product_2");

        bp = new BillingProcessor(this, "API KEY", this);
        bp.initialize();
    }

    public void purchaseProduct(String productId) {
        if (bp.isPurchased(productId)) {
            bp.consumePurchase(productId);
        }
        bp.purchase(this, productId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        SkuDetails sku = bp.getPurchaseListingDetails(productId);
        String purchaseMessage = sku.title + " 구매 성공!";
        Toast.makeText(this, purchaseMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        if (errorCode != Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            String errorMessage = "ERROR (" + errorCode + ")";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBillingInitialized() {
        products = (ArrayList<SkuDetails>) bp.getPurchaseListingDetails(arrayListOfProductIds);
        Collections.sort(products, new Comparator<SkuDetails>() {
            @Override
            public int compare(SkuDetails o1, SkuDetails o2) {
                if (o1.priceLong > o2.priceLong) {
                    return 1;
                } else if (o1.priceLong < o2.priceLong) {
                    return -1;
                } else return 0;
            }
        });

        skusAdapter = new InAppPurchaseAdapter(this);
        ListView lvSkus = findViewById(R.id.lv_skus);
        lvSkus.setAdapter(skusAdapter);
        skusAdapter.update(products);
    }
}
