package edu.uw.tcss450.chatphile.ui.contact.add;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentAddFriendBinding;

/**
 * Class for fragment_add_friend
 *
 * @author Devin
 * @author Edwin
 * @author Yihan
 * @version June 4 2023
 */
public class AddFriendFragment extends Fragment {

    private AddFriendViewModel mModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = new ViewModelProvider(getActivity()).get(AddFriendViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        FragmentAddFriendBinding binding = FragmentAddFriendBinding.bind(getView());

        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());

        binding.addSearch.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(binding.addSearch.getWindowToken(), 0);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        mModel.searchContact(args.getJwt(), s);
                        binding.addList.getAdapter().notifyDataSetChanged();
                        return true;
                    }
                }
        );

        mModel.addContactObserver(getViewLifecycleOwner(), addList -> {
            if (view instanceof ConstraintLayout) {
                binding.addList.setAdapter(
                        new AddFriendRecyclerViewAdapter(mModel.getmSearchList().getValue(), getActivity())
                );
            }
        });

        binding.buttonContactAdd.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    AddFriendFragmentDirections.actionAddFriendFragmentToNavigationContacts()
            );
        });


        binding.buttonInviteAdd.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    AddFriendFragmentDirections.actionAddFriendFragmentToNavigationInvitation()
            );
        });
    }
}