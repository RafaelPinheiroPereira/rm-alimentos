package br.com.app.rmalimentos.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.app.rmalimentos.R;
import br.com.app.rmalimentos.model.entity.Client;
import br.com.app.rmalimentos.model.entity.Payment;
import br.com.app.rmalimentos.model.entity.Price;
import br.com.app.rmalimentos.model.entity.Product;
import br.com.app.rmalimentos.model.entity.Sale;
import br.com.app.rmalimentos.model.entity.SaleItem;
import br.com.app.rmalimentos.model.entity.Unity;
import br.com.app.rmalimentos.tasks.InsertSaleItensTask;
import br.com.app.rmalimentos.tasks.UpdateSaleItensTask;
import br.com.app.rmalimentos.utils.CurrencyEditText;
import br.com.app.rmalimentos.utils.MonetaryFormatting;
import br.com.app.rmalimentos.utils.Singleton;
import br.com.app.rmalimentos.view.adapter.SaleItemAdapter;
import br.com.app.rmalimentos.view.dialog.EditSaleItemDialog;
import br.com.app.rmalimentos.viewmodel.SaleViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class SaleActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.txt_cliente_id)
  TextView txtClientId;

  @BindView(R.id.txt_fantasy_name)
  TextView txtFantasyName;

  @BindView(R.id.txt_cnpj)
  TextView txtCNPJ;

  @BindView(R.id.txt_open_note)
  TextView txtOpenNote;

  @BindView(R.id.txt_city)
  TextView txtCity;

  @BindView(R.id.txt_adress)
  TextView txtAdress;

  @BindView(R.id.spn_payment)
  Spinner spnPayment;

  @BindView(R.id.spn_unities)
  Spinner spnUnities;

  @BindView(R.id.rcv_sale_item)
  RecyclerView rcvSaleItem;

  @BindView(R.id.act_product)
  AutoCompleteTextView actProductId;

  @BindView(R.id.edt_quantity)
  EditText edtQtd;

  @BindView(R.id.cet_price)
  CurrencyEditText cetPrice;

  @BindView(R.id.fb_save_sale)
  FloatingActionButton fbSaveSale;

  @BindView(R.id.txt_total_value_product)
  TextView txtTotalValueProduct;

  SaleViewModel saleViewModel;

  ArrayAdapter paymentsAdapter;
  ArrayAdapter productsAdapter;
  ArrayAdapter unitiesAdapter;
  SaleItemAdapter saleItemAdapter;

  AbstractActivity abstractActivity;

  Paint paint = new Paint();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sale);
    ButterKnife.bind(this);
    initViews();
    saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
  }

  @Override
  protected void onStart() {
    super.onStart();
    try {
      abstractActivity = Singleton.getInstance(AbstractActivity.class);
      this.saleViewModel.setContext(this);

    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    this.getParentActivityData();
    this.setClientData();
    try {
      this.setAdapters();
      this.initSwipe();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      this.checkInitialConfigure();

    } catch (ParseException e) {
      abstractActivity.showErrorMessage(this, e.getMessage());
    }
    this.actProductId.setOnItemClickListener(
        (parent, view, position, id) -> {
          loadUnitiesByProduct(position);
        });

    this.spnUnities.setOnItemSelectedListener(
        new OnItemSelectedListener() {
          @Override
          public void onItemSelected(
              final AdapterView<?> parent, final View view, final int position, final long id) {

            saleViewModel.setUnitySelected((Unity) parent.getAdapter().getItem(position));
            saleViewModel
                .loadPriceByUnitAndProduct()
                .observe(
                    SaleActivity.this,
                    price -> {
                      saleViewModel.setPriceProductSelected(price);
                      configurePriceByUnityAndProduct();
                    });
          }

          @Override
          public void onNothingSelected(final AdapterView<?> parent) {}
        });

    this.spnPayment.setOnItemSelectedListener(
        new OnItemSelectedListener() {
          @Override
          public void onItemSelected(
              final AdapterView<?> parent, final View view, final int position, final long id) {
            saleViewModel.setPaymentSelected((Payment) parent.getAdapter().getItem(position));
          }

          @Override
          public void onNothingSelected(final AdapterView<?> parent) {}
        });

    edtQtd.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(
              final CharSequence s, final int start, final int count, final int after) {}

          @Override
          public void onTextChanged(
              final CharSequence s, final int start, final int before, final int count) {}

          @Override
          public void afterTextChanged(final Editable s) {
            updateTotalProductValue();
          }
        });

    cetPrice.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(
              final CharSequence s, final int start, final int count, final int after) {}

          @Override
          public void onTextChanged(
              final CharSequence s, final int start, final int before, final int count) {}

          @Override
          public void afterTextChanged(final Editable s) {
            updateTotalProductValue();
          }
        });
  }

  /*Inicializa e configura as acoes do swipe recycle view*/
  private void initSwipe() {

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
        new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

          @Override
          public void onChildDraw(
              Canvas c,
              RecyclerView recyclerView,
              RecyclerView.ViewHolder viewHolder,
              float dX,
              float dY,
              int actionState,
              boolean isCurrentlyActive) {

            Bitmap icon;
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

              View itemView = viewHolder.itemView;
              float height = (float) itemView.getBottom() - (float) itemView.getTop();
              float width = height / 3;

              if (dX > 0) {
                paint.setColor(Color.parseColor("#388E3C"));
                RectF background =
                    new RectF(
                        (float) itemView.getLeft(),
                        (float) itemView.getTop(),
                        dX,
                        (float) itemView.getBottom());
                c.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_edit_white_48dp);
                RectF icon_dest =
                    new RectF(
                        (float) itemView.getLeft() + width,
                        (float) itemView.getTop() + width,
                        (float) itemView.getLeft() + 2 * width,
                        (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, paint);
              } else {
                paint.setColor(Color.parseColor("#D32F2F"));
                RectF background =
                    new RectF(
                        (float) itemView.getRight() + dX,
                        (float) itemView.getTop(),
                        (float) itemView.getRight(),
                        (float) itemView.getBottom());
                c.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_delete_white_48dp);
                RectF icon_dest =
                    new RectF(
                        (float) itemView.getRight() - 2 * width,
                        (float) itemView.getTop() + width,
                        (float) itemView.getRight() - width,
                        (float) itemView.getBottom() - width);
                c.drawBitmap(icon, null, icon_dest, paint);
              }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
          }

          @Override
          public boolean onMove(
              RecyclerView recyclerView,
              RecyclerView.ViewHolder viewHolder,
              RecyclerView.ViewHolder target) {
            return false;
          }

          @Override
          public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {


              SaleItem saleItemToRemove = saleViewModel.getSaleItems().get(position);
              saleViewModel.getSaleItems().remove(position);
              if (Optional.ofNullable(saleItemToRemove.getId()).isPresent()) {
                saleViewModel.deleteSaleItem(saleItemToRemove);
              } else {
                saleViewModel.getSale().setSaleItemList(saleViewModel.getSaleItems());
              }

            } else {

              this.showEditSaleItem(saleViewModel, position);
            }
            updateRecicleView();
          }

          private void showEditSaleItem(final SaleViewModel saleViewModel, final int position) {
            DialogFragment dialog =
                new EditSaleItemDialog(saleViewModel, position, saleItemAdapter);
            /*Se ainda nao foi instanciado*/
            if (!dialog.isAdded()) {
              dialog.show(getSupportFragmentManager(), "datePicker");
            }
            /*Caso ele já tenha sido instanciado eu removo, isto ocorre devida a baixa performace
             * do equipamento, uma vez que eh solicitado a exibicao do dialog o mesmo demora e o edit
             * text possibilita um segundo clique como primeiro ja instaciado */
            else {
              getSupportFragmentManager().beginTransaction().remove(dialog).commit();
            }
            updateRecicleView();
          }
        };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
    itemTouchHelper.attachToRecyclerView(rcvSaleItem);
  }

  /*Configura as views e objetos relacionados ao preco do produto*/
  private void configurePriceByUnityAndProduct() {
    cetPrice.setText(
        MonetaryFormatting.convertToDolar(saleViewModel.getPriceProductSelected().getValue()));
    saleViewModel.setProductQuantity(
        new BigDecimal(edtQtd.getText().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    txtTotalValueProduct.setText(
        MonetaryFormatting.convertToReal(saleViewModel.totalValueProduct().doubleValue()));
  }

  /*Carrega as unidades de acordo com o produto*/
  private void loadUnitiesByProduct(final int position) {
    spnUnities.setEnabled(true);
    cetPrice.setEnabled(true);
    this.saleViewModel.setProductSelected((Product) productsAdapter.getItem(position));
    saleViewModel
        .loadUnitiesByProduct(this.saleViewModel.getProductSelected())
        .observe(
            SaleActivity.this,
            unities -> {
              unitiesAdapter =
                  new ArrayAdapter(
                      getApplicationContext(), android.R.layout.simple_list_item_1, unities);
              spnUnities.setAdapter(unitiesAdapter);
            });
  }

  /*Configura os componentes para a criacao da venda*/
  private void configureCreate() {
    this.spnPayment.setSelection(0);
  }

  /*Configura os componentes para a atualizacao da venda*/
  private void configureUpdate() {
    this.saleViewModel
        .findSaleByDateAndClient()
        .observe(
            this,
            sale -> {
              this.saleViewModel.setSale(sale);
              this.saleViewModel
                  .getAllSaleItens()
                  .observe(
                      this,
                      saleItems -> {
                        this.saleViewModel.setSaleItems(saleItems);
                        saleItemAdapter = new SaleItemAdapter(this, this.saleViewModel);
                        rcvSaleItem.setAdapter(saleItemAdapter);
                      });
            });
  }

  private void checkInitialConfigure() throws ParseException {
    this.saleViewModel
        .searchSaleByDateAndClient()
        .observe(
            this,
            sale -> {
              Optional<Sale> optionalSale = Optional.ofNullable(sale);
              if (optionalSale.isPresent()) {
                this.configureUpdate();
              } else {
                this.configureCreate();
              }
            });
  }

  private void setAdapters() throws ExecutionException, InterruptedException {
    this.saleViewModel
        .loadAllPaymentsType()
        .observe(
            this,
            payments -> {
              paymentsAdapter =
                  new ArrayAdapter(
                      getApplicationContext(), android.R.layout.simple_list_item_1, payments);

              spnPayment.setAdapter(paymentsAdapter);
            });

    this.saleViewModel
        .loadAllProducts()
        .observe(
            this,
            products -> {
              productsAdapter =
                  new ArrayAdapter(
                      getApplicationContext(), android.R.layout.simple_list_item_1, products);

              actProductId.setThreshold(1); // will start working from first character
              actProductId.setAdapter(productsAdapter);
            });
    this.saleViewModel.setSaleItems(new ArrayList<>());
    saleItemAdapter = new SaleItemAdapter(this, this.saleViewModel);

    rcvSaleItem.setAdapter(saleItemAdapter);
  }

  /*Preeche os text view com os dados do cliente*/
  private void setClientData() {
    this.txtClientId.setText("COD. : " + this.saleViewModel.getClient().getId());
    this.txtCNPJ.setText("CNPJ: " + this.saleViewModel.getClient().getCNPJ());
    this.txtOpenNote.setText(
        "  Notas Abertas: "
            + MonetaryFormatting.convertToReal(this.saleViewModel.getClient().getTotalOpenValue()));
    this.txtFantasyName.setText(
        "  Nome Fantasia: " + this.saleViewModel.getClient().getFantasyName());
    this.txtCity.setText("Cidade: " + this.saleViewModel.getClient().getAdress().getCity());
    this.txtAdress.setText(
        " Endereço: "
            + this.saleViewModel.getClient().getAdress().getDescription()
            + ", "
            + this.saleViewModel.getClient().getAdress().getNeighborhood()
            + ", "
            + this.saleViewModel.getClient().getAdress().getReferencePoint());
  }

  /*Obtem os dados da HomeActivity*/
  private void getParentActivityData() {

    Bundle args = getIntent().getExtras();

    if (args != null) {
      this.saleViewModel.setClient((Client) args.getSerializable("keyClient"));
      this.saleViewModel.setDateSale(args.getString("keyDateSale"));
    }
  }

  /*Atualiza o valor total do produto e as views*/
  private void updateTotalProductValue() {
    if (Optional.ofNullable(saleViewModel.getPriceProductSelected()).isPresent()) {
      saleViewModel.getPriceProductSelected().setValue(cetPrice.getCurrencyDouble());
    } else {
      saleViewModel.setPriceProductSelected(new Price());
    }
    saleViewModel.setProductQuantity(
        new BigDecimal(
                edtQtd.getText().toString().isEmpty()
                    ? 0.0
                    : Double.valueOf(edtQtd.getText().toString()))
            .setScale(2, BigDecimal.ROUND_HALF_DOWN));
    txtTotalValueProduct.setText(
        MonetaryFormatting.convertToReal(saleViewModel.totalValueProduct().doubleValue()));
  }

  private void initViews() {
    toolbar.setTitle("Trinity Mobile - R&M Alimentos");
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    rcvSaleItem.setLayoutManager(linearLayoutManager);
    this.spnUnities.setEnabled(false);
    edtQtd.setText("1");
    cetPrice.setText("0.00");
    txtTotalValueProduct.setText(MonetaryFormatting.convertToReal(0.00));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    switch (id) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        break;
    }

    return super.onOptionsItemSelected(item);
  }

  @OnClick(R.id.btn_add)
  public void insertItem(View view) {

    if (this.isValidItem()) {
      SaleItem saleItem = this.getItemToInsert();
      if (this.saleViewModel.getSaleItems().stream()
              .filter(item -> item.getProductId() == saleItem.getProductId())
              .count()
          == 0) {
        this.saleViewModel.insertItem(saleItem);
        this.updateRecicleView();

      } else {
        abstractActivity.showMessage(this, "O item já existe na lista!");
      }

    } else {
      abstractActivity.showMessage(this, "Verifique os campos obrigatórios,por favor!");
    }
  }

  @OnClick(R.id.fb_save_sale)
  public void saveSale(View view) {

    if (this.saleViewModel.getSaleItems().size() > 0) {

      MaterialDialog mDialog =
          new MaterialDialog.Builder(this)
              .setTitle("Salvar Venda?")
              .setMessage("Você deseja realmente confirmar a venda?")
              .setCancelable(true)
              .setNegativeButton(
                  "Não",
                  R.mipmap.ic_clear_black_48dp,
                  (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                  })
              .setPositiveButton(
                  "Salvar",
                  R.mipmap.ic_save_white_48dp,
                  (dialogInterface, which) -> {
                    if (!this.isUpdate()) {

                      try {
                        this.saleViewModel.setSale(this.getSaleToInsert());
                      } catch (ParseException e) {
                        e.printStackTrace();
                      }

                      new InsertSaleItensTask(this.saleViewModel, this).execute();

                    } else {
                      this.configSaleToUpdate();
                      new UpdateSaleItensTask(this.saleViewModel, this).execute();
                    }

                    dialogInterface.dismiss();
                  })
              .build();

      mDialog.show();

    } else {
      abstractActivity.showMessage(this, "Itens não adicionados!");
    }
  }
  /*Prepara os dados da venda para a insercao*/
  private Sale getSaleToInsert() throws ParseException {

    Sale sale = new Sale();
    sale.setClientId(this.saleViewModel.getClient().getId());
    sale.setPaymentId(this.saleViewModel.getPaymentSelected().getId());
    sale.setPaymentDescription(this.saleViewModel.getPaymentSelected().getDescription());
    sale.setSaleItemList(this.saleViewModel.getSaleItems());
    sale.setAmount(this.saleViewModel.getAmount());
    sale.setDateSale(this.saleViewModel.getDateSale());

    return sale;
  }

  /*Prepara os dados da venda para a alteracao*/
  private void configSaleToUpdate() {

    this.saleViewModel.getSale().setPaymentId(this.saleViewModel.getPaymentSelected().getId());
    this.saleViewModel
        .getSale()
        .setPaymentDescription(this.saleViewModel.getPaymentSelected().getDescription());
    this.saleViewModel.getSale().setSaleItemList(this.saleViewModel.getSaleItems());
    this.saleViewModel.getSale().setAmount(this.saleViewModel.getAmount());
  }

  /*Verifica se eh uma edicao a venda*/
  private boolean isUpdate() {
    return Optional.ofNullable(this.saleViewModel.getSale()).isPresent();
  }

  /*Atualiza o recycle view*/
  private void updateRecicleView() {
    saleItemAdapter.notifyDataSetChanged();
  }

  /*Prepara a inserçao do item */
  private SaleItem getItemToInsert() {
    SaleItem saleItem = new SaleItem();
    saleItem.setProductId(this.saleViewModel.getProductSelected().getId());
    saleItem.setUnityCode(this.saleViewModel.getUnitySelected().getCode());
    saleItem.setDescription(this.saleViewModel.getProductSelected().getDescription());
    saleItem.setValue(this.saleViewModel.getPriceProductSelected().getValue());
    saleItem.setTotalValue(this.saleViewModel.totalValueProduct().doubleValue());
    saleItem.setQuantity(this.saleViewModel.getProductQuantity().intValue());

    return saleItem;
  }

  /** Realiza a validacao dos itens antes da insercao */
  private boolean isValidItem() {
    if (TextUtils.isEmpty(edtQtd.getText().toString())) {
      edtQtd.setError("Quantidade Obrigatória!");
      edtQtd.requestFocus();
      return false;
    }
    if (Double.valueOf(edtQtd.getText().toString()) <= 0) {
      edtQtd.setError("Quantidade mínima de 1 item!");
      edtQtd.requestFocus();
      return false;
    }

    if (!Optional.ofNullable(this.saleViewModel.getProductSelected()).isPresent()) {
      actProductId.setError("Quantidade mínima de 1 produto!");
      actProductId.requestFocus();
      return false;
    }

    return true;
  }
}
