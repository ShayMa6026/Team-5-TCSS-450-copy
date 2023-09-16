package edu.uw.tcss450.chatphile.ui.auth.forget;

import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkClientPredicate;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdDigit;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdLowerCase;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdSpecialChar;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdUpperCase;

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

import java.util.Optional;

import edu.uw.tcss450.chatphile.databinding.FragmentForgetBinding;
import edu.uw.tcss450.chatphile.ui.auth.signin.SignInFragmentArgs;
import edu.uw.tcss450.chatphile.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 * @author Yihan
 */
public class ForgetFragment extends Fragment {

    private FragmentForgetBinding binding;
    private ForgetViewModel mForgetViewModel;
    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editNewPassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForgetViewModel = new ViewModelProvider(getActivity())
                .get(ForgetViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgetBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SignInFragmentArgs args = SignInFragmentArgs.fromBundle(getArguments());
        binding.textForgetEmail.setText(args.getEmail().equals("default") ? "" : args.getEmail());

        binding.buttonConfirm.setOnClickListener(this::attemptChange);
        mForgetViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptChange(final View button) {
        validatePasswordsMatch();
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.editNewPassword2.getText().toString().trim()));

        mPassWordValidator.processResult(
                matchValidator.apply(binding.editNewPassword1.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editNewPassword1.setError("Passwords must match."));
    }

    private void validatePassword() {
        Optional<PasswordValidator.ValidationResult> resultMsg = mPassWordValidator.apply(binding.editNewPassword1.getText().toString());
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
                result -> binding.editNewPassword1.setError(finalErrorMessage));
    }

    private void verifyAuthWithServer() {
        mForgetViewModel.connect(
                binding.textForgetEmail.getText().toString(),
                binding.editNewPassword1.getText().toString());
    }

    private void navigateToLogin() {
        ForgetFragmentDirections.ActionForgetFragmentToSignInFragment directions =
                ForgetFragmentDirections.actionForgetFragmentToSignInFragment();

        directions.setEmail(binding.textForgetEmail.getText().toString());
        directions.setPassword(binding.editNewPassword1.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editNewPassword1.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToLogin();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}