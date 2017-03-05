package by.eventcat;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for Category model
 */
public class CategoryTest {
    @Test
    public void getCategoryId() throws Exception {
        Category category = new Category();
        category.setCategoryId(12);
        assertEquals(category.getCategoryId(), 12);
    }

    @Test
    public void getCategoryName() throws Exception {
        Category category = new Category();
        category.setCategoryName("Театры");
        assertEquals(category.getCategoryName(), "Театры");
    }

}