package com.example.snackbar;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);


        testConnection();

        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        view.setMinimumHeight(20);
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

    }

    private void testConnection() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        NetworkCapabilities capabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());

        if (info != null && info.isConnected()) {

            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                showSnackBar(1);
            } else {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
                showSnackBar(2);
            }


        }
        else {
            showSnackBar(0);
        }

    }


    private void showSnackBar(int check) {

        if (check == 1) {
            snackbar = Snackbar.make(container, "Connected To Wifi", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (check == 2) {
            snackbar = Snackbar.make(container, "Connected To Mobile Data", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else {
            snackbar = Snackbar.make(container, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            snackbar.setAction("RE-TRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testConnection();
                }
            });
            snackbar.setActionTextColor(Color.BLUE);
        }

    }
}
