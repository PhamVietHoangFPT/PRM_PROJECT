package com.datj.mobile.data.remote;

import android.app.Application;
import android.content.Context;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.config.UIConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;


public class MyApplication extends Application {
    private static MyApplication instance;

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PayPalCheckout.setConfig(
                new CheckoutConfig(
                        this,
                        "AeHWum6h8vCgUPqKYzPvZJ6LC5zhor3O4Lf63Rg3k8SLquVh09vYQRvdGOLcov6iZbZFyXmB9W21jz19",
                        Environment.SANDBOX,
                        CurrencyCode.USD,
                        UserAction.PAY_NOW,
                        PaymentButtonIntent.CAPTURE,
                        new SettingsConfig(true, false),
                        new UIConfig(true),
                        "com.datj.mobile://paypalpay"
                )
        );
        instance = this;
    }
}
