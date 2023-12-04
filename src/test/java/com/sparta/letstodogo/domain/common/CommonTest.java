package com.sparta.letstodogo.domain.common;

import com.sparta.letstodogo.user.*;

public interface CommonTest {

    Long TEST_USER_ID1 = 1L;
    Long TEST_USER_ID2 = 2L;
    String TEST_USER_NAME1 = "Elsa";
    String TEST_USER_NAME2 = "SamPorter";
    String TEST_USER_PASSWORD1 = "DoYouWannaBuildASnowman";
    String TEST_USER_PASSWORD2 = "ReadyToDeliverAPackage";

    User TEST_USER1 = User.builder()
        .username(TEST_USER_NAME1)
        .password(TEST_USER_PASSWORD1)
        .build();
    User TEST_USER2 = User.builder()
        .username(TEST_USER_NAME2)
        .password(TEST_USER_PASSWORD2)
        .build();

}
