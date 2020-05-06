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
@Entity(tableName = "products")
public class Product implements Serializable {

    @PrimaryKey
    @NonNull
    private Long id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "promotion")
    private String promotion;

    @ColumnInfo(name = "stock_quantity")
    private int stockQuantity;

    @ColumnInfo(name = "system_date")
    private String systemDate;

    @ColumnInfo(name = "validity")
    private String validity;

    @ColumnInfo(name = "differentiated_weight")
    private String differentiatedWeight;

    @ColumnInfo(name = "standard_unit")
    private String standardUnit;

    @ColumnInfo(name = " unit_quantity")
    private String unitQuantity;
}
