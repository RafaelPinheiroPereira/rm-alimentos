package br.com.app.rmalimentos.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "Sale")
public class Sale implements Serializable {


  @ColumnInfo(name = "amount")
  private double amount;

    @ColumnInfo(name = "client_id")
    private long clientId;

  @ColumnInfo(name = "date")
  private Date date;

    @PrimaryKey
    @NonNull
    private Long id;

    @ColumnInfo(name = "payment_description")
    private double paymentDescription;

  @ColumnInfo(name = "payment_id")
  private int paymentId;

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(final long clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull final Long id) {
        this.id = id;
    }

    public double getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(final double paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final int paymentId) {
        this.paymentId = paymentId;
    }
}
