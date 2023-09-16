package edu.uw.tcss450.chatphile.ui.profile;

import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdUpperCase;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import edu.uw.tcss450.chatphile.MainActivityArgs;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentChangePasswordBinding;
import edu.uw.tcss450.chatphile.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#} factory method to
 * create an instance of this fragment.
 *
 * @author Yihan
 */
public class ChangePasswordFragment extends Fragment {

    public ChangePasswordViewModel mChangePasswordViewModel;
    public FragmentChangePasswordBinding binding;
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editChangePassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangePasswordViewModel = new ViewModelProvider(getActivity())
                .get(ChangePasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityArgs args = MainActivityArgs.fromBundle(getActivity().getIntent().getExtras());
        binding.textChangeEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());

        binding.buttonChangePasswordConfirm.setOnClickListener(this::attemptChange);
        mChangePasswordViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptChange(final View button) {
        validatePasswordsMatch();
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.editChangePassword2.getText().toString().trim()));

        mPassWordValidator.processResult(
                matchValidator.apply(binding.editChangePassword1.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editChangePassword1.setError("Passwords must match."));
    }

    private void validatePassword() {
        Optional<PasswordValidator.ValidationResult> resultMsg = mPassWordValidator.apply(binding.editChangePassword1.getText().toString());
        String errorMessage = "Please enter a valid password.";

        if (resultMsg.isPresent()) {
            if (resultMsg.get() == PasswordValidator.ValidationResult.PWD_INVALID_LENGTH) {
                errorMessage = "Password must be at least 8 characters long.";
            } else if (resultMsg.get() == PasswordValidator.ValidationResult.PWD_MISSING_SPECIAL) {
                errorMessage = "Password is missing at least one of these special characters: @#$%&*!?";
            } else if (resultMsg.get() == PasswordValidator.ValidationResult.PWD_INCLUDES_WHITESPACE) {
                errorMessage = "Password must not include whitespace.";
            } else if (resultMsg.get() == PasswordValidator.ValidationResult.PWD_MISSING_DIGIT) {
                errorMessage = "Password must contain a digit.";
            } else if (resultMsg.get() == PasswordValidator.ValidationResult.PWD_MISSING_LOWER ||
                    resultMsg.get() == PasswordValidator.ValidationResult.PWD_MISSING_UPPER) {
                errorMessage = "Password must contain an alphabetic character.";
            }
        }

        String finalErrorMessage = errorMessage;
        mPassWordValidator.processResult(
                resultMsg,
                this::verifyAuthWithServer,
                result -> binding.editChangePassword1.setError(finalErrorMessage));
    }

    private void verifyAuthWithServer() {
        mChangePasswordViewModel.connect(
                binding.textChangeEmail.getText().toString(),
                binding.editOldPassword.getText().toString(),
                binding.editChangePassword1.getText().toString());
    }

    private void navigateToHome() {
        Snackbar.make(binding.getRoot(), "Your User Name Successfully Changed", Snackbar.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editChangePassword1.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToHome();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}