package com.task.github_profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.task.github_profile.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RequestManager req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        req = new RequestManager(binding, this);

        sendRequest("Roharui");

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            String login = data.getStringExtra("login");
            if(login.equals("null")){
                Toast.makeText(getApplicationContext(), "No user find.", Toast.LENGTH_LONG).show();
            } else {
                sendRequest(login);
            }
        }
    }

    private void sendRequest(String login){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String user_url ="https://api.github.com/users/" + login;
        String repo_url ="https://api.github.com/users/" + login + "/repos";

        // Request a string response from the provided URL.
        StringRequest userRequest = new StringRequest(Request.Method.GET, user_url,  req.userRequest, req.errorManager);
        StringRequest repoRequest = new StringRequest(Request.Method.GET, repo_url,  req.repoRequest, req.errorManager);

        queue.add(userRequest);
        queue.add(repoRequest);
    }
}