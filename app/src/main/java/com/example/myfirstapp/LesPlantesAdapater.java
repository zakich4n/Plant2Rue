package com.example.myfirstapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.object.Plante;
import com.example.myfirstapp.singleton_and_adapter.OnRecyclerItemClickListener;
import com.squareup.picasso.Picasso;


public class LesPlantesAdapater extends RecyclerView.Adapter<LesPlantesAdapater.ViewHolder> {

    public static OnRecyclerItemClickListener onRecyclerItemClickListener;

    public LesPlantesAdapater(OnRecyclerItemClickListener _onRecyclerItemClickListener){
        onRecyclerItemClickListener = _onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ma_nouvelle_plante0_line, parent, false);

        Log.d("debug", "onCreateViewHolder");
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPlante(LesPlantesSingleton.getInstance().listPlantes.get(position));

        Log.d("debug", "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        Log.d("debug", "getItemCount");
        return LesPlantesSingleton.getInstance().listPlantes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgview_cover;
        private TextView textview_nomPlante;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview_cover = itemView.findViewById(R.id.imageView_plante_cover);
            textview_nomPlante = itemView.findViewById(R.id.textView_nom_type_Plante);
            itemView.setOnClickListener(this);
            Log.d("debug", "ViewHolder");
        }

        public void setPlante( Plante plante){
            textview_nomPlante.setText(plante.nomType);
            Picasso.get().load(plante.cover).into(imgview_cover);
            Log.d("debug", "setPlante");

        }

        @Override
        public void onClick(View view) {
            onRecyclerItemClickListener.OnRecyclerItemClickListener_function(view,getAdapterPosition());
        }
    }
}
