package com.github.jamie9504.board.role;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jamie9504.board.common.BaseAcceptanceTest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoleAcceptanceTest extends BaseAcceptanceTest {

    /* Feature : 롤 관리
     *
     * Scenario : 롤을 관리한다.
     *
     * When 롤을 등록한다. Then 롤이 등록된다.
     *
     * When 롤을 조회한다. Then 롤이 조회된다.
     *
     * When 롤을 수정한다. Then 롤이 수정된다.
     *
     * Given 롤을 등록한다. When 롤을 모두 조회한다. Then 롤이 모두 조회된다.
     *
     * When 롤을 삭제한다. Then 롤이 삭제된다.
     */
    @DisplayName("롤 관리 인수테스트")
    @Test
    void manageRule() {
        Map<String, String> createdLocations = new HashMap<>();

        // 등록
        String nameA = "A";
        String explanationA = "It's A";
        createdLocations.put(nameA, createRole(nameA, explanationA));

        // 조회
        RoleResponse findRole = find(createdLocations.get(nameA), RoleResponse.class);
        assertThat(findRole.getName()).isEqualTo(nameA);
        assertThat(findRole.getExplanation()).isEqualTo(explanationA);

        // 수정
        String nameC = "C";
        String explanationC = "It's C";
        String locationA = createdLocations.remove(nameA);
        createdLocations.put(nameC, locationA);
        updateRole(locationA, nameC, explanationC);

        RoleResponse modifiedRole = find(createdLocations.get(nameC), RoleResponse.class);
        assertThat(modifiedRole.getName()).isEqualTo(nameC);
        assertThat(modifiedRole.getExplanation()).isEqualTo(explanationC);

        // 목록 조회
        String nameB = "B";
        String explanationB = "It's B";
        createdLocations.put(nameB, createRole(nameB, explanationB));

        List<RoleResponse> roles = findAll("/roles", RoleResponse.class);
        assertThat(roles).hasSize(createdLocations.size());

        // 삭제
        for (String location : createdLocations.values()) {
            delete(location);
        }
    }

    private void updateRole(String location, String name, String explanation) {
        Map<String, String> params = makeParams(name, explanation);
        update(params, location);
    }

    private Map<String, String> makeParams(String name, String explanation) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("explanation", explanation);
        return params;
    }

    private String createRole(String name, String explanation) {
        Map<String, String> params = makeParams(name, explanation);
        return create(params, "/roles");
    }
}
