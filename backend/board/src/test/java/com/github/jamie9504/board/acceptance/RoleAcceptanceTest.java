package com.github.jamie9504.board.acceptance;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoleAcceptanceTest extends BaseAcceptanceTest {


    /* Feature : 롤 관리
     *
     * Scenario : 롤을 관리한다.
     *
     * When 룰을 등록한다. Then 룰이 등록된다.
     *
     * When 룰을 조회한다. Then 룰이 조회된다.
     *
     * Given 룰을 등록한다. When 룰을 모두 조회한다. Then 룰이 모두 조회된다.
     *
     * When 룰을 삭제한다. Then 룰이 삭제된다.
     */
    @DisplayName("롤 관리 인수테스트")
    @Test
    void manageRule() {
//        Map<String, String> createdLocations = new HashMap<>();
//
//        // 등록
//        String nameA = "A";
//        createdLocations.put(nameA, createRole(nameA));
//
//        // 조회
//        RoleResponse findRole = find(createdLocations.get(nameA), RoleResponse.class);
//        assertThat(findRole.getRoleName()).isEqualTo(nameA);
//
//        // 목록 조회
//        String nameB = "B";
//        createdLocations.put(nameB, createRole(nameB));
//
//        List<RoleResponse> roles = findAll("/roles", RoleResponse.class);
//        assertThat(roles).hasSize(createdLocations.size());
//
//        // 삭제
//        for (String location : createdLocations.values()) {
//            delete(location);
//        }
    }

    private String createRole(String roleName) {
        Map<String, String> params = new HashMap<>();
        params.put("roleName", roleName);

        return create(params, "/roles");
    }

}
