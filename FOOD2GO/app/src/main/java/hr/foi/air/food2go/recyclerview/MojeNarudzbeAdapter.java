package hr.foi.air.food2go.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.ButterKnife;
import hr.foi.air.core.Racun;
import hr.foi.air.food2go.R;

public class MojeNarudzbeAdapter extends RecyclerView.Adapter<MojeNarudzbeAdapter.ViewHolder> {
    private ArrayList<Racun> racuni;
    public Context context;
    private OnItemClickListener onItemClickListener;

    public MojeNarudzbeAdapter(Context context, OnItemClickListener onItemClickListener, ArrayList<Racun> racuni){
        this.context = context;
        this.racuni = racuni;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_moje_narudzbe_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.brojRacuna.setText(racuni.get(position).getBrojRacuna());
        DecimalFormat df = new DecimalFormat("0.00");
        holder.cijena.setText(df.format(racuni.get(position).getUkupno()).replace('.', ',') + " kn");
        holder.datum.setText(racuni.get(position).getDatum().toString());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView brojRacuna;
        TextView datum;
        TextView cijena;

        RelativeLayout artiklItemLayout;

        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;

            ButterKnife.bind(this, itemView);

            brojRacuna = itemView.findViewById(R.id.uibrojRacuna);
            cijena = itemView.findViewById(R.id.uiartiklCijena);
            datum = itemView.findViewById(R.id.uiartiklDatum);
            artiklItemLayout = itemView.findViewById(R.id.mojaNarudzba_artikl_layout);

            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return racuni.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
