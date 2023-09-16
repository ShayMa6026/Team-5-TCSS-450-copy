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
 * View Model for Selected Contacts for Chat room creation
 *  @author Devin
 *  @author Edwin
 *  @author Yihan
 *  @version June 4 2023
 */
public class SelectedContactsViewModel extends AndroidViewModel {

    /*
    Instance fields
     */
    private MutableLiveData<List<Contact>> mSelectedList;

    /*
    Constructor
     */
    public SelectedContactsViewModel(@NonNull Application application) {
        super(application);
        mSelectedList = new MutableLiveData<>();
        mSelectedList.setValue(new ArrayList<>());
    }

    public void addContactObserver(@NonNull LifecycleOwner owner,
                                   @NonNull Observer<? super List<Contact>> observer) {
        mSelectedList.observe(owner, observer);
    }

    public MutableLiveData<List<Contact>> getmSelectedList () {
        return this.mSelectedList;
    }

    public void setSelectedList(MutableLiveData<List<Contact>> mSelectedList) {
        this.mSelectedList = mSelectedList;
    }
}
