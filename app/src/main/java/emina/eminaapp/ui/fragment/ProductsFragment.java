package emina.eminaapp.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import emina.eminaapp.R;
import emina.eminaapp.adapter.AdapterProduct;
import emina.eminaapp.api.ApiClient;
import emina.eminaapp.api.ApiInterface;
import emina.eminaapp.api.ModelApiResponse;
import emina.eminaapp.ui.activity.ProductCategoryActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsFragment extends Fragment {
    private CardView cvSkinCare, cvMakeUp, cvBodyCare;
    private AdapterProduct adapterProduct;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvProduct;
    private ProgressDialog progressDialog;
    private SearchView inputSearchProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Loading..."); // Setting Title
        progressDialog.show();
        progressDialog.setCancelable(false);

        inputSearchProduct = view.findViewById(R.id.inputSearchProduct);
        cvSkinCare = view.findViewById(R.id.cvSkinCare);
        cvMakeUp = view.findViewById(R.id.cvMakeUp);
        cvBodyCare = view.findViewById(R.id.cvBodyCare);
        rvProduct = view.findViewById(R.id.rvProduct);

        layoutManager = new GridLayoutManager(view.getContext(), 2, RecyclerView.VERTICAL, false);
        rvProduct.setLayoutManager(layoutManager);

        clickCategory();
        getProduct();
    }

    @Override
    public void onResume() {
        super.onResume();
        searchProduct();
    }

    private void searchProduct() {
        inputSearchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterProduct.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void getProduct() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelApiResponse> call = apiInterface.getProducts();
        call.enqueue(new Callback<ModelApiResponse>() {
            @Override
            public void onResponse(Call<ModelApiResponse> call, Response<ModelApiResponse> response) {
                int value = response.body().getValue();

                if (value == 1) {
                    adapterProduct = new AdapterProduct(getActivity(), response.body().getResult());
                    rvProduct.setAdapter(adapterProduct);
                    progressDialog.dismiss();
                    adapterProduct.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void clickCategory() {
        cvSkinCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductCategoryActivity.class)
                        .putExtra("category", "1")
                        .putExtra("name", "Skin Care"));
            }
        });
        cvMakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductCategoryActivity.class)
                        .putExtra("category", "2")
                        .putExtra("name", "Make Up"));
            }
        });
        cvBodyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductCategoryActivity.class)
                        .putExtra("category", "3")
                        .putExtra("name", "Body Care"));
            }
        });
    }
}