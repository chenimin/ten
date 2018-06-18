package ci.ten.service;

import ci.ten.common.Pageable;
import ci.ten.common.Pagination;

public interface AuthService {

    public Pagination getUserAuthPage(Pageable pageable);

}
