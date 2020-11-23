package com.example.awsauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

public class SignInActivity extends AppCompatActivity {

    private  static final String TAG= "Cognito";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_in );

        final EditText inputUsername = findViewById( R.id.etUsername );
        final  EditText inputPassword = findViewById( R.id.etPassword1 );

        final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                Intent intent = new Intent(SignInActivity.this,HomeActivity.class);
                startActivity( intent );


            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {

                AuthenticationDetails authenticationDetails = new AuthenticationDetails( userId,String.valueOf( inputPassword.getText() ),null );
                //user sign in credentials
                authenticationContinuation.setAuthenticationDetails( authenticationDetails );

                //allow the sigin to continue
                authenticationContinuation.continueTask();


            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(TAG,"error"+exception.getLocalizedMessage());

            }
        };
        Button login = findViewById( R.id.btnLogin );

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CognitoSettings cognitoSettings = new CognitoSettings( SignInActivity.this );

                CognitoUser cognitoUser = cognitoSettings.getUserPool()
                        .getUser(String.valueOf( inputUsername.getText() ));

                cognitoUser.getSessionInBackground( authenticationHandler );
            }
        } );
    }
}