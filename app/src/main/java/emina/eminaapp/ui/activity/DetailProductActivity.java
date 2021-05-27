package emina.eminaapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import emina.eminaapp.R;

public class DetailProductActivity extends AppCompatActivity {
    private TextView tvName, tvDesc, tvKg;
    private ImageView img;
    private Button btnBuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        String intentname = getIntent().getStringExtra("name");
        String intentDesc = getIntent().getStringExtra("description");
        String intentImg = getIntent().getStringExtra("img");
        String intentKg = getIntent().getStringExtra("kg");

        getSupportActionBar().setTitle(intentname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = findViewById(R.id.tvNameProduct);
        tvDesc = findViewById(R.id.tvDescProduct);
        tvKg = findViewById(R.id.tvInfo);
        img = findViewById(R.id.imgProduct);
        btnBuy = findViewById(R.id.btnBuy);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailProductActivity.this, "Buy", Toast.LENGTH_SHORT).show();
            }
        });

        tvName.setText(intentname);
        tvDesc.setText(intentDesc);
        tvKg.setText(intentKg);
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