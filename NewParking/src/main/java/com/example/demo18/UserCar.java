package com.example.demo18;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class UserCar {
    private String userName;
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty carName;
    private final StringProperty carNumber;
    private final StringProperty dateFrom;
    private final StringProperty dateTo;
    private final StringProperty parkingPlace;
    private final ObjectProperty<CarModel> carModel;

    public UserCar() {
        id = new SimpleStringProperty(this, "id");
        name = new SimpleStringProperty(this, "name");
        carName = new SimpleStringProperty(this, "carName");
        carNumber = new SimpleStringProperty(this, "carNumber");
        dateFrom = new SimpleStringProperty(this, "dateFrom");
        dateTo = new SimpleStringProperty(this, "dateTo");
        parkingPlace = new SimpleStringProperty(this, "parkingPlace");
        carModel = new SimpleObjectProperty<>(this, "carModel");
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String newId) {
        id.set(newId);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        name.set(newName);
    }

    public StringProperty carNameProperty() {
        return carName;
    }

    public String getCarName() {
        return carName.get();
    }

    public void setCarName(String newCarName) {
        carName.set(newCarName);
    }

    public StringProperty carNumberProperty() {
        return carNumber;
    }

    public String getCarNumber() {
        return carNumber.get();
    }

    public void setCarNumber(String newCarNumber) {
        carNumber.set(newCarNumber);
    }

    public StringProperty dateFromProperty() {
        return dateFrom;
    }

    public String getDateFrom() {
        return dateFrom.get();
    }

    public Date getDateFromAsDate() {
        return Date.valueOf(dateFrom.get());
    }

    public void setDateFrom(String newDateFrom) {
        dateFrom.set(newDateFrom);
    }

    public void setDateFromAsDate(Date newDateFrom) {
        dateFrom.set(newDateFrom.toLocalDate().toString());
    }

    public StringProperty dateToProperty() {
        return dateTo;
    }

    public String getDateTo() {
        return dateTo.get();
    }

    public Date getDateToAsDate() {
        return Date.valueOf(dateTo.get());
    }

    public void setDateTo(String newDateTo) {
        dateTo.set(newDateTo);
    }

    public void setDateToAsDate(Date newDateTo) {
        dateTo.set(newDateTo.toLocalDate().toString());
    }

    public StringProperty parkingPlaceProperty() {
        return parkingPlace;
    }

    public String getParkingPlace() {
        return parkingPlace.get();
    }

    public void setParkingPlace(String newParkingPlace) {
        parkingPlace.set(newParkingPlace);
    }

    public ObjectProperty<CarModel> carModelProperty() {
        return carModel;
    }

    public CarModel getCarModel() {
        return carModel.get();
    }

    public void setCarModel(CarModel newCarModel) {
        carModel.set(newCarModel);
    }

    @Override
    public String toString() {
        return String.format("%s{id=%s, name=%s, carName=%s, carNumber=%s, dateFrom=%s, dateTo=%s, parkingPlace=%s, carModel=%s}",
                getId(), getName(), getCarName(), getCarNumber(), getDateFrom(), getDateTo(), getParkingPlace(), getCarModel());
    }
}
