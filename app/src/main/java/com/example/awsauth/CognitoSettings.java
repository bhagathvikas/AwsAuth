package com.example.awsauth;


import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    private String poolID = "ap-south-1_neHc2EQrG";
    private String clientID = "33arqem3a6e67meev6luq2n9ee";
    private String clientSecret = "mk1mflgsplke9ct16um24cbe2g7nbo5necs5k513ujj27lq2c48";
    private String  identityPoolId = "ap-south-1:43696364-4893-4f27-ab01-d3bf5085794b";
    private Regions awsRegion = Regions.AP_SOUTH_1;         // Place your Region

    private Context context;

    public CognitoSettings(Context context){
        this.context = context;

    }

    public String getPoolID() {
        return poolID;
    }

    public void setPoolID(String poolID) {
        this.poolID = poolID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Regions getAwsRegion() {
        return awsRegion;
    }

    public void setAwsRegion(Regions awsRegion) {
        this.awsRegion = awsRegion;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    public CognitoUserPool getUserPool(){
        return  new CognitoUserPool( context,poolID,clientID,clientSecret,awsRegion );
    }
    public CognitoCachingCredentialsProvider getCredentialProvider(){
        return new CognitoCachingCredentialsProvider( context.getApplicationContext()
                ,identityPoolId,
                awsRegion
            );
    }
}