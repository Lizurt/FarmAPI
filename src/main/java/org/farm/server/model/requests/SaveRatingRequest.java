package org.farm.server.model.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class SaveRatingRequest {
    @NotNull
    private Double rating;

    private Date date;

    @NotNull
    private Integer farmerId;

    public SaveRatingRequest(@NotNull Double rating, Date date, @NotNull Integer farmerId) {
        this.rating = rating;
        this.date = date;
        this.farmerId = farmerId;
    }

    public @NotNull Double getRating() {
        return rating;
    }

    public void setRating(@NotNull Double rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public @NotNull Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(@NotNull Integer farmerId) {
        this.farmerId = farmerId;
    }
}
