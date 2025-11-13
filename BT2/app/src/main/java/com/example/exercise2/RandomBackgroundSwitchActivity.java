package com.example.exercise2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class RandomBackgroundSwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_randombackgroundswitch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.randombackgorundswitch), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backRandomBackground = findViewById(R.id.imageView_back_RandomBackground);
        backRandomBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RandomBackgroundSwitchActivity.this, RandomBackgroundActivity.class);
                startActivity(intent);
            }
        });

        Switch sw = (Switch) findViewById(R.id.switch_randomBackground);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //isChecked = true
                    Toast.makeText(RandomBackgroundSwitchActivity.this,"Random Background: on",Toast.LENGTH_LONG).show();
                    randomTheme();
                } else{
                    Toast.makeText(RandomBackgroundSwitchActivity.this,"Random Background: off",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void randomTheme(){
        ConstraintLayout bg = (ConstraintLayout) findViewById(R.id.randombackgorundswitch);
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