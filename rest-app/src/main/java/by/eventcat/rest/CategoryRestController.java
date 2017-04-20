package by.eventcat.rest;

import by.eventcat.Category;
import by.eventcat.CategoryService;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryRestController
 */
@RestController
public class CategoryRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    //curl -v localhost:8080/categories
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> getCategories() {
        LOGGER.debug(" getCategories()");
        try{
            return categoryService.getAllCategories();
        } catch (ServiceException ex){
            return null;
        }
    }

    //curl -v localhost:8080/category/{categoryId}
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public @ResponseBody
    Category getCategoryById(@PathVariable(value = "categoryId") int categoryId) {
        LOGGER.debug("getCategoryById id={}", categoryId);
        try{
            return categoryService.getCategoryById(categoryId);
        } catch (ServiceException ex){
            return null;
        }
    }



}
