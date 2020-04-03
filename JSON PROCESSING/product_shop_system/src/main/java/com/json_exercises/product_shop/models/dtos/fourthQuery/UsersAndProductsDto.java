package com.json_exercises.product_shop.models.dtos.fourthQuery;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UsersAndProductsDto {

    @Expose
    private int usersCount;
    @Expose
    private List<UsersWithSoldProductsDto> users;

    public UsersAndProductsDto() {
    }

    public int getUsersCount() {
        return usersCount;
    }

    public List<UsersWithSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public void setUsers(List<UsersWithSoldProductsDto> users) {
        this.users = users;
    }
}
