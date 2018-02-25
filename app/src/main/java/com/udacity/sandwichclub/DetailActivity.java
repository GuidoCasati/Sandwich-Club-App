package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e("intent", "onCreate: fail");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e("intent", "EXTRA_POSITION not found in intent");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.e("intent", "Sandwich data unavailable");
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView TV_AKA = findViewById(R.id.also_known_tv);
        TextView TV_Desc = findViewById(R.id.description_tv);
        TextView TV_Ingredients = findViewById(R.id.ingredients_tv);
        TextView TV_Origin = findViewById(R.id.origin_tv);

        List<String> lsAKA = sandwich.getAlsoKnownAs();
        String sDesc = sandwich.getDescription();
        List<String> lsIngredients = sandwich.getIngredients();
        String sOrigin= sandwich.getPlaceOfOrigin();

        for (String aka : lsAKA)
        {
            if (aka.endsWith(","))
                aka = aka.substring(0,aka.length()-1);

            if (!aka.trim().isEmpty())
                TV_AKA.append(aka+"\n");
        }

        for (String ing : lsIngredients)
        {
            if (ing.endsWith(","))
                ing = ing.substring(0,ing.length()-1);

            if (!ing.trim().isEmpty())
                TV_Ingredients.append((ing+"\n"));
        }

        TV_Desc.setText(sDesc);
        TV_Origin.setText(sOrigin);



    }
}
