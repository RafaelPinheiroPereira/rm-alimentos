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
@Entity(tableName = "sale_items")
public class SaleItem implements Serializable {

    @PrimaryKey
    @NonNull
    private Long id;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "product_id")
    private long productId;

    @ColumnInfo(name = "unity_code")
    private String unityCode;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "value")
    private Double value;


}
