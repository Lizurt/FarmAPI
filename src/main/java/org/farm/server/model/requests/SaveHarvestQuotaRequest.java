package org.farm.server.model.requests;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

public class SaveHarvestQuotaRequest {
    private Integer id;

    private Date startDate;

    private Date endDate;

    @NotNull
    private Integer productTypeId;

    @NotNull
    private Integer farmerId;

    @NotNull
    private Double amount;

    public SaveHarvestQuotaRequest(
            Integer id,
            Date startDate,
            Date endDate,
            @NotNull Integer productTypeId,
            @NotNull Integer farmerId,
            @NotNull Double amount
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

    public @NotNull Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(@NotNull Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public @NotNull Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(@NotNull Integer farmerId) {
        this.farmerId = farmerId;
    }

    public @NotNull Double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull Double amount) {
        this.amount = amount;
    }
}
