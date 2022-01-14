package com.example.vsos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookAuthActivity extends Login {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException e) {
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void handleFacebookAccessToken(AccessToken token) {

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse response) {
                // Application code

                try {
                    Log.d("GraphResponse", response.toString());

                    String email = response.getJSONObject().getString("email");
                    String firstName = response.getJSONObject().getString("first_name");
                    String lastName = response.getJSONObject().getString("last_name");

                    register(token, email, firstName + lastName);

                } catch (JSONException e) {
                    Log.d("GraphResponse", e.getMessage().toString());
                    e.printStackTrace();
                }
            }

            private void register(@NonNull AccessToken token, String email, String name) {
                AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(FacebookAuthActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = task.getResult().getUser();
                                    if (user == null) {
                                        deleteCredential(credential);
                                        return;
                                    }

                                    UserClass userMap = new UserClass(email, "Facebook User", name, "default");

                                    String userId = user.getUid();

                                    FirebaseDatabase.getInstance().getReference().child("Users")
                                            .child(userId)
                                            .setValue(userMap)
                                            .addOnCompleteListener(task1 -> {
                                                if (!task1.isSuccessful()) {
                                                    return;
                                                }
                                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                                updateUI();
                                            }).addOnFailureListener(FacebookAuthActivity.this, e -> deleteCredential(credential));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(FacebookAuthActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,name,first_name,last_name,middle_name");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void updateUI() {
        Intent intent = new Intent(FacebookAuthActivity.this, Homepage.class);
        startActivity(intent);
    }

    //    Delete User Credential
    private void deleteCredential(AuthCredential credential) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Unable to register", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseUser.delete().addOnCompleteListener(task1 -> {
                    if (!task1.isSuccessful()) {
                        return;
                    }
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Unable to register", Toast.LENGTH_SHORT).show();
                });
            });
        }
    }
}