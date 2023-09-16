package edu.uw.tcss450.chatphile.ui.contact.invites.recive;

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
import edu.uw.tcss450.chatphile.databinding.FragmentInvitationBinding;
import edu.uw.tcss450.chatphile.databinding.FragmentInvitationCardBinding;
import edu.uw.tcss450.chatphile.model.UserInfoViewModel;
import edu.uw.tcss450.chatphile.ui.contact.Contact;

/**
 * Class for invite_list recyclerview in fragment_invitation
 *
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class InvitationRecyclerViewAdapter extends RecyclerView.Adapter<InvitationRecyclerViewAdapter.InvitationViewHolder>{

    private final List<Contact> mInvitations;
    private UserInfoViewModel mUserModel;
    private InvitationViewModel mInviteModel;
    private ViewModelStoreOwner mActivity;


    public InvitationRecyclerViewAdapter(List<Contact> InvitationViews, ViewModelStoreOwner owner) {
        this.mInvitations = InvitationViews;
        this.mActivity = owner;
    }

    @NonNull
    @Override
    public InvitationRecyclerViewAdapter.InvitationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mUserModel = new ViewModelProvider(mActivity).get(UserInfoViewModel.class);
        mInviteModel = new ViewModelProvider(mActivity).get(InvitationViewModel.class);
        return new InvitationRecyclerViewAdapter.InvitationViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_invitation_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationRecyclerViewAdapter.InvitationViewHolder holder, int position) {
        holder.setInvitation(mInvitations.get(position));
        holder.pressedDeny(position);
        holder.pressedAccept(position);
    }

    @Override
    public int getItemCount() {
        return mInvitations.size();
    }


    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class InvitationViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentInvitationCardBinding binding;
        private Contact mInvitation;

        public InvitationViewHolder(View view) {
            super(view);
            mView = view;
            binding = FragmentInvitationCardBinding.bind(view);
        }

        void setInvitation(final Contact Contact) {
            mInvitation = Contact;
            // shows data
            binding.inviteName.setText(mInvitation.getMyUsername());
            binding.inviteEmail.setText(mInvitation.getMyEmail());
        }

        void pressedDeny(int position) {
            binding.buttonDeny.setOnClickListener(view -> {
                Log.d("Button Pressed", "Decline");
                mInvitations.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mInvitations.size());
                mInviteModel.deleteContact(mInvitation.getMyMemberId(), mUserModel.getmJwt());
            });
        }

        void pressedAccept(int position) {
            binding.buttonAccept.setOnClickListener(view -> {
                Log.d("Button Pressed", "Decline");
                mInvitations.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mInvitations.size());
                mInviteModel.acceptIncomingContacts(mUserModel.getmJwt(), mInvitation.getMyMemberId());
                Log.d("ACCEPT", "Accept contact success");
            });
        }
    }
}
