package br.com.app.rmalimentos.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Adress {

    private String description;

    private int routeId;

    private String neighborhood;

    private int postalCode;

    private String city;

    private String routeDescription;

    private int localityCode;

    private String referencePoint;
}
