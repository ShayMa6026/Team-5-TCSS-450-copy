package edu.uw.tcss450.chatphile.ui.contact.current;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentAddCardBinding;
import edu.uw.tcss450.chatphile.databinding.FragmentContactCardBinding;
import edu.uw.tcss450.chatphile.model.UserInfoViewModel;
import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 * RecyclerViewAdapter Class for Contact Fragment
 */
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ContactViewHolder> {
    /*
    Instance fields
     */
    private final List<Contact> mContacts;
    private UserInfoViewModel mUserModel;
    private ContactViewModel mContactModel;
    private ViewModelStoreOwner mActivity;

    /*
    Constructor
     */
    public ContactRecyclerViewAdapter (List<Contact> items, ViewModelStoreOwner owner) {
        this.mContacts = items;
        this.mActivity = owner;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mUserModel = new ViewModelProvider(mActivity).get(UserInfoViewModel.class);
        mContactModel = new ViewModelProvider(mActivity).get(ContactViewModel.class);
        return new ContactViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_contact_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
        holder.pressedCancel(position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    /*
    Inner class to help set dummy data
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentContactCardBinding binding;
        private Contact mContact;

        public ContactViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentContactCardBinding.bind(view);
        }

        void setContact(final Contact Contact) {
            mContact = Contact;

            // shows data
            binding.textName.setText(mContact.getMyUsername());
            binding.textEmail.setText(mContact.getMyEmail());
        }

        void pressedCancel(int position) {
            binding.buttonSettings.setOnClickListener(view -> {
                Log.d("Button Pressed", "Decline");
                mContacts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mContacts.size());
                mContactModel.deleteContact(mContact.getMyMemberId(), mUserModel.getmJwt());
            });
        }
    }

}
