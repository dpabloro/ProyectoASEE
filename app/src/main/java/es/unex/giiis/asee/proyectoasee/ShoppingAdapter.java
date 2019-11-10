package es.unex.giiis.asee.proyectoasee;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder>  {
    private ArrayList<ShoppingItem> mItems = new ArrayList<ShoppingItem>();

    public interface OnItemClickListener {
        void onItemClick(ShoppingItem item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShoppingAdapter(OnItemClickListener listener) {

        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // - Inflate the View for every element
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_items, parent,false);

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

    public void add(ShoppingItem item) {

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

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private CheckBox statusView;
        private TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);

            // Get the references to every widget of the Item View
            title = (TextView) itemView.findViewById(R.id.titleView);
            statusView = (CheckBox) itemView.findViewById(R.id.statusCheckBox);
            dateView = (TextView) itemView.findViewById(R.id.dateView);
        }

        public void bind(final ShoppingItem shoppingItem, final OnItemClickListener listener) {

            // Display Title in TextView
            title.setText(shoppingItem.getTitle());


            //  Display Time and Date.
            // Hint - use shoppingItem.FORMAT.format(shoppingItem.getDate()) to get date and time String
           // dateView.setText(shoppingItem.FORMAT.format(shoppingItem.getDate()));
            dateView.setText(shoppingItem.getDate());
            //  Set up Status CheckBox
            statusView.setChecked(shoppingItem.getStatus() == ShoppingItem.Status.DONE);
            if (shoppingItem.getStatus() == ShoppingItem.Status.DONE) {
                shoppingItem.setStatus(ShoppingItem.Status.DONE);
                title.setBackgroundColor(Color.GREEN);
            } else {
                shoppingItem.setStatus(ShoppingItem.Status.PENDING);
                title.setBackgroundColor(Color.YELLOW);
            }


            statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {


                    // TODO - Set up and implement an OnCheckedChangeListener
                    // is called when the user toggles the status checkbox
                    if (isChecked) {
                        shoppingItem.setStatus(ShoppingItem.Status.DONE);
                        title.setBackgroundColor(Color.GREEN);
                    } else {
                        shoppingItem.setStatus(ShoppingItem.Status.PENDING);
                        title.setBackgroundColor(Color.YELLOW);
                    }

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(shoppingItem);
                }

            });
        }
    }
}
