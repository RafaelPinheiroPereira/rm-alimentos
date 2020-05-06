package br.com.app.rmalimentos.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
@Entity(
        tableName = "unitys",
        foreignKeys =
        @ForeignKey(entity = Product.class, parentColumns = "id", childColumns = "product_id"))
public class Unity implements Serializable {

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "code")
    private String code;

    @PrimaryKey
    @NonNull
    private Long id;

    @ColumnInfo(name = "multiple")
    private float multiple;

    @ColumnInfo(name = "standard")
    private String standard;

    @ColumnInfo(name = "weight")
    private float weight;
}
