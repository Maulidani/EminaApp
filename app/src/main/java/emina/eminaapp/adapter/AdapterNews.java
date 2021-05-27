package emina.eminaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import emina.eminaapp.R;
import emina.eminaapp.api.ModelResult;
import emina.eminaapp.ui.activity.DetailNewsActivity;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.NewsViewHolder> {

    private Context context;
    private final List<ModelResult> resultList;

    public AdapterNews(Context context, List<ModelResult> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        holder.tvTitle.setText(resultList.get(position).getTitle());
        holder.tvDate.setText(resultList.get(position).getDate());
        Glide.with(context)
                .load(resultList.get(position).getImg())
                .into(holder.img);

        holder.cvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DetailNewsActivity.class)
                        .putExtra("title", resultList.get(position).getTitle())
                        .putExtra("description", resultList.get(position).getDescription())
                        .putExtra("date", resultList.get(position).getDate())
                        .putExtra("img", resultList.get(position).getImg()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDate;
        ImageView img;
        CardView cvNews;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitleNews);
            tvDate = itemView.findViewById(R.id.tvDateNews);
            img = itemView.findViewById(R.id.imgnews);
            cvNews = itemView.findViewById(R.id.cvNews);
        }
    }
}
