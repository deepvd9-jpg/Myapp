package com.tournament.app.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.CFTheme;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.ui.api.CFDropCheckoutPayment;
import com.cashfree.pg.ui.api.CFPaymentComponent;

import java.util.HashMap;
import java.util.Map;

public class CashfreeManager implements CFCheckoutResponseCallback {

    private Context context;
    private PaymentCallback paymentCallback;

    public CashfreeManager(Context context, PaymentCallback paymentCallback) {
        this.context = context;
        this.paymentCallback = paymentCallback;
        try {
            CFPaymentGatewayService.getInstance().set = "PROD"; // Use "TEST" for testing
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startPayment(String orderId, String orderToken, double amount) {
        try {
            CFSession cfSession = new CFSession.Builder()
                    .setEnvironment(CFSession.Environment.PRODUCTION) // or TEST
                    .setOrderId(orderId)
                    .setOrderToken(orderToken)
                    .build();

            CFTheme cfTheme = new CFTheme.Builder()
                    .setNavigationBarBackgroundColor("#42A5F5")
                    .setNavigationBarTextColor("#FFFFFF")
                    .setButtonBackgroundColor("#FFC107")
                    .setButtonTextColor("#FFFFFF")
                    .setPrimaryFont("Montserrat-Regular.ttf")
                    .setSecondaryFont("Montserrat-Light.ttf")
                    .build();

            CFDropCheckoutPayment cfDropCheckoutPayment = new CFDropCheckoutPayment.Builder()
                    .setSession(cfSession)
                    .setCFUIPaymentModes(new CFPaymentComponent.CFUIPaymentModes.Builder().build())
                    .setCFTheme(cfTheme)
                    .build();

            CFPaymentGatewayService.getInstance().doPayment((Activity) context, cfDropCheckoutPayment);

        } catch (CFException e) {
            e.printStackTrace();
            paymentCallback.onPaymentError(e.getMessage());
        }
    }

    @Override
    public void onCFCheckoutResponse(CFCheckoutResponse cfCheckoutResponse) {
        if (cfCheckoutResponse.getStatus().equals("PAID")) {
            paymentCallback.onPaymentSuccess(cfCheckoutResponse.getOrderId(), cfCheckoutResponse.getReferenceId());
        } else if (cfCheckoutResponse.getStatus().equals("FAILED")) {
            paymentCallback.onPaymentFailure(cfCheckoutResponse.getMessage());
        } else {
            paymentCallback.onPaymentError("Payment status: " + cfCheckoutResponse.getStatus());
        }
    }

    @Override
    public void onCFNewCheckoutResponse(CFNewCheckoutResponse cfNewCheckoutResponse) {
        // Handle new checkout response if needed
        if (cfNewCheckoutResponse.getStatus().equals("PAID")) {
            paymentCallback.onPaymentSuccess(cfNewCheckoutResponse.getOrderId(), cfNewCheckoutResponse.getReferenceId());
        } else if (cfNewCheckoutResponse.getStatus().equals("FAILED")) {
            paymentCallback.onPaymentFailure(cfNewCheckoutResponse.getMessage());
        } else {
            paymentCallback.onPaymentError("Payment status: " + cfNewCheckoutResponse.getStatus());
        }
    }

    public interface PaymentCallback {
        void onPaymentSuccess(String orderId, String transactionId);
        void onPaymentFailure(String errorMessage);
        void onPaymentError(String errorMessage);
    }
}
