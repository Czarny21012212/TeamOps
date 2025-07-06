package com.example.Backend.Dto;

public class MembershipWithDepartmentAndCompany {
    private Long dep_id;
    private Long user_id;
    private String position;
    private boolean is_leader;

    public Long getDep_id() {
        return dep_id;
    }
    public void setDep_id(Long dep_id) {
        this.dep_id = dep_id;
    }

    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isIs_leader() {
        return is_leader;
    }
    public void setIs_leader(boolean is_leader) {
        this.is_leader = is_leader;
    }


}
