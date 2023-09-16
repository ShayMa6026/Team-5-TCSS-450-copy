package edu.uw.tcss450.chatphile.ui.contact.add;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.MainActivity;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentAddCardBinding;
import edu.uw.tcss450.chatphile.model.UserInfoViewModel;
import edu.uw.tcss450.chatphile.ui.chat.room.ChatViewModel;
import edu.uw.tcss450.chatphile.ui.contact.Contact;
import edu.uw.tcss450.chatphile.ui.contact.current.ContactViewModel;

/**
 * Class for add_list recyclerview in fragment_add_friend
 *
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class AddFriendRecyclerViewAdapter extends RecyclerView.Adapter<AddFriendRecyclerViewAdapter.AddFriendViewHolder> {

    private final List<Contact> mContacts;
    private UserInfoViewModel mUserModel;
    private AddFriendViewModel mAddFriendModel;
    private ViewModelStoreOwner mActivity;

    public AddFriendRecyclerViewAdapter(List<Contact> theContactList, ViewModelStoreOwner owner) {
        this.mContacts = theContactList;
        this.mActivity = owner;
    }

    @NonNull
    @Override
    public AddFriendRecyclerViewAdapter.AddFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mUserModel = new ViewModelProvider(mActivity).get(UserInfoViewModel.class);
        mAddFriendModel = new ViewModelProvider(mActivity).get(AddFriendViewModel.class);
        return new AddFriendRecyclerViewAdapter.AddFriendViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_add_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendRecyclerViewAdapter.AddFriendViewHolder holder, int position) {
        holder.setAddFriend(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }


    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class AddFriendViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentAddCardBinding binding;
        private Contact mContact;

        public AddFriendViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentAddCardBinding.bind(view);
        }

        void setAddFriend(final Contact Contact) {
            mContact = Contact;

            // shows data
            binding.addName.setText(mContact.getMyUsername());
            binding.addEmail.setText(mContact.getMyEmail());

            binding.buttonAdd.setOnClickListener(button -> {
                Log.d("ADD", "Add button clicked");
                mAddFriendModel.addContact(mContact.getMyEmail(), mUserModel.getmJwt());
            });
        }
    }
}
