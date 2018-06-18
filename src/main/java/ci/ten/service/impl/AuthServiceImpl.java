package ci.ten.service.impl;

import ci.ten.common.Pageable;
import ci.ten.common.Pagination;
import ci.ten.common.PaginationHelper;
import ci.ten.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Pagination getUserAuthPage(Pageable pageable) {
        String sql = "SELECT" +
                " user_role.id,user_role.auth_page,t_user.username,user_role.role " +
                " FROM" +
                " user_role" +
                " LEFT JOIN t_user ON t_user.`code` = user_role.user_code " +
                " WHERE" +
                " user_role.deleted = FALSE";
        PaginationHelper<Map> page = new PaginationHelper<Map>();
        Pagination pagination = page.selectMapPage(jdbcTemplate, sql, pageable.getPage(), pageable.getLimit());
        return pagination;
    }

}
