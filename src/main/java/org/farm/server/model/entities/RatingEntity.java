package org.farm.server.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;

@Table(name = "rating")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RatingEntity {
    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;

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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public FarmerEntity getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerEntity farmer) {
        this.farmer = farmer;
    }
}
