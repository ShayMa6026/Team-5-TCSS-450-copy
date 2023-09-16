package edu.uw.tcss450.chatphile.ui.auth.forget;

import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkExcludeWhiteSpace;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPinLength;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdLength;
import static edu.uw.tcss450.chatphile.utils.PasswordValidator.checkPwdSpecialChar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uw.tcss450.chatphile.databinding.FragmentForgetVarifyBinding;
import edu.uw.tcss450.chatphile.utils.PasswordValidator;

/**
 * A simple {@link Fragment} subclass.
 * @author Yihan
 */
public class ForgetVerifyFragment extends Fragment {

    private FragmentForgetVarifyBinding binding;
    private ForgetVerifyViewModel mForgetVerifyViewModel;
    private PasswordValidator mVerificationValidator = checkPinLength(6)
            .and(checkExcludeWhiteSpace());
    private PasswordValidator mEmailValidator = checkPwdLength(2)
            .and(checkExcludeWhiteSpace())
            .and(checkPwdSpecialChar("@"));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForgetVerifyViewModel = new ViewModelProvider(getActivity()).get(ForgetVerifyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgetVarifyBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonResend.setOnClickListener(this::attemptSend);
        binding.buttonForgetVerify.setOnClickListener(this::attemptVerify);
        mForgetVerifyViewModel.addResponseObserver(getViewLifecycleOwner(),
                this::observeResponse);
    }

    private void attemptSend(final View button) {
        mEmailValidator.processResult(
                mEmailValidator.apply(binding.editForgetEmail.getText().toString()),
                this::emailSend,
                result -> binding.editForgetEmail.setError("Please enter a valid email address."));
    }

    private void emailSend() {
        mForgetVerifyViewModel.sendVerify(
                binding.editForgetEmail.getText().toString());
    }

    private void attemptVerify(final View button) {
        mVerificationValidator.processResult(
                mVerificationValidator.apply(binding.editVerifyCode.getText().toString().trim()),
                this::codeVerify,
                result -> binding.editVerifyCode.setError("Please enter a valid verification code."));
    }

    private void codeVerify() {
        mForgetVerifyViewModel.confirmVerify(
                binding.editForgetEmail.getText().toString(),
                binding.editVerifyCode.getText().toString());
    }

    private void navigateToForget() {
        ForgetVerifyFragmentDirections.ActionForgetVerifyFragmentToForgetFragment directions =
                ForgetVerifyFragmentDirections.actionForgetVerifyFragmentToForgetFragment();

        directions.setEmail(binding.editForgetEmail.getText().toString());

        Navigation.findNavController(getView()).navigate(directions);
    }

    private void observeResponse(final JSONObject response) {
        if (response.length() > 0) {
            if (response.has("code")) {
                try {
                binding.editVerifyCode.setError(
                        "Error Authenticating: " + //"incorrect verification code");
                response.getJSONObject("data").getString("message"));
                } catch (JSONException e) {
                Log.e("JSON Parse Error", e.getMessage());
                }
            } else {
                if (response.has("success")) {
                    navigateToForget();
                }
            }
        } else {
            Log.d("JSON Response", "No Response");
        }
    }
}
