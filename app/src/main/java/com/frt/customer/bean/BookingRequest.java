package com.frt.customer.bean;

import java.io.Serializable;

public class BookingRequest implements Serializable {

    String pickuplocation;
    String DropoffLocation;
    String StopLocation;
    Double StopLat;

    Boolean isStopPointSeted;

    public BookingRequest() {
    }

    Double StopLng;
    Double PickupLat;
    Double PickupLng;
    Double DropoffLat;
    Double DropoffLng;

    Double StopPointLat;
    Double StopPointLng;


    String carselectionid;

    public String getPickuplocation() {
        return pickuplocation;
    }

    public void setPickuplocation(String pickuplocation) {
        this.pickuplocation = pickuplocation;
    }

    public String getDropoffLocation() {
        return DropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        DropoffLocation = dropoffLocation;
    }

    public Double getPickupLat() {
        return PickupLat;
    }

    public void setPickupLat(Double pickupLat) {
        PickupLat = pickupLat;
    }

    public Double getPickupLng() {
        return PickupLng;
    }

    public void setPickupLng(Double pickupLng) {
        PickupLng = pickupLng;
    }

    public Double getDropoffLat() {
        return DropoffLat;
    }

    public void setDropoffLat(Double dropoffLat) {
        DropoffLat = dropoffLat;
    }

    public Double getDropoffLng() {
        return DropoffLng;
    }

    public void setDropoffLng(Double dropoffLng) {
        DropoffLng = dropoffLng;
    }

    public Boolean getIsStopPointSeted() {
        return isStopPointSeted;
    }

    public void setIsStopPointSeted(Boolean stopPointSeted) {
        isStopPointSeted = stopPointSeted;
    }

    public Double getStopPointLat() {
        return StopPointLat;
    }

    public void setStopPointLat(Double stopPointLat) {
        StopPointLat = stopPointLat;
    }

    public Double getStopPointLng() {
        return StopPointLng;
    }

    public void setStopPointLng(Double stopPointLng) {
        StopPointLng = stopPointLng;
    }

    public String getCarselectionid() {
        return carselectionid;
    }

    public void setCarselectionid(String carselectionid) {
        this.carselectionid = carselectionid;
    }

    public String getStopLocation() {
        return StopLocation;
    }

    public void setStopLocation(String stopLocation) {
        StopLocation = stopLocation;
    }

    /*public Double getStopLat() {
        return StopLat;
    }

    public void setStopLat(Double stopLat) {
        StopLat = stopLat;
    }

    public Double getStopLng() {
        return StopLng;
    }

    public void setStopLng(Double stopLng) {
        StopLng = stopLng;
    }*/
}
