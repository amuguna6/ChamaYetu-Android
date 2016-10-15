package com.chamayetu.chamayetu.registeruser;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * ChamaYetu
 * com.chamayetu.chamayetu.registeruser
 * Created by lusinabrian on 15/10/16.
 * Interactor implementation returns the results or just returns the control to presenter implementation by calling listener methods
 */

public interface RegisterInteractor {

    interface OnRegistrationFinishedListener {
        void onNameError();

        void onEmailError();

        void onPhoneError();

        void onPasswordError();

        void onSuccess();

        /**display toast error when user encounters error when executing FirebaseAuth*/
        void onTaskError(String message, int messageType);
    }

    interface OnRegisterNewChamaFinishedListener{
        /**Displays errors when the new chama name is already existing*/
        void onChamaNameError();
    }

    /**Registers a new user taking in the name, password, phone number an a registration listener
     * @param name name of the ner user
     * @param password password of the new user
     * @param retypePassword retyped password by user
     * @param phoneNumber Number of the new user
     * @param listener registration listner*/
    void registerNewUser(Context context, String name, String email, String password, String retypePassword, long phoneNumber, FirebaseAuth mAuth, DatabaseReference mDatabaseReference, OnRegistrationFinishedListener listener);

    /**registers a new chama to the database adding the name and the members
     * @param chamaName  name of the chama
     * @param chamaMembers members of the new chama
     * @param chairPerson the chairperson name, which is the new registering member*/
    void registerNewChama(String chamaName, String chamaMembers, String chairPerson, OnRegisterNewChamaFinishedListener listener);

}