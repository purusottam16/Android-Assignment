package com.example.movieactivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private String[] data;
    Context context;
    public Context real_context;

    public MyAdapter(String[] data, Context context){

        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_view, viewGroup, false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            String title = data[i];
            myViewHolder.textView.setText(title);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        private RecyclerViewClickListener mListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            real_context = itemView.getContext();

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View vClick) {

            int pos = getLayoutPosition();
            Intent intent = new Intent(real_context, MovieInformationActivity.class);
            intent.putExtra("buttonText", textView.getText());


            real_context.startActivity(intent);

        }
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

}
