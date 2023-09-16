package edu.uw.tcss450.chatphile.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * View model contacts available in create chat room
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class ChatContactsViewModel extends AndroidViewModel {
    /*
        Instance fields
         */
    private MutableLiveData<List<Contact>> mContactList;
    private MutableLiveData<List<Contact>> mSelectedList;

    /*
    Constructor
     */
    public ChatContactsViewModel(@NonNull Application application) {
        super(application);
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        mSelectedList = new MutableLiveData<>();
        mSelectedList.setValue(new ArrayList<>());
    }

    public void addContactObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<Contact>> observer) {
        mContactList.observe(owner, observer);
    }

    public void addSelectedObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<Contact>> observer) {
        mSelectedList.observe(owner, observer);
    }

    public MutableLiveData<List<Contact>> getmContactList() {
        return mContactList;
    }

    public void setContactList(MutableLiveData<List<Contact>> mContactList) {
        this.mContactList = mContactList;
    }

    public MutableLiveData<List<Contact>> getmSelectedList() {
        return mSelectedList;
    }

    public void setSelectedList(MutableLiveData<List<Contact>> mSelectedList) {
        this.mSelectedList = mSelectedList;
    }
}
