package model;

import com.mysql.jdbc.Blob;

import java.util.List;

/**
 * Created by incrit.com on 8/20/17.
 */
public class Offer {
    private Long id;
    private String offerDescription;
    private String offerName;
    private List<Category> categories;
    private String offerImageName;
    private Blob blob;

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

    public String getOfferImageName() {
        return offerImageName;
    }

    public void setOfferImageName(String offerImageName) {
        this.offerImageName = offerImageName;
    }

    public List<Category> getOfferCategories() {
        return categories;
    }

    public void setOfferCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Blob getOfferMainImage() {
        return blob;
    }

    public void setOfferMainImage(Blob blob) {
        this.blob = blob;
    }

    public String toString() {
        return "Offer - Id: " + id + ", Offer Title: " + offerName + ", Offer Description: " + offerDescription;
    }
}