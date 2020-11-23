package com.example.awsauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.services.cognitoidentityprovider.model.SignUpResult;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "";
    Button signup;
    TextView signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up );
        signup = findViewById( R.id.btnSignup );

        signin = findViewById( R.id.tvSignInHere );

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i(TAG, userStateDetails.getUserState().toString());
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                    case SIGNED_OUT:
                        Intent i = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(i);
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        Intent intent1 = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent1);
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, e.toString());
            }
        });

        signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity( intent );
            }
        } );

        registerUser();

    }

    private void registerUser() {
        final EditText username = findViewById( R.id.etUserName );
        final EditText password = findViewById( R.id.etPassword );
        final EditText email = findViewById( R.id.etEmail );

        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

       final  SignUpHandler signUpHandler = new SignUpHandler() {
           @Override
           public void onSuccess(CognitoUser user,SignUpResult signUpResult) {

               Log.i( TAG,"sign up sucess" );
               Intent intent = new Intent(SignUpActivity.this,VerifyUserActivity.class);
               intent.putExtra( "username",username.getText().toString() );
               startActivity( intent );




           }

           @Override
           public void onFailure(Exception exception) {
               Log.i( TAG,"failed"+exception.getLocalizedMessage() );

           }
       };

        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userAttributes.addAttribute( "email",String.valueOf( email.getText() ) );


                CognitoSettings cognitoSettings = new CognitoSettings( SignUpActivity.this );

                cognitoSettings.getUserPool().signUpInBackground( String.valueOf( username.getText()), String.valueOf( password.getText()),userAttributes,null,signUpHandler );

            }
        } );

    }
}