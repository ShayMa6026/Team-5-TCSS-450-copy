package edu.uw.tcss450.chatphile.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uw.tcss450.chatphile.MainActivity;
import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450.chatphile.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Yihan
 */
public class ProfileFragment extends Fragment {

    public FragmentProfileBinding binding;
    public String mUsername;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mUsername = mainActivity.getUsername();
        }

        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());
        binding.textProfileEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.textProfileName.setText(mUsername);

        binding.buttonChangeName.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToChangeNameFragment()
            );
        });

        binding.buttonChangePicture.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToChangePictureFragment()
            );
        });

        binding.buttonChangePassword.setOnClickListener(button -> {
            Navigation.findNavController(requireView()).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            );
        });
    }
}