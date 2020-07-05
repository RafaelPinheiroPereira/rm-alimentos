package br.com.app.rmalimentos.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.app.rmalimentos.R;
import br.com.app.rmalimentos.listener.RecyclerViewOnClickListenerHack;
import br.com.app.rmalimentos.model.entity.Client;
import br.com.app.rmalimentos.view.adapter.HomeAdapter.MyViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

  private LayoutInflater mLayoutInflater;
  private List<Client> clientList;
  private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

  public HomeAdapter(Context ctx, List<Client> clients) {
    this.mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.clientList = clients;
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
    View v = mLayoutInflater.inflate(R.layout.item_recycle_home, parent, false);
    MyViewHolder mvh = new MyViewHolder(v);
    return mvh;
  }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

    holder.txtClientId.setText(String.format("%05d", clientList.get(position).getId()));
    holder.txtNameFantasy.setText(clientList.get(position).getFantasyName());
    String strAdress =
        clientList.get(position).getAdress().toString() != null
            ? clientList.get(position).getAdress().getDescription()
                + ", "
                + clientList.get(position).getAdress().getNeighborhood()
            : "";
    holder.txtAdress.setText(strAdress);
  }

  public Client getItem(int position) {
    return this.clientList.get(position);
  }

  @Override
  public int getItemCount() {
    return this.clientList.size();
  }

  @Override
  public Filter getFilter() {
    return null;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    @BindView(R.id.txt_adress)
    public TextView txtAdress;

    @BindView(R.id.txt_fantasy_name)
    public TextView txtNameFantasy;

    @BindView(R.id.txt_client_id)
    public TextView txtClientId;

    @BindView(R.id.img_info)
    Button imgInfo;

    @BindView(R.id.btn_sale)
    Button btnSale;

    public MyViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

      imgInfo.setOnClickListener(this);
      btnSale.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
      if (recyclerViewOnClickListenerHack != null) {
        recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
      }
    }
  }

  public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r) {
    recyclerViewOnClickListenerHack = r;
  }
}
