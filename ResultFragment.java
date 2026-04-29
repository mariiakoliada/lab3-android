package com.example.lab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ResultFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        TextView textResult = view.findViewById(R.id.textResult);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        if (getArguments() != null) {
            textResult.setText(getArguments().getString("result"));
        }

        btnCancel.setOnClickListener(v -> {
            FragmentManager fm = requireActivity().getSupportFragmentManager();

            fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    InputFragment inputFragment = (InputFragment) fm
                            .findFragmentById(R.id.fragmentContainer);
                    if (inputFragment != null) {
                        inputFragment.clearForm();
                    }
                    fm.removeOnBackStackChangedListener(this);
                }
            });

            fm.popBackStack();
        });

        return view;
    }
}

