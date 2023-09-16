package edu.uw.tcss450.chatphile.ui.contact.current;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentContactBinding;
import edu.uw.tcss450.chatphile.model.UserInfoViewModel;
import edu.uw.tcss450.chatphile.ui.contact.current.ContactFragmentDirections;

/**
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 * Class that handles Contact Fragment
 */
public class ContactFragment extends Fragment {
    /*
    Instance Fields
     */
    private ContactViewModel mContactModel;

    /*
    Empty Constructor
     */
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelProvider provider = new ViewModelProvider(getActivity());
        mContactModel = provider.get(ContactViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Bottom nav", "CONTACTS");

        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentContactBinding binding = FragmentContactBinding.bind(getView());
        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());

        mContactModel.getContacts(args.getJwt());

        mContactModel.addContactObserver(getViewLifecycleOwner(), contacts -> {
            if (view instanceof ConstraintLayout) {
                binding.listRoot.setAdapter(
                        new ContactRecyclerViewAdapter(mContactModel.getmContactList().getValue(), getActivity())
                );
            }
        });

        binding.buttonInviteContact.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    ContactFragmentDirections.actionNavigationContactsToInvitationFragment()
            );
        });

        binding.buttonAddContact.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    ContactFragmentDirections.actionNavigationContactsToAddFriendFragment()
            );
        });
    }
}