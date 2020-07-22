package com.github.jamie9504.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String explanation;

    protected Role() {
    }

    public Role(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }

    public void update(Role role) {
        this.name = role.name;
        this.explanation = role.explanation;
    }

    public String getName() {
        return name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Role{" +
            "name='" + name + '\'' +
            ", explanation='" + explanation + '\'' +
            '}';
    }
}
