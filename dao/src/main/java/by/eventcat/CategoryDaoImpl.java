package by.eventcat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.eventcat.TimeConverter.*;

/**
 * Category Dao Implementation
 */
public class CategoryDaoImpl implements CategoryDao{

    private static final Logger LOGGER = LogManager.getLogger();

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_NAME = "category_name";
    private static final String TOTAL_EVENTS = "total_events";

    @Value("${category.selectAll}")
    String getAllCategoriesSQL;

    @Value("${category.selectCategoryById}")
    String getCategoryByIdSql;

    @Value("${category.selectCategoryByName}")
    String getCategoryByNameSql;

    @Value("${category.insertCategory}")
    String insertCategorySql;

    @Value("${category.updateCategory}")
    String updateCategorySql;

    @Value("${category.deleteCategory}")
    String deleteCategorySql;

    @Value("${category.getCategoriesWithEventCount}")
    String getCategoriesWithEventCount;

    CategoryDaoImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Category> getAllCategories() throws DataAccessException {
        LOGGER.debug("getAllCategories()");
        return jdbcTemplate.query(getAllCategoriesSQL, new CategoryRowMapper());
    }

    @Override
    public Category getCategoryById(Integer categoryId) throws DataAccessException {
        LOGGER.debug("getCategoryById({})", categoryId);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_category_id", categoryId);
        return namedParameterJdbcTemplate.queryForObject(
                getCategoryByIdSql, namedParameters, new CategoryRowMapper());

    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) throws DataAccessException {
        LOGGER.debug("getCategoryByCategoryName({})", categoryName);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_category_name", categoryName);
        return namedParameterJdbcTemplate.queryForObject(
                getCategoryByNameSql, namedParameters, new CategoryRowMapper());
    }

    @Override
    public List<CategoryWithCount> getEventsCountForCertainTimeIntervalGroupByCategory
            (long beginOfInterval, long endOfInterval) throws DataAccessException {
        LOGGER.debug("getEventsCountForCertainTimeIntervalGroupByCategory()");
        return jdbcTemplate.query(getCategoriesWithEventCount, new String[]{convertTimeFromSecondsToString(beginOfInterval),
                convertTimeFromSecondsToString(endOfInterval)}, new CategoryWithCountRowMapper());
    }

    @Override
    public Integer addCategory(Category category) throws DataAccessException {
        LOGGER.debug("addCategory(category): name = {}", category.getCategoryName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(CATEGORY_NAME, category.getCategoryName());
        namedParameterJdbcTemplate.update(insertCategorySql, parameterSource, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateCategory(Category category) throws DataAccessException {
        LOGGER.debug("update category {}", category);
        Map<String, Object> params = new HashMap<>();
        params.put(CATEGORY_ID, category.getCategoryId());
        params.put(CATEGORY_NAME, category.getCategoryName());
        return namedParameterJdbcTemplate.update(updateCategorySql, params);
    }

    @Override
    public int deleteCategory(Integer categoryId) throws DataAccessException {
        LOGGER.debug("delete category with categoryId = {}", categoryId);
        Map<String, Object> params = new HashMap<>();
        params.put(CATEGORY_ID, categoryId);
        return namedParameterJdbcTemplate.update(deleteCategorySql, params);
    }

    private class CategoryRowMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Category(
                    resultSet.getInt(CATEGORY_ID),
                    resultSet.getString(CATEGORY_NAME));
        }
    }

    private class CategoryWithCountRowMapper implements RowMapper<CategoryWithCount> {
        @Override
        public CategoryWithCount mapRow(ResultSet resultSet, int i) throws SQLException {
            return new CategoryWithCount(
                    new Category(resultSet.getInt(CATEGORY_ID), resultSet.getString(CATEGORY_NAME)),
                    resultSet.getInt(TOTAL_EVENTS)
                    );
        }
    }
}
