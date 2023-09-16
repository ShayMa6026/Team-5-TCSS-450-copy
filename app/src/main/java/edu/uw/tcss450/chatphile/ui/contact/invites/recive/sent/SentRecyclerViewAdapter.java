package edu.uw.tcss450.chatphile.ui.contact.invites.recive.sent;

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
import edu.uw.tcss450.chatphile.databinding.FragmentSentCardBinding;
import edu.uw.tcss450.chatphile.model.UserInfoViewModel;
import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 * RecyclerViewAdapter Class for Contact objects
 */
public class SentRecyclerViewAdapter extends RecyclerView.Adapter<SentRecyclerViewAdapter.SentViewHolder>{
    /*
    Instance fields
     */
    private final List<Contact> mySents;
    private UserInfoViewModel mUserModel;
    private SentViewModel mSentModel;
    private ViewModelStoreOwner mActivity;

    /*
    Constructor
     */
    public SentRecyclerViewAdapter (List<Contact> items, ViewModelStoreOwner owner) {
        this.mySents = items;
        this.mActivity = owner;
    }

    @NonNull
    @Override
    public SentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mUserModel = new ViewModelProvider(mActivity).get(UserInfoViewModel.class);
        mSentModel = new ViewModelProvider(mActivity).get(SentViewModel.class);
        return new SentViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.fragment_sent_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SentViewHolder holder, int position) {
        holder.setSent(mySents.get(position));
        holder.pressedCancel(position);
    }

    @Override
    public int getItemCount() {
        return mySents.size();
    }

    /*
    Inner class to fill in dummy data
     */
    public class SentViewHolder extends RecyclerView.ViewHolder {
        public final View myView;
        public FragmentSentCardBinding binding;
        private Contact mySent;

        public SentViewHolder (View view) {
            super(view);
            myView = view;
            binding = FragmentSentCardBinding.bind(view);
        }

        void setSent (final Contact sent) {
            mySent = sent;

            // shows data
            binding.sentName.setText(mySent.getMyUsername());
            binding.sentEmail.setText(mySent.getMyEmail());
        }

        void pressedCancel(int position) {
            binding.buttonCancel.setOnClickListener(view -> {
                Log.d("Button Pressed", "Decline");
                mySents.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mySents.size());
                mSentModel.deleteContact(mySent.getMyMemberId(), mUserModel.getmJwt());
            });
        }
    }
}
