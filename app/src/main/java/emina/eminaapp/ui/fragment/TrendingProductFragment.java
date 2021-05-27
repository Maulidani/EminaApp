package emina.eminaapp.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class TrendingProductFragment extends Fragment {
    private String categoryId;
    private AdapterProduct adapterProduct;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvProductCategory;
    private ProgressDialog progressDialog;

    public TrendingProductFragment(String str) {
        categoryId = str;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Loading..."); // Setting Title
        progressDialog.show();
        progressDialog.setCancelable(false);

        rvProductCategory = view.findViewById(R.id.rvProductCategory);
        layoutManager = new GridLayoutManager(view.getContext(), 2,RecyclerView.VERTICAL,false);
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
                        adapterProduct = new AdapterProduct(getActivity(), response.body().getResult());
                        rvProductCategory.setAdapter(adapterProduct);
                        progressDialog.dismiss();
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
}