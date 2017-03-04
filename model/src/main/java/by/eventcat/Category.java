package by.eventcat;

/**
 * Created by andrei on 4.3.17.
 */
public class Category {

    private int categoryId;
    private String categoryName;

    public Category (){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
