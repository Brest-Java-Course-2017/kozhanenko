package by.eventcat;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 * Category (Topic) of event
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private Set<Event> events = new HashSet<>();

    public Category (){}

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category(int categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId &&
                Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
