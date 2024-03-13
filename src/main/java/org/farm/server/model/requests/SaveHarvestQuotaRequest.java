package org.farm.server.model.requests;

import java.util.Date;

public class SaveHarvestQuotaRequest {
    private Integer id;

    private Date startDate;

    private Date endDate;

    private Integer productTypeId;

    private Integer farmerId;

    private Double amount;

    public SaveHarvestQuotaRequest(
            Integer id,
            Date startDate,
            Date endDate,
            Integer productTypeId,
            Integer farmerId,
            Double amount
    ) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productTypeId = productTypeId;
        this.farmerId = farmerId;
        this.amount = amount;
    }

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

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
