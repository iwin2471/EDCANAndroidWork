package net.iwin247.calendar.utils;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.Object;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import net.iwin247.calendar.R;
import net.iwin247.calendar.activity.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016-08-14.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Summary>{

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<ContactInfo> mDataset;
    private static MyClickListener myClickListener;

    public static class Summary extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView summary;
        TextView start;
        TextView end;

        public Summary(View itemView) {
            super(itemView);
            summary = (TextView) itemView.findViewById(R.id.summary);
            start = (TextView) itemView.findViewById(R.id.Start);
            end = (TextView) itemView.findViewById(R.id.End);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(1,v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerAdapter(ArrayList<ContactInfo> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public Summary onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardview, parent, false);

        Summary summary = new Summary(view);
        return summary;
    }

    @Override
    public void onBindViewHolder(Summary holder, int position) {
        holder.summary.setText(mDataset.get(position).getMsummary());
        holder.start.setText(mDataset.get(position).getmStime());
        holder.end.setText(mDataset.get(position).getmEtime());
    }

    public void addItem(ContactInfo dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}