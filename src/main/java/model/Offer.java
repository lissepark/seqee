package model;

import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public class Offer {
    private Long id;
    private String offerDescription;
    private String offerName;
    private List<Category> categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String toString() {
        return "Offer - Id: " + id + ", Offer Title: " + offerName + ", Offer Description: " + offerDescription;
    }
}