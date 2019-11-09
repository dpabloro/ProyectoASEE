package es.unex.giiis.asee.proyectoasee;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyectoasee.Model.Posts;

public class AlimentAdapter extends RecyclerView.Adapter<AlimentAdapter.ViewHolder>  {
    private final List<AlimentItem> mItems = new ArrayList<AlimentItem>();
    private  List<Posts> mDataset;

    public interface OnItemClickListener {
        void onItemClick(AlimentItem item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlimentAdapter(OnItemClickListener listener) {

        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlimentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // - Inflate the View for every element
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.aliment_items, parent,false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(AlimentItem item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {

        return mItems.get(pos);

    }


    public void swap(List<Posts> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox statusView;
        private TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);

            // Get the references to every widget of the Item View
            title = (TextView) itemView.findViewById(R.id.titleAliment);
            statusView = (CheckBox) itemView.findViewById(R.id.alimentCheckBox);
        }

        public void bind(final AlimentItem alimentItem, final OnItemClickListener listener) {

            // Display Title in TextView
            title.setText(alimentItem.getTitle());

            //  Set up Status CheckBox
            statusView.setChecked(alimentItem.getStatus() == AlimentItem.Status.SELECTED);
            if (alimentItem.getStatus() == AlimentItem.Status.SELECTED) {
                title.setBackgroundColor(Color.CYAN);
            } else {
                alimentItem.setStatus(AlimentItem.Status.NOTSELECTED);
                title.setBackgroundColor(Color.WHITE);
            }


            statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {


                    // TODO - Set up and implement an OnCheckedChangeListener
                    // is called when the user toggles the status checkbox
                    if (isChecked) {
                        alimentItem.setStatus(AlimentItem.Status.SELECTED);
                        title.setBackgroundColor(Color.CYAN);
                    } else {
                        alimentItem.setStatus(AlimentItem.Status.NOTSELECTED);
                        title.setBackgroundColor(Color.WHITE);
                    }

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(alimentItem);
                }

            });
        }
    }
}
