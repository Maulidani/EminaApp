package emina.eminaapp.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import emina.eminaapp.R;
import emina.eminaapp.adapter.AdapterNews;
import emina.eminaapp.api.ApiClient;
import emina.eminaapp.api.ApiInterface;
import emina.eminaapp.api.ModelApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private AdapterNews adapterNews;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView rvNews;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Loading..."); // Setting Title
        progressDialog.show();
        progressDialog.setCancelable(false);

        rvNews = view.findViewById(R.id.rvNews);
        getNews();
    }

    private void getNews() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ModelApiResponse> call = apiInterface.getNews();
        call.enqueue(new Callback<ModelApiResponse>() {
            @Override
            public void onResponse(Call<ModelApiResponse> call, Response<ModelApiResponse> response) {
                int value = response.body().getValue();

                if (value == 1) {
                    adapterNews = new AdapterNews(getActivity(), response.body().getResult());
                    layoutManager = new LinearLayoutManager(getActivity());
                    rvNews.setLayoutManager(layoutManager);
                    rvNews.setAdapter(adapterNews);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelApiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Connenction Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}