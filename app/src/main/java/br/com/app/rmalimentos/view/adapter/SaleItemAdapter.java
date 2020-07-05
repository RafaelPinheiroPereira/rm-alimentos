package br.com.app.rmalimentos.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.app.rmalimentos.R;
import br.com.app.rmalimentos.utils.MonetaryFormatting;
import br.com.app.rmalimentos.viewmodel.SaleViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleItemAdapter extends RecyclerView.Adapter<SaleItemAdapter.MyViewHolder> {

  private LayoutInflater mLayoutInflater;
  private SaleViewModel saleViewModel;

  public SaleItemAdapter(Context ctx, SaleViewModel saleViewModel) {
    this.saleViewModel = saleViewModel;
    this.mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public void onBindViewHolder(
      @NonNull final SaleItemAdapter.MyViewHolder holder, final int position) {

    holder.txtProductDescription.setText(String.format("%05d ",this.saleViewModel.getSaleItems().get(position).getId())+
        this.saleViewModel.getSaleItems().get(position).getDescription());
    holder.txtUnity.setText(
        this.saleViewModel.getSaleItems().get(position).getQuantity()
            + " x "
            + this.saleViewModel.getSaleItems().get(position).getUnityCode());
    holder.txtProductValue.setText(
        this.saleViewModel.getSaleItems().get(position).getQuantity()
            + " x "
            + MonetaryFormatting.convertToReal(
                this.saleViewModel.getSaleItems().get(position).getValue()));
    holder.txtTotalValue.setText(
        "TOTAL: "
            + MonetaryFormatting.convertToReal(
                this.saleViewModel.getSaleItems().get(position).getTotalValue()));

    holder.txtPosition.setText((position+1) + "/" + (this.getItemCount() ));
  }

  /**
   * Returns the total number of items in the data set held by the adapter.
   *
   * @return The total number of items in this adapter.
   */
  @Override
  public int getItemCount() {
    return this.saleViewModel.getSaleItems().size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_product_description)
    public TextView txtProductDescription;

    @BindView(R.id.txt_product_value)
    public TextView txtProductValue;

    @BindView(R.id.txt_unity)
    public TextView txtUnity;

    @BindView(R.id.txt_total_value)
    public TextView txtTotalValue;
      @BindView(R.id.txt_position)
      public TextView txtPosition;

    public MyViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  @NonNull
  @Override
  public SaleItemAdapter.MyViewHolder onCreateViewHolder(
      @NonNull final ViewGroup parent, final int viewType) {


    View v = mLayoutInflater.inflate(R.layout.item_sale_item, parent, false);
    SaleItemAdapter.MyViewHolder mvh = new SaleItemAdapter.MyViewHolder(v);
    return mvh;
  }
}
