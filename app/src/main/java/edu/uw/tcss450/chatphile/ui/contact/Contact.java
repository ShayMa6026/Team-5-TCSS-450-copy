package edu.uw.tcss450.chatphile.ui.contact;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 * Base Contact Class
 */

public class Contact implements Serializable {

    /*
    Instance Variables
     */
    private final int mMemberId;
    private final String mFirstName;
    private final String mLastName;
    private final String mUsername;
    private final String mEmail;

    /*
    Constructor
     */
    private Contact (int theMemberId, String theFirstName, String theLastName, String theEmail, String theUsername) {
        this.mMemberId = theMemberId;
        this.mFirstName = theFirstName;
        this.mLastName = theLastName;
        this.mEmail = theEmail;
        this.mUsername = theUsername;
    }

    /**
     * Static factory method to turn a properly formatted JSON String into a
     * Contact object.
     * @param jsonObject a JSONObject to be made into a Contact Object.
     * @return a Contact Object with the details contained in the JSON String.
     * @throws JSONException when cAsString cannot be parsed into a Contact.
     */
    public static Contact createFromJsonObject(final JSONObject jsonObject) throws JSONException {
        int memberid = 0;
        try {
            memberid = jsonObject.getInt("memberid");
        } catch (JSONException err) {
            try {
                memberid = jsonObject.getInt("memberid_b");
            } catch (JSONException err2) {
                memberid = jsonObject.getInt("memberid_a");
            }
        }
        return new Contact(memberid,
                jsonObject.getString("firstname"),
                jsonObject.getString("lastname"),
                jsonObject.getString("email"),
                jsonObject.getString("username"));
    }


    /*
    Contact instance field getters
     */
    public int getMyMemberId () {
        return mMemberId;
    }
    public String getMyFirstName () {
        return mFirstName;
    }
    public String getMyLastName () {
        return mLastName;
    }

    public String getMyEmail () {
        return mEmail;
    }

    public String getMyUsername () {
        return mUsername;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Contact contact = (Contact) obj;
        if (contact.getMyEmail().equals(this.mEmail)) {
            return true;
        }

        return false;
    }
}

