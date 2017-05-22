package by.eventcat;

/**
 * Class for holding results of category events counting
 */
public class CategoryWithCount {
    private Category category;
    private int countEventsOfCategory;

    public CategoryWithCount(Category category, int countEventsOfCategory) {
        this.category = category;
        this.countEventsOfCategory = countEventsOfCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCountEventsOfCategory() {
        return countEventsOfCategory;
    }

    public void setCountEventsOfCategory(int countEventsOfCategory) {
        this.countEventsOfCategory = countEventsOfCategory;
    }

    @Override
    public String toString() {
        return "CategoryWithCount{" +
                "category=" + category +
                ", countEventsOfCategory=" + countEventsOfCategory +
                '}';
    }
}
