package com.task.github_profile;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.task.github_profile.Data.Item;
import com.task.github_profile.Data.Logo;
import com.task.github_profile.Data.User;
import com.task.github_profile.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestManager {

    private ActivityMainBinding binding;
    private MainActivity main;

    public RequestManager(ActivityMainBinding binding, MainActivity main) {
        this.binding = binding;
        this.main = main;
    }

    private void setImgAsUrl(String url){
        Glide.with(this.main).load(url).into(binding.imageView);
    }

    public Response.Listener<String> userRequest = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            // Display the first 500 characters of the response string.
            User u = User.toUser(response);
            binding.name.setText(u.getLogin());

            setImgAsUrl(u.getImg());
            Chip follower = (Chip) binding.follower;
            Chip following = (Chip) binding.following;

            follower.setText("Follower : " + Integer.toString(u.getFollwer()));
            following.setText("Following : " + Integer.toString(u.getFollwing()));
        }
    };

    public Response.Listener<String> repoRequest = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Item> lst = new ArrayList<Item>();

            RecyclerView recyclerView = (RecyclerView) binding.recyclerview;
            LinearLayoutManager layoutManager = new LinearLayoutManager(main.getApplicationContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);

            try {
                JSONArray arr = new JSONArray(response);

                for(int i=0; i < arr.length(); i++){
                    JSONObject json = arr.getJSONObject(i);
                    Item item = new Item(json);

                    Integer img = Logo.getLogo(json.getString("language"));
                    item.setImage(img.intValue());

                    lst.add(item);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }

            recyclerView.setAdapter(new ListView(main.getApplicationContext(), lst, R.layout.activity_main));

        }
    };

    public Response.ErrorListener errorManager =  new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            CharSequence text = "Request Error!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(main.getApplicationContext(), text, duration).show();
        }
    };
}
