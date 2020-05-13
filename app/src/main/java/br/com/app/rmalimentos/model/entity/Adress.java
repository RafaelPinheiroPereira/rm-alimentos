package br.com.app.rmalimentos.model.entity;

public class Adress {

    private String city;

    private String description;

    private int localityCode;

    private String neighborhood;

    private int postalCode;

    private String referencePoint;

    private String routeDescription;

    private int routeId;

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getLocalityCode() {
        return localityCode;
    }

    public void setLocalityCode(final int localityCode) {
        this.localityCode = localityCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(final String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final int postalCode) {
        this.postalCode = postalCode;
    }

    public String getReferencePoint() {
        return referencePoint;
    }

    public void setReferencePoint(final String referencePoint) {
        this.referencePoint = referencePoint;
    }

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(final String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(final int routeId) {
        this.routeId = routeId;
    }
}
