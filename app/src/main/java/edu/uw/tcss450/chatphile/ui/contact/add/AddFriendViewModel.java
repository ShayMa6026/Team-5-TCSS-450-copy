package edu.uw.tcss450.chatphile.ui.contact.add;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.io.RequestQueueSingleton;
import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * View model for fragment_add_friend
 *
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class AddFriendViewModel extends AndroidViewModel {

    /*
    Instance fields
     */
    private MutableLiveData<List<Contact>> mAddContactList;
    private MutableLiveData<List<Contact>> mSearchList;

    /*
    Constructor
     */
    public AddFriendViewModel(@NonNull Application application) {
        super(application);
        mAddContactList = new MutableLiveData<>();
        mAddContactList.setValue(new ArrayList<>());
        mSearchList = new MutableLiveData<>();
        mSearchList.setValue(new ArrayList<>());
    }

    public void addContactObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<Contact>> observer) {
        mAddContactList.observe(owner, observer);
    }

    public MutableLiveData<List<Contact>> getmAddContactList () {
        return this.mAddContactList;
    }

    public void addToAddList (Contact theContact) {
        mAddContactList.getValue().add(theContact);
        mAddContactList.setValue(mAddContactList.getValue());
    }

    public void resetAddList () {
        mAddContactList.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Contact>> getmSearchList () {
        return this.mSearchList;
    }

    public void addToSearchList (Contact theContact) {
        mSearchList.getValue().add(theContact);
        mSearchList.setValue(mAddContactList.getValue());
    }

    public void resetSearchList () {
        mSearchList.setValue(new ArrayList<>());
    }

    public void searchContact (final String jwt, final String searchTerm) {
        String url = getApplication().getResources().getString(R.string.base_url) + "members/" + searchTerm;

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this::handleGet,
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    public void addContact (final String theFriendEmail, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "contacts";

        JSONObject body = new JSONObject();
        try {
            body.put("email", theFriendEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body, //push token found in the JSONObject body
                this::handleResult, // we get a response but do nothing with it
                this::handleError) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // add headers <key,value>
                headers.put("Authorization", jwt);
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Instantiate the RequestQueue and add the request to the queue
        RequestQueueSingleton.getInstance(getApplication().getApplicationContext())
                .addToRequestQueue(request);
    }

    private void handleGet (JSONObject result) {
        mSearchList.getValue().clear();
        MutableLiveData<List<Contact>> list = mSearchList;
        try {
            JSONObject response = result;

            if (response.has("rows")) {
                JSONArray rows = response.getJSONArray("rows");

                for (int i = 0; i < rows.length(); i++) {
                    Contact contact = Contact.createFromJsonObject(rows.getJSONObject(i));

                    if (!list.getValue().contains(contact)) {
                        Log.d("SEARCH", "Friend Searched");
                        list.getValue().add(contact);
                    }
                }
            }
        } catch (JSONException err) {
            err.printStackTrace();
            Log.e("ERROR", err.getMessage());
        }

        list.setValue(list.getValue());
        mSearchList = list;
    }

    private void handleResult (JSONObject result) {
        JSONObject response = result;
        if (response.has("success")) {
            Log.d("ADDMEMBER", "Added member success");
        }
    }

    private void handleError(final VolleyError error) {
        if (Objects.isNull(error.networkResponse)) {
            Log.e("NETWORK ERROR", error.getMessage());
        }
        else {
            String data = new String(error.networkResponse.data, Charset.defaultCharset());
            Log.e("CLIENT ERROR",
                    error.networkResponse.statusCode +
                            " " +
                            data);
        }
    }
}
