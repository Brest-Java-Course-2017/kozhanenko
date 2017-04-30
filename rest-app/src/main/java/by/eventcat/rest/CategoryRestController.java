package by.eventcat.rest;

import by.eventcat.Category;
import by.eventcat.CategoryService;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryRestController
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryRestController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CategoryService categoryService;

    //curl -v localhost:8090/categories
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

    //curl -v localhost:8090/category/1
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.FOUND)
    public @ResponseBody
    Category getCategoryById(@PathVariable(value = "categoryId") int categoryId) {
        LOGGER.debug("getCategoryById id={}", categoryId);
        try{
            return categoryService.getCategoryById(categoryId);
        } catch (ServiceException ex){
            return null;
        }
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"categoryName":"Дискотека"}' -v localhost:8090/category
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    int addUser(@RequestBody Category category) {
        LOGGER.debug("addCategory: category = {}", category);
        try{
            return categoryService.addCategory(category);
        } catch (ServiceException ex){
            return -99;
        }
    }

    //curl -X PUT -v localhost:8080/user/2/l1/p1/d1
    @RequestMapping(value = "/category/{categoryId}/{categoryName}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateUser(@PathVariable(value = "categoryId") int categoryId,
                           @PathVariable(value = "categoryName") String categoryName) {
        LOGGER.debug("updateCategory: id = {}", categoryId);
        try{
            categoryService.updateCategory(new Category(categoryId, categoryName));
        } catch (ServiceException ex){

        }
    }







}
