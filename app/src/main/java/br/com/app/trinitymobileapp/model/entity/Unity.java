package br.com.app.trinitymobileapp.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Unity")
public class Unity implements Serializable {

  @ColumnInfo(name = "code")
  private String code;

  @PrimaryKey
  @NonNull
  private String id;

  @ColumnInfo(name = "multiple")
  private float multiple;

  @ColumnInfo(name = "product_id")
  private long productId;

  @ColumnInfo(name = "standard")
  private String standard;

  @ColumnInfo(name = "weight")
  private float weight;

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  public float getMultiple() {
    return multiple;
  }

  public void setMultiple(final float multiple) {
    this.multiple = multiple;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(final long productId) {
    this.productId = productId;
  }

  public String getStandard() {
    return standard;
  }

  public void setStandard(final String standard) {
    this.standard = standard;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(final float weight) {
    this.weight = weight;
  }

  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }
}
