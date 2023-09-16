package edu.uw.tcss450.chatphile.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChatSelectedMemberCardBinding;
import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * RecyclerViewAdapter for Selected Contacts for Chat room creation
 *  @author Devin
 *  @author Edwin
 *  @author Yihan
 *  @version June 4 2023
 */
public class SelectedContactsRecyclerViewAdapter extends RecyclerView.Adapter<SelectedContactsRecyclerViewAdapter.SelectedContactsViewHolder> {
    /*
    Instance fields
     */
    private final List<Contact> mContacts;
    private ViewModelStoreOwner mActivity;
    private CreateChatContactsRecyclerViewAdapter mAdapter;

    /*
    Constructor
     */
    public SelectedContactsRecyclerViewAdapter (List<Contact> items, ViewModelStoreOwner owner, CreateChatContactsRecyclerViewAdapter adapter) {
        this.mContacts = items;
        this.mActivity = owner;
        this.mAdapter = adapter;
    }

    @NonNull
    @Override
    public SelectedContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectedContactsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_selected_member_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedContactsViewHolder holder, int position) {
        holder.setContact(mContacts.get(position));
        holder.pressedDeleted(position);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void addToRoom (Contact contact) {
        mContacts.add(contact);
        notifyItemInserted(mContacts.size() - 1);
    }

    /*
    Inner class to help set dummy data
     */
    public class SelectedContactsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatSelectedMemberCardBinding binding;
        private Contact mContact;

        public SelectedContactsViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentChatSelectedMemberCardBinding.bind(view);
        }

        void setContact(final Contact Contact) {
            mContact = Contact;

            // shows data
            binding.smallContactNickname.setText(mContact.getMyUsername());
        }

        void pressedDeleted(int position) {
            binding.deleteChosenContact.setOnClickListener(view -> {
                Log.d("Button Pressed", "Removed");
                final Contact contact = mContacts.get(position);
                mAdapter.removeFromRoom(contact);
                mContacts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mContacts.size());
            });
        }
    }
}
