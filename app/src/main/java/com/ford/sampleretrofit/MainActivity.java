package com.ford.sampleretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String API_BASE_URL = "https://jsonplaceholder.typicode.com/";
    private TextView textviewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewResult = findViewById(R.id.text_view_result);

        Retrofit retro2Obj=new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retro2Obj.create(JsonPlaceHolderApi.class);

        Call<List<Post>>  call =jsonPlaceHolderApi.getPost();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textviewResult.setText("Code "+response.code());
                    return;
                }

                List<Post> postsValue = response.body();
                for (int i=0; i<postsValue.size(); i++){
                    String content = "";
                    content += "ID: "+ postsValue.get(i).getId()+"\n";
                    content += "User ID: "+ postsValue.get(i).getUserId()+"\n";
                    content += "Title: "+ postsValue.get(i).getTitle()+"\n";
                    content += "ID: "+ postsValue.get(i).getText()+"\n\n";
                    Log.d("MainActivity","Current "+ content);
                    textviewResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textviewResult.setText(t.getMessage());
                    }
                });

            }
        });


    }
}