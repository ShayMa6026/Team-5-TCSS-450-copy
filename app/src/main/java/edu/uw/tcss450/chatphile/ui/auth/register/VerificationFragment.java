package edu.uw.tcss450.chatphile.ui.auth.register;

import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPinLength;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.chatphile.databinding.FragmentVerificationBinding;
import edu.uw.tcss450.chatphile.ui.auth.signin.SignInFragmentArgs;
import edu.uw.tcss450.chatphile.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 * @author Yihan
 */
public class VerificationFragment extends Fragment {

    private FragmentVerificationBinding binding;
    private VerificationViewModel mVerificationViewModel;

    private PasswordValidator mVerificationValidator = checkPinLength(6)
            .and(checkExcludeWhiteSpace());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVerificationViewModel = new ViewModelProvider(getActivity()).get(VerificationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVerificationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edu.uw.tcss450.chatphile.ui.auth.signin.SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        binding.verificationEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());
        binding.textPassword.setText(args.getPassword().equals("default") ? "" : args.getPassword());

        mVerificationViewModel.sendVerify(binding.verificationEmail.getText().toString());

        binding.buttonResend.setOnClickListener(button -> {
            mVerificationViewModel.sendVerify(binding.verificationEmail.getText().toString());
        });

        binding.buttonVerify.setOnClickListener(this::attemptVerify);
        mVerificationViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptVerify(final View button) {
        mVerificationValidator.processResult(
                mVerificationValidator.apply(binding.verificationCode.getText().toString().trim()),
                this::codeVerify,
                result -> binding.verificationCode.setError("Please enter a valid verification code."));
    }

    private void codeVerify() {
        mVerificationViewModel.confirmVerify(
                binding.verificationEmail.getText().toString(),
                binding.verificationCode.getText().toString());
    }

    private void navigateToLogin() {
        VerificationFragmentDirections.ActionVerificationFragmentToSignInFragment directions =
                VerificationFragmentDirections.actionVerificationFragmentToSignInFragment();

        directions.setEmail(binding.verificationEmail.getText().toString());
        directions.setPassword(binding.textPassword.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.verificationCode.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                if (response.has("success")) {
                    navigateToLogin();
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}