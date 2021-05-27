package emina.eminaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import emina.eminaapp.R;
import emina.eminaapp.api.ModelResult;
import emina.eminaapp.ui.activity.DetailNewsActivity;
import emina.eminaapp.ui.activity.DetailProductActivity;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> implements Filterable {
    private Context context;
    private List<ModelResult> resultList;

    public AdapterProduct(Context context, List<ModelResult> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final AdapterProduct.ProductViewHolder myViewHolder = new AdapterProduct.ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.tvName.setText(resultList.get(position).getName());
        Glide.with(context)
                .load(resultList.get(position).getImg())
                .into(holder.img);

        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailProductActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("name", resultList.get(position).getName())
                        .putExtra("description", resultList.get(position).getDescription())
                        .putExtra("img", resultList.get(position).getImg())
                        .putExtra("kg", resultList.get(position).getKg()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView img;
        CardView cvProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameProduct);
            img = itemView.findViewById(R.id.imgProduct);
            cvProduct = itemView.findViewById(R.id.cvProduct);
        }
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String string = constraint.toString();
                if (string.isEmpty()) {
                } else {
                    ArrayList<ModelResult> filteredList = new ArrayList<>();
                    for (ModelResult modelResult : resultList) {
                        if (modelResult.getName().toLowerCase().contains(string)
                                || modelResult.getName().toUpperCase().contains(string)
                                || modelResult.getName().contains(string)) {
                            filteredList.add(modelResult);
                        }
                    }
                    resultList = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = resultList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                resultList = (ArrayList<ModelResult>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
