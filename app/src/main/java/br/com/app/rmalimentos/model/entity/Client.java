package br.com.app.rmalimentos.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
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
@Entity(tableName = "clients")
public class Client implements Serializable {

    @PrimaryKey
    @NonNull
    private Long id;

    @ColumnInfo(name = "fantasy_name")
    private String fantasyName;

    @ColumnInfo(name = "social_name")
    private String razaoSocial;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "cpf")
    private String CPF;

    @ColumnInfo(name = "cnpj")
    private String CNPJ;

    @ColumnInfo(name = "contact")
    private String contact;

    @ColumnInfo(name = "observation")
    private String observation;

    @ColumnInfo(name = "rg")
    private String RG;

    @ColumnInfo(name = "open_note")
    private int openNote;

    @ColumnInfo(name = "bank_check_history")
    private String bankCheckHistory;

    @ColumnInfo(name = "bank_check_amount_returned")
    private Double bankCheckAmountReturned;

    @ColumnInfo(name = "average_purchase_value")
    private Double averagePurchaseValue;

    @ColumnInfo(name = "average_delay")
    private int averageDelay;

    @ColumnInfo(name = "date_last_purchase")
    private String dateLastPurchase;

    @ColumnInfo(name = "value_last_purchase")
    private Double valueLastPurchase;

    @ColumnInfo(name = "route_order")
    private int routeOrder;

    @ColumnInfo(name = "total_open_value")
    private Double totalOpenValue;

    @Embedded
    Adress adress;
}
