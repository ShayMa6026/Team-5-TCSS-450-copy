package edu.uw.tcss450.chatphile.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChatListCardBinding;
import edu.uw.tcss450.chatphile.ui.chat.room.ChatViewModel;

/**
 * RecyclerViewAdapter for chat list
 * @author Edwin
 */
public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatViewHolder> {
    private final List<ChatPreview> mChats;
    private ViewModelStoreOwner mActivity;

    public ChatListRecyclerViewAdapter(List<ChatPreview> chatViews, ViewModelStoreOwner activity) {
        this.mChats = chatViews;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_chat_list_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setChatPreview(mChats.get(position));
        holder.mbinding.buttonChatRoom.setOnClickListener(button -> {
            ChatViewModel vm = new ViewModelProvider(mActivity).get(ChatViewModel.class);
            vm.mChatId = mChats.get(position).getRoomId();
            Navigation.findNavController(holder.itemView).navigate(
                    ChatListFragmentDirections.actionNavigationChatsToChatRoomFragment(mChats.get(position).getRoomId())
            );
        });
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }


    /**
     * Objects from this class represent an individual row View from the List
     * of rows in the Chat Recycler View.
     */
    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public FragmentChatListCardBinding mbinding;
        private ChatPreview mChat;

        public ChatViewHolder(View view) {
            super(view);
            mView = view;
            mbinding = FragmentChatListCardBinding.bind(view);
        }

        void setChatPreview(final ChatPreview chatPreview) {
            mChat = chatPreview;

            // shows dummy data
            mbinding.txtUserName.setText(chatPreview.getContact());
            mbinding.messageTime.setText(chatPreview.getTimeOfMsg());
            mbinding.txtUserMessage.setText(chatPreview.getPreviewMsg());
        }
    }
}