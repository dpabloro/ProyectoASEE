package es.unex.giiis.asee.proyectoasee;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlimentAdapter extends RecyclerView.Adapter<AlimentAdapter.MyViewHolder>  {
    private  ArrayList<Aliments> mDataset;
    private static final String TAG = "AlimentAdapter-UserInterface";


    public interface OnListInteractionListener {
       public void onListInteraction(String url);     //Type of the element to be returned
    }

    private final OnListInteractionListener mListener;



    // Create new views (invoked by the layout manager)

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitleView;
        public View mView;

        public Aliments mItem;

        public CheckBox selectedView;

        public MyViewHolder(View v) {
            super(v);
            mView=v;
            mTitleView = v.findViewById(R.id.titleAliment);
            selectedView= v.findViewById(R.id.alimentCheckBox);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public AlimentAdapter(ArrayList<Aliments> myDataset, OnListInteractionListener listener) {
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

    public ArrayList<Aliments> getItems(){
        ArrayList<Aliments> alimentsItems =new ArrayList<>();
        alimentsItems =mDataset;
        return alimentsItems;
    }
    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mItem = mDataset.get(position);
        holder.mTitleView.setText(mDataset.get(position).getStrIngredient());
        holder.selectedView.setChecked(mDataset.get(position).isSelected());
        holder.selectedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    holder.mItem.setSelected(true);
                }
                else{
                    holder.mItem.setSelected(false);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(ArrayList<Aliments> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }



    public ArrayList<Aliments> getSelected(){

        //Lista alimentos seleccionados
        ArrayList<Aliments> listaSeleccionados = new ArrayList<Aliments>();
        for (int i=0; i< mDataset.size(); i++) {
            Aliments p;
            p = mDataset.get(i);
            if(p.isSelected()){
                listaSeleccionados.add(p);
                //log("Lista seleccionada " + listaSeleccionados.get(i).getStrIngredient() );

            }

        }

        return listaSeleccionados;

    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }


}
