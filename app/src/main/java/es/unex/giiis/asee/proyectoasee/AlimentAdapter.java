package es.unex.giiis.asee.proyectoasee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlimentAdapter extends RecyclerView.Adapter<AlimentAdapter.MyViewHolder>  {
    private final List<AlimentItem> mItems = new ArrayList<AlimentItem>();
    private  List<Posts> mDataset;


    public interface OnListInteractionListener {
       public void onListInteraction(String url);     //Type of the element to be returned
    }

    private final OnListInteractionListener mListener;



    // Create new views (invoked by the layout manager)

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleView;
        public View mView;

        public Posts mItem;

        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mTitleView = v.findViewById(R.id.titleAliment);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public AlimentAdapter(List<Posts> myDataset,OnListInteractionListener listener) {
        mDataset=myDataset;
        mListener = listener;
    }

    @Override
    public AlimentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aliment_items, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mItem = mDataset.get(position);
        holder.mTitleView.setText(mDataset.get(position).getStrIngredient());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<Posts> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }
}
