package com.example.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    private EditText editQuestion;
    private RadioGroup groupDifficulty, groupType;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input, container, false);

        editQuestion = view.findViewById(R.id.editQuestion);
        groupDifficulty = view.findViewById(R.id.groupDifficulty);
        groupType = view.findViewById(R.id.groupType);

        dbHelper = new DatabaseHelper(requireContext());

        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnOpen = view.findViewById(R.id.btnOpen);

        btnOk.setOnClickListener(v -> {
            String question = editQuestion.getText().toString();
            int diffId = groupDifficulty.getCheckedRadioButtonId();
            int typeId = groupType.getCheckedRadioButtonId();

            if (question.trim().isEmpty() || diffId == -1 || typeId == -1) {
                Toast.makeText(getContext(),
                        "Завершіть введення всіх даних",
                        Toast.LENGTH_LONG).show();
            } else {
                RadioButton rbDiff = view.findViewById(diffId);
                RadioButton rbType = view.findViewById(typeId);

                String difficulty = rbDiff.getText().toString();
                String type = rbType.getText().toString();

                boolean success = dbHelper.insertRecord(question, difficulty, type);

                if (success) {
                    Toast.makeText(getContext(),
                            "Дані успішно збережено!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),
                            "Помилка збереження!",
                            Toast.LENGTH_SHORT).show();
                }

                String result = "Питання: " + question +
                        "\nСкладність: " + difficulty +
                        "\nТип: " + type;

                ResultFragment resultFragment = new ResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString("result", result);
                resultFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, resultFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), HistoryActivity.class);
            startActivity(intent);
        });

        return view;
    }

    public void clearForm() {
        editQuestion.setText("");
        groupDifficulty.clearCheck();
        groupType.clearCheck();
    }
}