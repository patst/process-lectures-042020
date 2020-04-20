package de.patst.process.model;

import java.io.Serializable;

public class CarContractDTO implements Serializable {

    private String id;
    private String carModel;
    private String customerEmail;
    private AddressDTO customerAddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public AddressDTO getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(AddressDTO customerAddress) {
        this.customerAddress = customerAddress;
    }
}
