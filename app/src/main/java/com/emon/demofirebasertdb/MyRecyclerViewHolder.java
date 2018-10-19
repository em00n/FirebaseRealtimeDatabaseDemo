package com.emon.demofirebasertdb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView nameTV,emailTV;
    OnLongClickListener onLongClickListener;

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        nameTV=(TextView) itemView.findViewById(R.id.nameTV);
        emailTV=(TextView)itemView.findViewById(R.id.emailTV);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onLongClickListener.onClick(v,getAdapterPosition());
    }

}
