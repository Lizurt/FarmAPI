package org.farm.server.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "harvest_quota")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HarvestQuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "harvest_quota_id")
    private Integer id;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private ProductTypeEntity productType;

    @Column(nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private FarmerEntity farmer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ProductTypeEntity getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeEntity productType) {
        this.productType = productType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public FarmerEntity getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerEntity farmer) {
        this.farmer = farmer;
    }
}
