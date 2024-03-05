package org.farm.server.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "product")
@Entity
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double amount;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date producedDate;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private FarmerEntity producedByFarmer;

    @ManyToOne()
    @JoinColumn(name = "product_type_id", nullable = false)
    private ProductTypeEntity productType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(Date producedDate) {
        this.producedDate = producedDate;
    }

    public FarmerEntity getProducedByFarmer() {
        return producedByFarmer;
    }

    public void setProducedByFarmer(FarmerEntity producedByFarmer) {
        this.producedByFarmer = producedByFarmer;
    }

    public ProductTypeEntity getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeEntity productType) {
        this.productType = productType;
    }
}
