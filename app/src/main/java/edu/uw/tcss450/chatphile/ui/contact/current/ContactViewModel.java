package edu.uw.tcss450.chatphile.ui.contact.current;

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
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 * Class to help handle Contact observers
 */
public class ContactViewModel extends AndroidViewModel {
    /*
    Instance fields
     */
    private MutableLiveData<List<Contact>> mContactList;

    /*
    Constructor
     */
    public ContactViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
    }

    public void addContactObserver(@NonNull LifecycleOwner owner,
                                    @NonNull Observer<? super List<Contact>> observer) {
        mContactList.observe(owner, observer);
    }

    public MutableLiveData<List<Contact>> getmContactList  () {
        return this.mContactList;
    }

    public void getContacts (final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "contacts";

        Request request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null, //push token found in the JSONObject body
                this::handleGet, // we get a response but do nothing with it
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

    private void handleGet (final JSONObject result) {
        mContactList.getValue().clear();
        MutableLiveData<List<Contact>> list = mContactList;

        try {
            JSONObject response = result;

            if (response.has("rows")) {
                JSONArray rows = response.getJSONArray("rows");
                for (int i = 0; i < rows.length(); i++) {
                    Contact contact = Contact.createFromJsonObject(rows.getJSONObject(i));

                    if (!list.getValue().contains(contact)) {
                        list.getValue().add(contact);
                    }
                }
            }
        } catch (JSONException err) {
            err.printStackTrace();
            Log.e("ERROR", err.getMessage());
        }

        list.setValue(list.getValue());
        mContactList = list;
    }

    public void deleteContact (final int theFriendId, final String jwt) {
        String url = getApplication().getResources().getString(R.string.base_url) + "contacts/" + theFriendId;

        Request request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null, //push token found in the JSONObject body
                this::handleDelete, // we get a response but do nothing with it
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

    private void handleDelete (JSONObject result) {
        JSONObject response = result;

        if (response.has("success")) {
            Log.d("DELETE", "Delete Contact Success");
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
