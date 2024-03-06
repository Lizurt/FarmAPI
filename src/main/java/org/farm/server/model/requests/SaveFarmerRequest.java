package org.farm.server.model.requests;

import jakarta.validation.constraints.NotNull;

public class SaveFarmerRequest {
    @NotNull
    private String name;

    @NotNull
    private String surname;

    private String patronymic;

    @NotNull
    private Integer userId;

    public SaveFarmerRequest(@NotNull String name, @NotNull String surname, String patronymic, @NotNull Integer userId) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.userId = userId;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getSurname() {
        return surname;
    }

    public void setSurname(@NotNull String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public @NotNull Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull Integer userId) {
        this.userId = userId;
    }
}
