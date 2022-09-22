package com.smart.sample.app.util;

import android.hardware.fingerprint.FingerprintManager;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

public class FingerprintHelper extends FingerprintManagerCompat.AuthenticationCallback {

    private FingerprintHelperListener listener;

    public FingerprintHelper(FingerprintHelperListener listener) {
        this.listener = listener;
    }

    //interface for the listner
    public interface FingerprintHelperListener {
        void authenticationFailed(String error);
        void authenticationSuccess(FingerprintManager.AuthenticationResult result);
    }

}