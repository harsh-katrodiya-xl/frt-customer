package com.frt.customer.bean;

public class BookJobModel {

//    String book_job_json = ConstantStore.book_job_json;
    String booking_type;

    String total_passenger, customer_name, customer_id, payment_type;
    String vehicle_type_id, vehicle_mobility_id, code, sharing_no, promo_code;
    String drop_off_location, pick_up_location;
    Double pick_up_location_latitude, pick_up_location_longitude;
    Double drop_off_location_latitude, drop_off_location_longitude;

    public BookJobModel() {
    }

    public BookJobModel(String booking_type, String total_passenger, String customer_name, String customer_id, String payment_type, String vehicle_type_id, String vehicle_mobility_id, String code, String sharing_no, String promo_code, String drop_off_location, String pick_up_location, Double pick_up_location_latitude, Double pick_up_location_longitude, Double drop_off_location_latitude, Double drop_off_location_longitude) {
        this.booking_type = booking_type;
        this.total_passenger = total_passenger;
        this.customer_name = customer_name;
        this.customer_id = customer_id;
        this.payment_type = payment_type;
        this.vehicle_type_id = vehicle_type_id;
        this.vehicle_mobility_id = vehicle_mobility_id;
        this.code = code;
        this.sharing_no = sharing_no;
        this.promo_code = promo_code;
        this.drop_off_location = drop_off_location;
        this.pick_up_location = pick_up_location;
        this.pick_up_location_latitude = pick_up_location_latitude;
        this.pick_up_location_longitude = pick_up_location_longitude;
        this.drop_off_location_latitude = drop_off_location_latitude;
        this.drop_off_location_longitude = drop_off_location_longitude;
    }

    public String getBooking_type() {
        return booking_type;
    }

    public void setBooking_type(String booking_type) {
        this.booking_type = booking_type;
    }

    public String getTotal_passenger() {
        return total_passenger;
    }

    public void setTotal_passenger(String total_passenger) {
        this.total_passenger = total_passenger;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(String vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getVehicle_mobility_id() {
        return vehicle_mobility_id;
    }

    public void setVehicle_mobility_id(String vehicle_mobility_id) {
        this.vehicle_mobility_id = vehicle_mobility_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSharing_no() {
        return sharing_no;
    }

    public void setSharing_no(String sharing_no) {
        this.sharing_no = sharing_no;
    }

    public String getPromo_code() {
        return promo_code;
    }

    public void setPromo_code(String promo_code) {
        this.promo_code = promo_code;
    }

    public String getDrop_off_location() {
        return drop_off_location;
    }

    public void setDrop_off_location(String drop_off_location) {
        this.drop_off_location = drop_off_location;
    }

    public String getPick_up_location() {
        return pick_up_location;
    }

    public void setPick_up_location(String pick_up_location) {
        this.pick_up_location = pick_up_location;
    }

    public Double getPick_up_location_latitude() {
        return pick_up_location_latitude;
    }

    public void setPick_up_location_latitude(Double pick_up_location_latitude) {
        this.pick_up_location_latitude = pick_up_location_latitude;
    }

    public Double getPick_up_location_longitude() {
        return pick_up_location_longitude;
    }

    public void setPick_up_location_longitude(Double pick_up_location_longitude) {
        this.pick_up_location_longitude = pick_up_location_longitude;
    }

    public Double getDrop_off_location_latitude() {
        return drop_off_location_latitude;
    }

    public void setDrop_off_location_latitude(Double drop_off_location_latitude) {
        this.drop_off_location_latitude = drop_off_location_latitude;
    }

    public Double getDrop_off_location_longitude() {
        return drop_off_location_longitude;
    }

    public void setDrop_off_location_longitude(Double drop_off_location_longitude) {
        this.drop_off_location_longitude = drop_off_location_longitude;
    }
}
