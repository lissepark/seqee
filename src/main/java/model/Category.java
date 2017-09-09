package model;

/**
 * Created by incrit.com on 8/20/17.
 */
public class Category {
    private Long id;
    private String categoryDescription;
    private String categoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String toString() {
        return "Category - Id: "+ id + ", Category Name: " + categoryName + ", Category Description: " + categoryDescription;
    }
}