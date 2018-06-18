package ci.ten.config.jdbc.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@EnableConfigurationProperties({MasterDatasourceSettings.class,RRDatasourceSettings.class})
public class DataSourceConfig {
    /**
     * 增加只读数据源
     *
     * @param ds
     * @return
     * @throws SQLException
     */
    @Bean(name = "rrDataSource", destroyMethod = "close", initMethod = "init")
    public DataSource rrDataSource(RRDatasourceSettings ds) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource(false);
        if (StringUtils.isNotEmpty(ds.getId())) {
            druidDataSource.setName(ds.getId());
        }
        druidDataSource.setUsername(ds.getUsername());
        druidDataSource.setUrl(ds.getUrl());
        druidDataSource.setPassword(ds.getPassword());
        druidDataSource.setFilters(ds.getFilters());
        druidDataSource.setMaxActive(ds.getMaxActive());
        druidDataSource.setInitialSize(ds.getInitialSize());
        druidDataSource.setMaxWait(ds.getMaxWait());
        druidDataSource.setMinIdle(ds.getMinIdle());
        druidDataSource.setTimeBetweenEvictionRunsMillis(ds.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(ds.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(ds.getValidationQuery());
        druidDataSource.setTestWhileIdle(ds.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(ds.isTestOnBorrow());
        druidDataSource.setTestOnReturn(ds.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(ds.isPoolPreparedStatements());
        druidDataSource.setMaxOpenPreparedStatements(ds.getMaxOpenPreparedStatements());
        return druidDataSource;
    }

    /**
     * 只读数据源 jdbcTemplate bean
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate rrJdbcTemplate(
            @Qualifier("rrDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
