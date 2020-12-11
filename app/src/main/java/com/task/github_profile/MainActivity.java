package com.task.github_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.task.github_profile.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int ITEM_SIZE = 1;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getUser("Roharui");
    }

    private void placeItem(){
        RecyclerView recyclerView = (RecyclerView) binding.recyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item(R.drawable.a, "#1", "test1");
//        item[1] = new Item(R.drawable.b, "#2", "test2");
//        item[2] = new Item(R.drawable.c, "#3", "test3");
//        item[3] = new Item(R.drawable.d, "#4", "test4");
//        item[4] = new Item(R.drawable.e, "#5", "test5");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new ListView(getApplicationContext(), items, R.layout.activity_main));
    }

    private void getUser(String login){

        final TextView textView = (TextView) binding.name;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.github.com/users/" + login;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        User u = User.toUser(response);
                        textView.setText(u.getLogin());

                        setImgAsUrl(u.getImg());
                        Chip follower = (Chip) binding.follower;
                        Chip following = (Chip) binding.following;

                        follower.setText("Follower : " + Integer.toString(u.getFollwer()));
                        following.setText("Following : " + Integer.toString(u.getFollwing()));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CharSequence text = "Request Error!";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(getApplicationContext(), text, duration).show();
            }
        });

        queue.add(stringRequest);
    }

    public void setImgAsUrl(String url){
        Glide.with(this).load(url).into(binding.imageView);
    }
}