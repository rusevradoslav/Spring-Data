package com.json_exercises.product_shop.models.dtos.seedData;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserSeedDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;

    public UserSeedDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    @NotNull(message = "Last name can not be null.")
    @Length(min = 3, message = "Last name must be at least 3 symbols.")
    public String getLastName() {
        return lastName;
    }


    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
