package edu.uw.tcss450.chatphile.ui.auth.register;

import static edu.uw.tcss450.chatphile.utils.PasswordValidator.*;

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

import edu.uw.tcss450.chatphile.databinding.FragmentRegisterBinding;
import edu.uw.tcss450.chatphile.utils.PasswordValidator;


/**
 * A simple {@link Fragment} subclass.
 * @author Edwin
 */
public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

    private RegisterViewModel mRegisterModel;

    private PasswordValidator mNameValidator = checkPwdLength(1);

    private PasswordValidator mEmailValidator = checkPwdLength(6)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    private PasswordValidator mPassWordValidator =
            checkClientPredicate(pwd -> pwd.equals(binding.editPassword2.getText().toString()))
                    .and(checkPwdLength(7))
                    .and(checkPwdSpecialChar())
                    .and(checkExcludeWhiteSpace())
                    .and(checkPwdDigit())
                    .and(checkPwdLowerCase().or(checkPwdUpperCase()));

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegisterModel = new ViewModelProvider(getActivity())
                .get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonRegister.setOnClickListener(this::attemptRegister);
        mRegisterModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptRegister(final View button) {
        validateFirst();
    }

    private void validateFirst() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editFirst.getText().toString().trim()),
                this::validateLast,
                result -> binding.editFirst.setError("Please enter a valid first name."));
    }

    private void validateLast() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editLast.getText().toString().trim()),
                this::validateNickname,
                result -> binding.editLast.setError("Please enter a valid last name."));
    }

    private void validateNickname() {
        mNameValidator.processResult(
                mNameValidator.apply(binding.editNickname.getText().toString().trim()),
                this::validateEmail,
                result -> binding.editNickname.setError("Please enter a nickname."));
    }

    private void validateEmail() {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editEmail.getText().toString().trim()),
                this::validatePasswordsMatch,
                result -> binding.editEmail.setError("Please enter a valid Email address."));
    }

    private void validatePasswordsMatch() {
        PasswordValidator matchValidator =
                checkClientPredicate(
                        pwd -> pwd.equals(binding.editPassword2.getText().toString().trim()));

        mEmailValidator.processResult(
                matchValidator.apply(binding.editPassword1.getText().toString().trim()),
                this::validatePassword,
                result -> binding.editPassword1.setError("Passwords must match."));
    }

    private void validatePassword() {
        Optional<ValidationResult> resultMsg = mPassWordValidator.apply(binding.editPassword1.getText().toString());
        String errorMessage = "Please enter a valid password.";

        if (resultMsg.isPresent()) {
            if (resultMsg.get() == ValidationResult.PWD_INVALID_LENGTH) {
                errorMessage = "Password must be at least 8 characters long.";
            } else if (resultMsg.get() == ValidationResult.PWD_MISSING_SPECIAL) {
                errorMessage = "Password is missing at least one of these special characters: @#$%&*!?";
            } else if (resultMsg.get() == ValidationResult.PWD_INCLUDES_WHITESPACE) {
                errorMessage = "Password must not include whitespace.";
            } else if (resultMsg.get() == ValidationResult.PWD_MISSING_DIGIT) {
                errorMessage = "Password must contain a digit.";
            } else if (resultMsg.get() == ValidationResult.PWD_MISSING_LOWER ||
                    resultMsg.get() == ValidationResult.PWD_MISSING_UPPER) {
                errorMessage = "Password must contain an alphabetic character.";
            }
        }

        String finalErrorMessage = errorMessage;
        mPassWordValidator.processResult(
                resultMsg,
                this::verifyAuthWithServer,
                result -> binding.editPassword1.setError(finalErrorMessage));
    }

    private void verifyAuthWithServer() {
        mRegisterModel.connect(
                binding.editFirst.getText().toString(),
                binding.editLast.getText().toString(),
                binding.editNickname.getText().toString(),
                binding.editEmail.getText().toString(),
                binding.editPassword1.getText().toString());
        // This is an Asynchronous call. No statements after should rely on the result of connect().
    }

    private void navigateToVerify() {
        RegisterFragmentDirections.ActionRegisterFragmentToVerificationFragment directions =
                RegisterFragmentDirections.actionRegisterFragmentToVerificationFragment();

        directions.setEmail(binding.editEmail.getText().toString());
        directions.setPassword(binding.editPassword1.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);

    }

    /**
     * An observer on the HTTP Response from the web server. This observer should be
     * attached to SignInViewModel.
     *
     * @param response the Response from the server
     */
    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                    binding.editEmail.setError(
                            "Error Authenticating: " +
                                    response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                    Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                navigateToVerify();
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
