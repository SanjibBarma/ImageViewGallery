package com.example.galleryappdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    ArrayList<ImageModel> imageArrayList;
    ArrayList<ImageModel> searchList;
    Context context;
    List<ImageModel> filterList = new ArrayList<>();

    public ImageAdapter(ArrayList<ImageModel> imageArrayList, Context context) {
        this.imageArrayList = imageArrayList;
        this.context = context;
        searchList = new ArrayList<>(imageArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.view.setText(imageArrayList.get(position).getOutletName());
        Glide.with(context).load(imageArrayList.get(position).getPath()).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imageData = imageArrayList.get(position).getPath();
                String nameData = imageArrayList.get(position).getOutletName();
                context.startActivity(new Intent(context,FullScreenActivity.class)
                        .putExtra("parseData", imageData)
                        .putExtra("imageName", nameData));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }



    public Filter getOutletFilter(){
        return outletFilter;
    }

    private Filter outletFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filterList = new ArrayList<>();
            if (charSequence == null|| charSequence.length()==0){

                filterList.addAll(searchList);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ImageModel image: searchList){
                    if (image.getOutletName().toLowerCase().contains(filterPattern)){
                        filterList.add(image);
                    }
                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            imageArrayList.clear();
            imageArrayList.addAll((List<ImageModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public int getItemCountAfterFilter() {
        return filterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_image);
            view = itemView.findViewById(R.id.list_item_title);
        }
    }
}
