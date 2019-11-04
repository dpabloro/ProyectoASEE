package es.unex.giiis.asee.proyectoasee;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>  {
    private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();

    public interface OnItemClickListener {
        void onItemClick(ToDoItem item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ToDoAdapter(OnItemClickListener listener) {

        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // - Inflate the View for every element
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_items, parent,false);

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

    public void add(ToDoItem item) {

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
        private TextView priorityView;
        private TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);

            // Get the references to every widget of the Item View
            title = (TextView) itemView.findViewById(R.id.titleView);
            statusView = (CheckBox) itemView.findViewById(R.id.statusCheckBox);
            priorityView = (TextView) itemView.findViewById(R.id.priorityView);
            dateView = (TextView) itemView.findViewById(R.id.dateView);
        }

        public void bind(final ToDoItem toDoItem, final OnItemClickListener listener) {

            // Display Title in TextView
            title.setText(toDoItem.getTitle());

            // Display Priority in a TextView
            priorityView.setText(toDoItem.getPriority().toString());

            //  Display Time and Date.
            // Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and time String
            dateView.setText(toDoItem.FORMAT.format(toDoItem.getDate()));

            //  Set up Status CheckBox
            statusView.setChecked(toDoItem.getStatus() == ToDoItem.Status.DONE);

            statusView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {


                    // TODO - Set up and implement an OnCheckedChangeListener
                    // is called when the user toggles the status checkbox
                    if (isChecked) {
                        toDoItem.setStatus(ToDoItem.Status.DONE);
                        title.setBackgroundColor(Color.GREEN);
                    } else {
                        toDoItem.setStatus(ToDoItem.Status.PENDING);
                        title.setBackgroundColor(Color.WHITE);
                    }

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(toDoItem);
                }

            });
        }
    }
}
