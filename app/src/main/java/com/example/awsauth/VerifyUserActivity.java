package com.example.awsauth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

public class VerifyUserActivity extends AppCompatActivity {
    Button submit;
    private static final String TAG = "Cognito";
    private CognitoUserPool userPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verify_user );

        final EditText verificatoncode =findViewById( R.id.etOTP );
        final EditText Vusername =findViewById( R.id.etVusername );
        submit = findViewById( R.id.btnSubmit );



        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConfirmTask().execute(String.valueOf( verificatoncode.getText() ),String.valueOf( Vusername.getText() ));
            }
        } );


    }

    private class ConfirmTask extends AsyncTask<String, Void,String > {
        @Override
        protected String doInBackground(String... strings) {
            final  String[] result = new String[1];

            final GenericHandler genericHandler = new GenericHandler() {
                @Override
                public void onSuccess() {
                    result[0] = "succeeded";
                    Intent intent = new Intent(VerifyUserActivity.this ,HomeActivity.class);
                    startActivity( intent );

                }

                @Override
                public void onFailure(Exception exception) {

                    result[0] = "failed: "+exception.getMessage();

                }
            };

            CognitoSettings cognitoSettings = new CognitoSettings( VerifyUserActivity.this );
            CognitoUser user = cognitoSettings.getUserPool().getUser(strings[1]);

            user.confirmSignUp( strings[0],false,genericHandler );
            return result[0];

        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute( result );
            Log.i( TAG,"result"+result );
        }

    }
}