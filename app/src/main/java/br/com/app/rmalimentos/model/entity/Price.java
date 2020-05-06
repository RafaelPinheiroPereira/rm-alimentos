package br.com.app.rmalimentos.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(tableName = "prices")
public class Price implements Serializable {

    @PrimaryKey
    @NonNull
    private Long id;

    @ColumnInfo(name = "value")
    Double value;

    @ColumnInfo(name = "product_id")
    private long productId;

    @ColumnInfo(name = "payment_id")
    private long paymentId;

    @ColumnInfo(name = "unity_code")
    private String unityCode;
}
