package ci.ten.common;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class PaginationHelper<E> {

    public Pagination<E> fetchPage(
            final JdbcTemplate jt,
            final String sqlFetchRows,
            final Object args[],
            final int pageNo,
            final int pageSize,
            final RowMapper<E> rowMapper) {

        // determine how many rows are available
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sqlFetchRows);
        totalSQL.append(" ) totalTable ");
        final int rowCount = jt.queryForObject(totalSQL.toString(),Integer.class);

        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }

        // create the page object
        final Pagination<E> page = new Pagination<E>();
        page.setPage(pageNo);
        page.setPagesize(pageCount);
        page.setCount(rowCount);

        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        final String resultSql = totalSQL.append(" limit ").append(startRow).append(",").append(pageSize).toString();
        List<E> object = jt.query(
                resultSql,
                args,
                rowMapper);
        page.setPageItems(object);
        return page;
    }

    public Pagination<E> selectPage( final JdbcTemplate jt,
                              final String sqlFetchRows,
                              final int pageNo,
                              final int pageSize,
                              final RowMapper<E> rowMapper){
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sqlFetchRows);
        totalSQL.append(" ) totalTable ");
        final int rowCount = jt.queryForObject(totalSQL.toString(),Integer.class);

        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }

        // create the page object
        final Pagination<E> page = new Pagination<E>();
        page.setPage(pageNo);
        page.setPagesize(pageSize);
        page.setCount(rowCount);

        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        final String resultSql = totalSQL.append(" limit ").append(startRow).append(",").append(pageSize).toString();
        List<E> object = jt.query(
                resultSql,
                rowMapper);
        page.setPageItems(object);
        return page;
    }
    public Pagination<E> selectPageObject( final JdbcTemplate jt,
                              final String sqlFetchRows,
                              final int pageNo,
                              final int pageSize,BeanPropertyRowMapper beanPropertyRowMapper){
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sqlFetchRows);
        totalSQL.append(" ) totalTable ");
        final int rowCount = jt.queryForObject(totalSQL.toString(),Integer.class);

        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }

        // create the page object
        final Pagination<E> page = new Pagination<E>();
        page.setPage(pageNo);
        page.setPagesize(pageSize);
        page.setCount(rowCount);

        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        final String resultSql = new StringBuilder(sqlFetchRows).append(" limit ").append(startRow).append(",").append(pageSize).toString();
        List object = jt.query(resultSql,beanPropertyRowMapper);
        page.setPageItems(object);
        return page;
    }

    public Pagination<E> selectMapPage(final JdbcTemplate jt,
                                       final String sqlFetchRows,
                                       final int pageNo,
                                       final int pageSize){
        // determine how many rows are available
        StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
        totalSQL.append(sqlFetchRows);
        totalSQL.append(" ) totalTable ");
        final int rowCount = jt.queryForObject(totalSQL.toString(),Integer.class);

        // calculate the number of pages
        int pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }

        // create the page object
        final Pagination<E> page = new Pagination<>();
        page.setPage(pageNo);
        page.setPagesize(pageCount);
        page.setCount(rowCount);

        // fetch a single page of results
        final int startRow = (pageNo - 1) * pageSize;
        final String resultSql = new StringBuilder(sqlFetchRows).append(" limit ").append(startRow).append(",").append(pageSize).toString();
        List object = jt.queryForList(resultSql);
        page.setPageItems(object);
        return page;
    }




}