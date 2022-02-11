package com.example.vsos;

public class vehicleClass {
    private String RegdownerName;
    private String RegdNumber;
    private String Vehicle_make_Model;
    private String Regd_vehicleColor;
    private String Regd_vehicleYear;


    public vehicleClass() {
    }

    public vehicleClass(String RegdownerName, String RegdNumber, String Vehicle_make_Model, String Regd_vehicleColor,String Regd_vehicleYear) {
        this.RegdownerName = RegdownerName;
        this.RegdNumber = RegdNumber;
        this.Vehicle_make_Model = Vehicle_make_Model;
        this.Regd_vehicleColor = Regd_vehicleColor;
        this.Regd_vehicleYear = Regd_vehicleYear;
    }

    public String getRegdownerName() { return RegdownerName; }
    public void setRegdownerName(String regdownerName) { RegdownerName = regdownerName; }


    public String getRegdNumber() { return RegdNumber; }
    public void setRegdNumber(String regdNumber) { RegdNumber = regdNumber; }


    public String getVehicle_make_Model() { return Vehicle_make_Model; }
    public void setVehicle_make_Model(String vehicle_make_Model) { Vehicle_make_Model = vehicle_make_Model; }


    public String getRegd_vehicleColor() { return Regd_vehicleColor; }
    public void setRegd_vehicleColor(String regd_vehicleColor) { Regd_vehicleColor = regd_vehicleColor; }


    public String getRegd_vehicleYear() { return Regd_vehicleYear; }
    public void setRegd_vehicleYear(String regd_vehicleYear) { Regd_vehicleYear = regd_vehicleYear; }
}
