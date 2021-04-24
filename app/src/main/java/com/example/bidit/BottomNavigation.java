package com.example.bidit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class BottomNavigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AuctionsView()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new AuctionsView();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new MyAuctions();
                            break;
                        case R.id.account:
                            selectedFragment = new myAccount();
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.more_options, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){


            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(BottomNavigation.this,UserLogin.class);
            startActivity(intent);
            Toast.makeText(BottomNavigation.this,"User logged out!",Toast.LENGTH_LONG).show();

        }
        if(item.getItemId() == R.id.share){


            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My App Name");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Excellent app for biding!");
            startActivity(Intent.createChooser(sharingIntent, "Share app via"));

        }if(item.getItemId() == R.id.privacy){


            final TextView input = new TextView (this);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Privacy policy");
            alert.setMessage(
                    "What is a Privacy Policy\n" +
                    "A Privacy Policy is a legal statement that specifies what the business owner does with the personal data collected from users, along with how the data is processed and for what purposes.\n" +
                    "\n" +
                    "In 1968, Council of Europe did studies on the threat of the Internet expansion as they were concerned with the effects of technology on human rights. This lead to the development of policies that were to be developed to protect personal data.\n" +
                    "\n" +
                    "This marks the start of what we know now as a \"Privacy Policy.\" While the name \"Privacy Policy\" refers to the legal agreement, the concept of privacy and protecting user data is closely related.\n" +
                    "\n" +
                    "This agreement can also be known under these names:\n" +
                    "\n" +
                    "Privacy Statement\n" +
                    "Privacy Notice\n" +
                    "Privacy Information\n" +
                    "Privacy Page\n" +
                            "What is a Privacy Policy\n" +
                            "A Privacy Policy is a legal statement that specifies what the business owner does with the personal data collected from users, along with how the data is processed and for what purposes.\n" +
                            "\n" +
                            "In 1968, Council of Europe did studies on the threat of the Internet expansion as they were concerned with the effects of technology on human rights. This lead to the development of policies that were to be developed to protect personal data.\n" +
                            "\n" +
                            "This marks the start of what we know now as a \"Privacy Policy.\" While the name \"Privacy Policy\" refers to the legal agreement, the concept of privacy and protecting user data is closely related.\n" +
                            "\n" +
                            "This agreement can also be known under these names:\n" +
                            "\n" +
                            "Privacy Statement\n" +
                            "Privacy Notice\n" +
                            "Privacy Information\n" +
                            "Privacy Page\n"
            );

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Do something with value!
                }
            });

            alert.show();

        }

        if(item.getItemId() == R.id.terms){


            final TextView input = new TextView (this);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Terms and conditions");
            alert.setMessage(
                    "Terms and conditions\n" +
                            "A Privacy Policy is a legal statement that specifies what the business owner does with the personal data collected from users, along with how the data is processed and for what purposes.\n" +
                            "\n" +
                            "In 1968, Council of Europe did studies on the threat of the Internet expansion as they were concerned with the effects of technology on human rights. This lead to the development of policies that were to be developed to protect personal data.\n" +
                            "\n" +
                            "This marks the start of what we know now as a \"Privacy Policy.\" While the name \"Privacy Policy\" refers to the legal agreement, the concept of privacy and protecting user data is closely related.\n" +
                            "\n" +
                            "This agreement can also be known under these names:\n" +
                            "\n" +
                            "Privacy Statement\n" +
                            "Privacy Notice\n" +
                            "Privacy Information\n" +
                            "Privacy Page\n" +
                            "What is a Privacy Policy\n" +
                            "A Privacy Policy is a legal statement that specifies what the business owner does with the personal data collected from users, along with how the data is processed and for what purposes.\n" +
                            "\n" +
                            "In 1968, Council of Europe did studies on the threat of the Internet expansion as they were concerned with the effects of technology on human rights. This lead to the development of policies that were to be developed to protect personal data.\n" +
                            "\n" +
                            "This marks the start of what we know now as a \"Privacy Policy.\" While the name \"Privacy Policy\" refers to the legal agreement, the concept of privacy and protecting user data is closely related.\n" +
                            "\n" +
                            "This agreement can also be known under these names:\n" +
                            "\n" +
                            "Privacy Statement\n" +
                            "Privacy Notice\n" +
                            "Privacy Information\n" +
                            "Privacy Page\n"
            );

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }

}