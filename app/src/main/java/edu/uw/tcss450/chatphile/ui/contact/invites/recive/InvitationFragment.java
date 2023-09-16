package edu.uw.tcss450.chatphile.ui.contact.invites.recive;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentInvitationBinding;
import edu.uw.tcss450.chatphile.ui.contact.invites.recive.sent.SentRecyclerViewAdapter;
import edu.uw.tcss450.chatphile.ui.contact.invites.recive.sent.SentViewModel;

/**
 * Class for fragment_invitation.
 *
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class InvitationFragment extends Fragment {

    private InvitationViewModel iModel;
    private SentViewModel sModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invitation, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iModel = new ViewModelProvider(getActivity()).get(InvitationViewModel.class);
        sModel = new ViewModelProvider(getActivity()).get(SentViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);



        FragmentInvitationBinding binding = FragmentInvitationBinding.bind(getView());
        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());

        iModel.getIncomingContacts(args.getJwt());

        iModel.addContactObserver(getViewLifecycleOwner(), addList -> {
            if (view instanceof ConstraintLayout) {
                binding.inviteList.setAdapter(
                        new InvitationRecyclerViewAdapter(iModel.getmInviteList().getValue(), getActivity())
                );
            }
        });

        sModel.getSentContacts(args.getJwt());

        sModel.addContactObserver(getViewLifecycleOwner(), sents -> {
            if (view instanceof ConstraintLayout) {
                binding.listRoot.setAdapter(
                        new SentRecyclerViewAdapter(sModel.getmSentList().getValue(), getActivity())
                );
            }
        });

        binding.buttonContactInvite.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    InvitationFragmentDirections.actionInvitationFragmentToNavigationContacts()
            );
        });

        binding.buttonAddInvite.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    InvitationFragmentDirections.actionNavigationInvitationToAddFriendFragment()
            );
        });
    }
}