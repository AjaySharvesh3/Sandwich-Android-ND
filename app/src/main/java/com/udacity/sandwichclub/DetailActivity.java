package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

    private TextView descriptionTV, ingredientsTV, placeOfOriginTV, alsoKnownAsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        descriptionTV = (TextView) findViewById(R.id.description_tv);
        ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        placeOfOriginTV = (TextView) findViewById(R.id.origin_tv);
        alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
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
        description(sandwich);
        ingredients(sandwich);
        placeOfOrigin(sandwich);
        asKnownAs(sandwich);
    }

    private void description(Sandwich sandwich) {
        //TextView descriptionTV = findViewById(R.id.description_tv);
        String description = sandwich.getDescription();
        if (description == null || description.equals("")) {
            description = getString(R.string.no_description);
        }
        descriptionTV.setText(description);
    }

    private void ingredients(Sandwich sandwich) {
        //TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        String ingredients;
        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty()) {
            ingredients = getString(R.string.no_ingredients);
        } else {
            StringBuilder ingredientsSB = new StringBuilder();
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredientsSB
                        .append("\u2022 ") // Bullet point
                        .append(ingredientsList.get(i));
                // No line break after last ingredient
                if (i != ingredientsList.size() - 1)
                    ingredientsSB.append('\n');
            }
            ingredients = ingredientsSB.toString();
        }
        ingredientsTV.setText(ingredients);
    }

    private void placeOfOrigin(Sandwich sandwich) {
        //TextView placeOfOriginTV = findViewById(R.id.origin_tv);
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin == null || placeOfOrigin.equals("")) {
            placeOfOrigin = getString(R.string.no_origin);
        }
        placeOfOriginTV.setText(placeOfOrigin);
    }

    private void asKnownAs(Sandwich sandwich) {
        //TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);
        String alsoKnownAs;
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.isEmpty()) {
            alsoKnownAs = getString(R.string.no_aliases);
        } else {
            StringBuilder akaSB = new StringBuilder();
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                akaSB
                        .append("\u2022 ") // Bullet point
                        .append(alsoKnownAsList.get(i));
                // No line break after last aka
                if (i != alsoKnownAsList.size() - 1)
                    akaSB.append('\n');
            }
            alsoKnownAs = akaSB.toString();
        }
        alsoKnownAsTV.setText(alsoKnownAs);
    }
}
