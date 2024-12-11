package com.bus_station_ticket.project.ProjectDTO;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BusDTO {
       @NotNull(message = "Bus ID cannot be null")
       private Long busId;

       @NotBlank(message = "Bus number cannot be blank")
       @Pattern(regexp = "^[0-9]{2}[A-Z]{1}-[0-9]{4,5}$", message = "Bus number must be alphanumeric")
       private String busNumber;

       @Min(value = 5, message = "Capacity must be at least 5")
       private int capacity;

       @NotBlank(message = "Brand cannot be blank")
       @Size(max = 50, message = "Brand name must be less than 50 characters")
       private String brand;

       @NotNull(message = "Delete status cannot be null")
       private Boolean isDelete;

       @NotNull(message = "Employee entity IDs cannot be null")
       private List<Long> listEmployeeEntities_Id;

       @NotNull(message = "Bus Routes Schedules entity IDs cannot be null")
       private List<Long> listBusRouteSchedules_Id;

       @NotNull(message = "Penalty ticket entity IDs cannot be null")
       private List<Long> listPenaltyTicketEntities_Id;

       public BusDTO() {
       }

       public Long getBusId() {
              return busId;
       }

       public void setBusId(Long busId) {
              this.busId = busId;
       }

       public String getBusNumber() {
              return busNumber;
       }

       public void setBusNumber(String busNumber) {
              this.busNumber = busNumber;
       }

       public int getCapacity() {
              return capacity;
       }

       public void setCapacity(int capacity) {
              this.capacity = capacity;
       }

       public String getBrand() {
              return brand;
       }

       public void setBrand(String brand) {
              this.brand = brand;
       }

       public Boolean getIsDelete() {
              return isDelete;
       }

       public void setIsDelete(Boolean isDelete) {
              this.isDelete = isDelete;
       }

       public List<Long> getListEmployeeEntities_Id() {
              return listEmployeeEntities_Id;
       }

       public void setListEmployeeEntities_Id(List<Long> listEmployeeEntities_Id) {
              this.listEmployeeEntities_Id = listEmployeeEntities_Id;
       }

       public List<Long> getListPenaltyTicketEntities_Id() {
              return listPenaltyTicketEntities_Id;
       }

       public void setListPenaltyTicketEntities_Id(List<Long> listPenaltyTicketEntities_Id) {
              this.listPenaltyTicketEntities_Id = listPenaltyTicketEntities_Id;
       }

       public List<Long> getListBusRouteSchedules_Id() {
              return listBusRouteSchedules_Id;
       }

       public void setListBusRouteSchedules_Id(List<Long> listBusRouteSchedules_Id) {
              this.listBusRouteSchedules_Id = listBusRouteSchedules_Id;
       }

}
