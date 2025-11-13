package com.example.exercise2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class RandomBackgroundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_randombackground);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.randombackground), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView nextDemo1 = findViewById(R.id.imageView_next_randomBackgroundSwitch);
        nextDemo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomBackgroundActivity.this, RandomBackgroundSwitchActivity.class);
                startActivity(intent);
            }
        });
        randomTheme();
    }

    private void randomTheme(){
        ConstraintLayout bg = (ConstraintLayout) findViewById(R.id.randombackground);
        bg.setBackgroundColor(Color.BLUE);
        bg.setBackgroundResource(R.drawable.bg2);

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.drawable.bg1);
        arrayList.add(R.drawable.bg2);
        arrayList.add(R.drawable.bg3);
        arrayList.add(R.drawable.bg4);
        arrayList.add(R.drawable.bg5);
        arrayList.add(R.drawable.bg6);
        Random random = new Random();
        int vitri = random.nextInt(arrayList.size());
        bg.setBackgroundResource(arrayList.get(vitri));
    }
}