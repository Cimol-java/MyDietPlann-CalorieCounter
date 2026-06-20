package com.mydietplan.auth.bizz;

import com.mydietplan.auth.User;

public interface IAuthBizz {
    boolean register(String email, String password);

    User login(String email, String password);

    void logout();
}
