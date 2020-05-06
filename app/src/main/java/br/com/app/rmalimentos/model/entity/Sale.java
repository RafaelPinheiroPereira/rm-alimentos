package br.com.app.rmalimentos.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = "sales")
public class Sale implements Serializable {

  @PrimaryKey
  @NonNull
  private Long id;

  @ColumnInfo(name = "client_id")
  private long clientId;

  @ColumnInfo(name = "amount")
  private double amount;

  @ColumnInfo(name = "payment_description")
  private double paymentDescription;

  @ColumnInfo(name = "date")
  private Date date;

  @ColumnInfo(name = "payment_id")
  private int paymentId;

  List<SaleItem> itens;
}
