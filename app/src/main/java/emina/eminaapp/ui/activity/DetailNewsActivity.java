package emina.eminaapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import emina.eminaapp.R;

public class DetailNewsActivity extends AppCompatActivity {
    private TextView tvTitle, tvDesc, tvDate;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        String intentTitle = getIntent().getStringExtra("title");
        String intentDesc = getIntent().getStringExtra("description");
        String intentDate = getIntent().getStringExtra("date");
        String intentImg = getIntent().getStringExtra("img");

        getSupportActionBar().setTitle(intentTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle = findViewById(R.id.tvTitleNews);
        tvDesc = findViewById(R.id.tvDescNews);
        tvDate = findViewById(R.id.tvDateNews);
        img = findViewById(R.id.imgnews);

        tvTitle.setText(intentTitle);
        tvDesc.setText(intentDesc);
        tvDate.setText(intentDate);
        Glide.with(this)
                .load(intentImg)
                .into(img);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}