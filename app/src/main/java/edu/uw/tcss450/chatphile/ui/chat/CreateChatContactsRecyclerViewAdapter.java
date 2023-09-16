package edu.uw.tcss450.chatphile.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChatCreateContactCardBinding;
import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * RecyclerViewAdapter for Contacts available for Chat room creation
 *  @author Devin
 *  @author Edwin
 *  @author Yihan
 *  @version June 4 2023
 */
public class CreateChatContactsRecyclerViewAdapter extends RecyclerView.Adapter<CreateChatContactsRecyclerViewAdapter.CreateChatContactViewHolder> {
    /*
    Instance fields
     */
    private List<Contact> mContacts;
    private ViewModelStoreOwner mActivity;
    private SelectedContactsRecyclerViewAdapter mAdapter;

    /*
    Constructor
     */
    public CreateChatContactsRecyclerViewAdapter (List<Contact> items, ViewModelStoreOwner owner, SelectedContactsRecyclerViewAdapter adapter) {
        this.mContacts = items;
        this.mActivity = owner;
        this.mAdapter = adapter;
    }

    @NonNull
    @Override
    public CreateChatContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreateChatContactViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_create_contact_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreateChatContactViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
        holder.pressedAdd(position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void removeFromRoom (Contact contact) {
        mContacts.add(contact);
        notifyItemInserted(mContacts.size());
    }

    /*
    Inner class to help set dummy data
     */
    public class CreateChatContactViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatCreateContactCardBinding binding;
        private Contact mContact;

        public CreateChatContactViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatCreateContactCardBinding.bind(view);
        }

        void setContact(final Contact Contact) {
            mContact = Contact;

            // shows data
            binding.textName.setText(mContact.getMyUsername());
            binding.textEmail.setText(mContact.getMyEmail());
        }

        void pressedAdd(int position) {
            binding.addContactToChatButton.setOnClickListener(view -> {
                Log.d("Button Pressed", "Added");
                final Contact contact = mContacts.get(position);
                mAdapter.addToRoom(contact);
                mContacts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mContacts.size());
            });
        }
    }
}
