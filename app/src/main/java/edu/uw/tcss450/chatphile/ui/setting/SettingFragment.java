package edu.uw.tcss450.chatphile.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uw.tcss450.chatphile.MainActivity;
import edu.uw.tcss450.chatphile.R;
import edu.uw.tcss450.chatphile.databinding.FragmentSettingBinding;

/**
 * A simple {@link Fragment} subclass.
 * @author Yihan
 */
public class SettingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentSettingBinding binding = FragmentSettingBinding.bind(getView());

        binding.buttonLogOut.setOnClickListener(button ->
                ((MainActivity) getActivity()).signOut()
        );
    }
}