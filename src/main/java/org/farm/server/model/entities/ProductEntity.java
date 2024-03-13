package org.farm.server.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;

@Table(name = "product")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date producedDate;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    @JsonIdentityReference(alwaysAsId = true)
    private FarmerEntity producedBy;

    @ManyToOne()
    @JoinColumn(name = "product_type_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private ProductTypeEntity productType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public ProductTypeEntity getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeEntity productType) {
        this.productType = productType;
    }

    public FarmerEntity getProducedBy() {
        return producedBy;
    }

    public void setProducedBy(FarmerEntity producedBy) {
        this.producedBy = producedBy;
    }
}
