package com.example.lab1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout container;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        container = findViewById(R.id.historyContainer);
        dbHelper = new DatabaseHelper(this);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnDelete = findViewById(R.id.btnDelete);

        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            dbHelper.deleteAllRecords();
            container.removeAllViews();
            Toast.makeText(this, "Всі записи видалено!", Toast.LENGTH_SHORT).show();
            
            TextView emptyText = new TextView(this);
            emptyText.setText("Сховище порожнє. Немає збережених даних.");
            emptyText.setTextSize(16f);
            emptyText.setPadding(32, 32, 32, 32);
            container.addView(emptyText);
        });

        loadRecords();
    }

    private void loadRecords() {
        container.removeAllViews();
        if (dbHelper.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("Сховище порожнє. Немає збережених даних.");
            emptyText.setTextSize(16f);
            emptyText.setPadding(32, 32, 32, 32);
            container.addView(emptyText);
        } else {
            List<String> records = dbHelper.getAllRecords();
            for (String record : records) {
                TextView tv = new TextView(this);
                tv.setText(record);
                tv.setTextSize(14f);
                tv.setPadding(32, 24, 32, 24);

                View divider = new View(this);
                divider.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 2));
                divider.setBackgroundColor(0xFFCCCCCC);

                container.addView(tv);
                container.addView(divider);
            }
        }
    }
}