package com.example.amit.dsiofficial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ChooseSectionActivity extends AppCompatActivity {

    private Button okbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_section);

        okbutton = (Button) findViewById(R.id.ok_button_choose_section);
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseSectionActivity.this, MainActivity.class);
                intent.putExtra("fromChooseSection", true);
                startActivity(intent);
                finish();
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.choose_section_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sections_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                User.setClassChosen((String) adapterView.getItemAtPosition(i));
                getSharedPreferences("Login", MODE_PRIVATE).edit().putString("yearChosen", (String) adapterView.getItemAtPosition(i)).commit();
                //Toast.makeText(NewBookActivity.this, subject, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
