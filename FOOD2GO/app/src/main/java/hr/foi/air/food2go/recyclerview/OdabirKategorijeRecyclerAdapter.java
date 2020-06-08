package hr.foi.air.food2go.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import hr.foi.air.food2go.R;
import hr.foi.air.core.Artikl;
import hr.foi.air.food2go.fragmenti.odabir_kategorije.OdabirKategorije;
import hr.foi.air.food2go.fragmenti.odabir_potkategorije.OdabirPotkategorijeFragment;

public class OdabirKategorijeRecyclerAdapter extends RecyclerView.Adapter<OdabirKategorijeRecyclerAdapter.ViewHolder>{

    private OnItemClickListener onItemClickListener;
    private Context context;
    private ArrayList<Artikl> artikli;

    public OdabirKategorijeRecyclerAdapter(Context context, OnItemClickListener onItemClickListener, ArrayList<Artikl> artikli){
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.artikli = artikli;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.odabir_kategorije_artikl_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view, onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(artikli.get(position).getUrlSlike())
                .into(holder.slika);

        holder.naziv.setText(artikli.get(position).getNaziv());
        DecimalFormat df = new DecimalFormat("0.00");
        holder.cijena.setText(df.format(artikli.get(position).getCijena()).replace('.', ',') + " kn");

        /*
        holder.artiklItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Artikl artikl = artikli.get(position);
                OdabirKategorije.Artikl = artikl;
            }
        });

         */
    }

    @Override
    public int getItemCount() {
        return artikli.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView slika;
        TextView naziv;
        TextView cijena;
        RelativeLayout artiklItemLayout;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            slika = itemView.findViewById(R.id.artikl_thumbnail);
            naziv = itemView.findViewById(R.id.artikl_ime);
            cijena = itemView.findViewById(R.id.artikl_cijena);
            artiklItemLayout = itemView.findViewById(R.id.artikl_item_layout);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
