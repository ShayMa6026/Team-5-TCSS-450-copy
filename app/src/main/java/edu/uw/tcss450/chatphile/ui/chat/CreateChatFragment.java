package edu.uw.tcss450.chatphile.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;

import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChatCreateBinding;
import edu.uw.tcss450.chatphile.databinding.FragmentContactBinding;
import edu.uw.tcss450.chatphile.ui.contact.Contact;
import edu.uw.tcss450.chatphile.ui.contact.current.ContactFragmentDirections;
import edu.uw.tcss450.chatphile.ui.contact.current.ContactRecyclerViewAdapter;
import edu.uw.tcss450.chatphile.ui.contact.current.ContactViewModel;

/**
 * Class for fragment_chat_create
 *
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class CreateChatFragment extends Fragment {
    /*
        Instance Fields
         */
    private ContactViewModel mContactModel;
    private ChatContactsViewModel mChatContactsModel;
    private SelectedContactsViewModel mSelectedModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mContactModel = provider.get(ContactViewModel.class);
        mChatContactsModel = provider.get(ChatContactsViewModel.class);
        mSelectedModel = provider.get(SelectedContactsViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentChatCreateBinding binding = FragmentChatCreateBinding.bind(view);
        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());

        mContactModel.getContacts(args.getJwt());

        mChatContactsModel.setContactList(mContactModel.getmContactList());

        mContactModel.addContactObserver(getViewLifecycleOwner(), contacts -> {
            if (view instanceof ConstraintLayout) {
                binding.listContactsInChatRoom.setAdapter(
                        new CreateChatContactsRecyclerViewAdapter(mChatContactsModel.getmContactList().getValue(), getActivity(), (SelectedContactsRecyclerViewAdapter) binding.selectedMember.getAdapter())
                );
            }
        });

        mSelectedModel.addContactObserver(getViewLifecycleOwner(), contacts -> {
            if (view instanceof ConstraintLayout) {
                binding.selectedMember.setAdapter(
                        new SelectedContactsRecyclerViewAdapter(mSelectedModel.getmSelectedList().getValue(), getActivity(), (CreateChatContactsRecyclerViewAdapter) binding.listContactsInChatRoom.getAdapter())
                );
            }
        });
    }
}