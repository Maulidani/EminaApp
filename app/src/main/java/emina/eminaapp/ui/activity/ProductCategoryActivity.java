package emina.eminaapp.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import emina.eminaapp.R;
import emina.eminaapp.adapter.AdapterProduct;
import emina.eminaapp.api.ApiClient;
import emina.eminaapp.api.ApiInterface;
import emina.eminaapp.api.ModelApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryActivity extends AppCompatActivity {
    private String categoryId;
    private AdapterProduct adapterProduct;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvProductCategory;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading..."); // Setting Title
        progressDialog.show();
        progressDialog.setCancelable(false);

        categoryId = getIntent().getStringExtra("category");
        String intentName = getIntent().getStringExtra("name");

        getSupportActionBar().setTitle(intentName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvProductCategory = findViewById(R.id.rvProductCategory);
        layoutManager = new GridLayoutManager(this, 2,RecyclerView.VERTICAL,false);
        rvProductCategory.setLayoutManager(layoutManager);
        getProductCategory();
    }

    private void getProductCategory() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelApiResponse> call = apiInterface.getProductCategory(Integer.parseInt(categoryId));
        call.enqueue(new Callback<ModelApiResponse>() {
            @Override
            public void onResponse(Call<ModelApiResponse> call, Response<ModelApiResponse> response) {
                int value = response.body().getValue();

                if (value == 1) {
                    adapterProduct = new AdapterProduct(getApplicationContext(), response.body().getResult());
                    rvProductCategory.setAdapter(adapterProduct);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}