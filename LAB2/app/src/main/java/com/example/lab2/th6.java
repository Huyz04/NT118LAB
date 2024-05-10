package com.example.lab2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class th6 extends AppCompatActivity {
    private ArrayList<Hero> mHeros ;
    private RecyclerView mRecyclerHero;
    private HeroAdapter mHeroAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_th6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mRecyclerHero = findViewById(R.id.recyclerHero);
        mHeros = new ArrayList<>();
        createHeroList();
        mHeroAdapter = new HeroAdapter(this,mHeros);
        mRecyclerHero.setAdapter(mHeroAdapter);
        mRecyclerHero.setLayoutManager(new LinearLayoutManager(this));
    }
    private void createHeroList() {
        mHeros.add(new Hero("Thor",R.drawable.image_thor));
        mHeros.add(new Hero("IronMan",R.drawable.image_ironman));
        mHeros.add(new Hero("Hulk",R.drawable.image_hulk));
        mHeros.add(new Hero("SpiderMan",R.drawable.image_spiderman));
        mHeros.add(new Hero("Thor",R.drawable.image_thor));
        mHeros.add(new Hero("IronMan",R.drawable.image_ironman));
        mHeros.add(new Hero("Hulk",R.drawable.image_hulk));
        mHeros.add(new Hero("SpiderMan",R.drawable.image_spiderman));
        mHeros.add(new Hero("Thor",R.drawable.image_thor));
        mHeros.add(new Hero("IronMan",R.drawable.image_ironman));
    }

}