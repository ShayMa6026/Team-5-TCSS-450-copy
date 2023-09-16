package edu.uw.tcss450.chatphile.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.chatphile.MainActivity;
import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChangeNameBinding;
import edu.uw.tcss450.chatphile.databinding.FragmentChangePasswordBinding;

/**
 * A simple {@link Fragment} subclass.
 * @author Yihan
 */
public class ChangeNameFragment extends Fragment {

    public FragmentChangeNameBinding binding;
    public ChangeNameViewModel mChangeNameViewModel;
    private String mUsername;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangeNameViewModel = new ViewModelProvider(getActivity())
                .get(ChangeNameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangeNameBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());
        binding.textChangeNameEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());

        binding.buttonChangeNameConfirm.setOnClickListener(this::attemptChange);
        mChangeNameViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptChange(final View button) {
        mChangeNameViewModel.connect(
                binding.textChangeNameEmail.getText().toString(),
                binding.editUsername.getText().toString()
        );
    }

    private void navigateToHome() {
        Snackbar.make(binding.getRoot(), "Your User Name Successfully Changed", Snackbar.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editUsername.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                if (response.has("success")) {
                    mUsername = binding.editUsername.getText().toString();
                    if (getActivity() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.setUsername(mUsername);
                    }
                }
                navigateToHome();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}