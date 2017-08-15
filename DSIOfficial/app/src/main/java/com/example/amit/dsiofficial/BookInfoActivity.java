package com.example.amit.dsiofficial;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BookInfoActivity extends Activity {

    private TextView title, author, subject, price, name, phone, email, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        title = (TextView) findViewById(R.id.infoTitle);
        author = (TextView) findViewById(R.id.infoAuthor);
        subject = (TextView) findViewById(R.id.infoSubject);
        price = (TextView) findViewById(R.id.infoPrice);
        name = (TextView) findViewById(R.id.infoBookName);
        phone = (TextView) findViewById(R.id.infoPhone);
        email = (TextView) findViewById(R.id.infoEmail);
        call = (TextView) findViewById(R.id.callSeller);

        Bundle extras = getIntent().getExtras();

        title.setText(extras.getString("Title"));
        author.setText(extras.getString("Author"));
        subject.setText(extras.getString("Subject"));
        price.setText(extras.getString("Price"));
        name.setText(extras.getString("Name"));
        phone.setText(extras.getString("Phone"));
        email.setText(extras.getString("Email"));
        Log.d("ALERT !!", phone.getText().toString());
        Log.d("ALERT !!", extras.getString("Phone"));

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = "tel:" + phone.getText().toString();
                Log.d("ALERT !!", phone.getText().toString());

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(number));
                startActivity(intent);
            }
        });
    }
}
