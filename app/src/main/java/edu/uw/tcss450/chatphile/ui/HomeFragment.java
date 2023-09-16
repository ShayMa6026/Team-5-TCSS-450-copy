package edu.uw.tcss450.chatphile.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.chatphile.MainActivity;
import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChangeNameBinding;
import edu.uw.tcss450.chatphile.databinding.FragmentHomeBinding;
import edu.uw.tcss450.chatphile.ui.chat.ChatListGenerator;
import edu.uw.tcss450.chatphile.ui.chat.ChatListRecyclerViewAdapter;
import edu.uw.tcss450.chatphile.ui.chat.ChatListViewModel;
import edu.uw.tcss450.chatphile.ui.profile.ChangeNameViewModel;

/**
 * Class for home page fragment_home
 *
 * @author Yihan
 * @version 4 May 2023
 */
public class HomeFragment extends Fragment {

    private ChatListViewModel mChatListViewModel;
    private FragmentHomeBinding binding;
    private HomeViewModel mHomeViewModel;
    private String mUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChatListViewModel = new ViewModelProvider(getActivity())
                .get(ChatListViewModel.class);
        mHomeViewModel = new ViewModelProvider(getActivity())
                .get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());
        binding.textHomeEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        mHomeViewModel.connect(binding.textHomeEmail.getText().toString());

        mHomeViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);

        mChatListViewModel.addChatListObserver(getViewLifecycleOwner(), chatList -> {
                binding.homeNotification.setAdapter(
                        new ChatListRecyclerViewAdapter(ChatListGenerator.getChatList(), getActivity())
                );
        });
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("success")) {
                try {
                    mUsername = response.getString("name");
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setUsername(mUsername);
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
