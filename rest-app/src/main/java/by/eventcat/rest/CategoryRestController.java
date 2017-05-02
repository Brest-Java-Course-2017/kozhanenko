package by.eventcat.rest;

import by.eventcat.Category;
import by.eventcat.CategoryService;
import by.eventcat.custom.exceptions.CustomErrorCodes;
import by.eventcat.custom.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * CategoryRestController
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryRestController {

    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, Object> responseObject;

    @Autowired
    private CategoryService categoryService;

    @Value("${program_error}")
    private String programError;

    @Value("${data_not_found}")
    private String dataNotFound;

    @Value("${category_added}")
    private String categoryAdded;

    @Value("${bad_input_category_name}")
    private String badInputCategoryName;

    @Value("${duplicate_category_name}")
    private String duplicateCategoryName;

    @Value("${category_updated}")
    private String categoryUpdated;

    @Value("${no_actions_made}")
    private String noActionsMade;

    @Value("${category_deleted}")
    private String categoryDeleted;

    //curl -v localhost:8090/categories
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getCategories() {
        LOGGER.debug(" getCategories()");
        responseObject = new HashMap<>();

        try{
            responseObject.put("data", categoryService.getAllCategories());
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_CALLING_DATA_FOUND)){
                responseObject.put("errorMessage", dataNotFound);
            } else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }

    //curl -v localhost:8090/category/1
//    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.FOUND)
//    public @ResponseBody
//    Category getCategoryById(@PathVariable(value = "categoryId") int categoryId) {
//        LOGGER.debug("getCategoryById id={}", categoryId);
//        try{
//            return categoryService.getCategoryById(categoryId);
//        } catch (ServiceException ex){
//            return null;
//        }
//    }


    //curl -H "Content-Type: application/json" -X POST -d '{"categoryName":"Дискотека"}' -v localhost:8090/category
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody//returns new category id
    Map<String, Object> addCategory(@RequestBody Category category) {
        LOGGER.debug("addCategory: category = {}", category);
        responseObject = new HashMap<>();

        try{
            responseObject.put("data", categoryService.addCategory(category));
            responseObject.put("successMessage", categoryAdded);
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INPUT_DATA)){
                responseObject.put("errorMessage", badInputCategoryName);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED)){
                responseObject.put("errorMessage", duplicateCategoryName);
            } else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }

    //curl -X PUT -v localhost:8090/category/{categoryId}/{categoryName}
    @RequestMapping(value = "/category/{categoryId}/{categoryName}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Map<String, Object> updateCategory(@PathVariable(value = "categoryId") int categoryId,
                           @PathVariable(value = "categoryName") String categoryName) {
        LOGGER.debug("updateCategory: id = {}", categoryId);
        responseObject = new HashMap<>();

        try{
            categoryService.updateCategory(new Category(categoryId, categoryName));
            responseObject.put("successMessage", categoryUpdated);
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INDEX)){
                responseObject.put("errorMessage", programError);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INPUT_DATA)){
                responseObject.put("errorMessage", badInputCategoryName);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_DUPLICATE_DATA_PERMITTED)){
                responseObject.put("errorMessage", duplicateCategoryName);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE)){
                responseObject.put("errorMessage", noActionsMade);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.ACTIONS_ERROR)){
                responseObject.put("errorMessage", programError);
            } else {
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }

    //curl -X DELETE localhost:8090/category/{categoryId}
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Map<String, Object> deleteCategoryById(@PathVariable(value = "categoryId") int categoryId) {
        LOGGER.debug("deleteCategoryById id={}", categoryId);
        responseObject = new HashMap<>();

        try{
            categoryService.deleteCategory(categoryId);
            responseObject.put("successMessage", categoryDeleted);
        } catch (ServiceException ex){
            if (ex.getCustomErrorCode().equals(CustomErrorCodes.INCORRECT_INDEX)){
                responseObject.put("errorMessage", programError);
            } else if (ex.getCustomErrorCode().equals(CustomErrorCodes.NO_ACTIONS_MADE)){
                responseObject.put("errorMessage", noActionsMade);
            } else {
                //the same error from CustomErrorCodes.ACTIONS_ERROR
                responseObject.put("errorMessage", programError);
            }
        } catch (DataAccessException ex){
            responseObject.put("errorMessage", programError);
        }
        return responseObject;
    }



}
