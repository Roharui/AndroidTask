package com.task.github_profile;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.task.github_profile.Data.Item;
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

//    private void placeItem(){
//        RecyclerView recyclerView = (RecyclerView) binding.recyclerview;
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
//
//        List<Item> items = new ArrayList<>();
//        Item[] item = new Item[ITEM_SIZE];
//        item[0] = new Item(R.drawable.a, "#1", "test1");
////        item[1] = new Item(R.drawable.b, "#2", "test2");
////        item[2] = new Item(R.drawable.c, "#3", "test3");
////        item[3] = new Item(R.drawable.d, "#4", "test4");
////        item[4] = new Item(R.drawable.e, "#5", "test5");
//
//        for (int i = 0; i < ITEM_SIZE; i++) {
//            items.add(item[i]);
//        }
//
//        recyclerView.setAdapter(new ListView(getApplicationContext(), items, R.layout.activity_main));
//    }

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
                    Item item = new Item(arr.getJSONObject(i));
                    item.setImage(R.drawable.a);
                    lst.add(item);
                }

            } catch (JSONException e){}

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
