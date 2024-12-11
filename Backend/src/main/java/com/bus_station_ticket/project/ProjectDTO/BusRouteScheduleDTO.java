package com.bus_station_ticket.project.ProjectDTO;

import java.time.LocalTime;
import java.util.List;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BusRouteScheduleDTO {

       @NotNull(message = "Schedule ID cannot be null")
       private Long scheduleId;

       @Pattern(regexp = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)", message = "value must be 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday' or  'Sunday' ")
       private String dayOfWeek;

       @NotNull(message = "Bus ID cannot be null")
       private Long busEntity_Id;

       @NotNull(message = "Routes ID cannot be null")
       private Long busRoutesEntity_Id;

       @NotNull(message = "Departure Time cannot be null")
       private LocalTime departureTime;

       @NotNull(message = "Delete status cannot be null")
       private Boolean isDelete;

       @NotNull(message = "listTicketEntities_Id status cannot be null")
       private List<Long> listTicketEntities_Id;

       public BusRouteScheduleDTO() {
       }

       public BusRouteScheduleDTO(@NotNull(message = "Schedule ID cannot be null") Long scheduleId,
                     @Pattern(regexp = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)", message = "value must be 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday' or  'Sunday' ") String dayOfWeek,
                     @NotNull(message = "Bus ID cannot be null") Long busEntity_Id,
                     @NotNull(message = "Routes ID cannot be null") Long busRoutesEntity_Id,
                     @NotNull(message = "Departure Time cannot be null") LocalTime departureTime,
                     @NotNull(message = "Delete status cannot be null") Boolean isDelete,
                     @NotNull(message = "listTicketEntities_Id status cannot be null") List<Long> listTicketEntities_Id) {
              this.scheduleId = scheduleId;
              this.dayOfWeek = dayOfWeek;
              this.busEntity_Id = busEntity_Id;
              this.busRoutesEntity_Id = busRoutesEntity_Id;
              this.departureTime = departureTime;
              this.isDelete = isDelete;
              this.listTicketEntities_Id = listTicketEntities_Id;
       }

       public Long getScheduleId() {
              return scheduleId;
       }

       public void setScheduleId(Long scheduleId) {
              this.scheduleId = scheduleId;
       }

       public String getDayOfWeek() {
              return dayOfWeek;
       }

       public void setDayOfWeek(String dayOfWeek) {
              this.dayOfWeek = dayOfWeek;
       }

       public Long getBusEntity_Id() {
              return busEntity_Id;
       }

       public void setBusEntity_Id(Long busEntity_Id) {
              this.busEntity_Id = busEntity_Id;
       }

       public Long getBusRoutesEntity_Id() {
              return busRoutesEntity_Id;
       }

       public void setBusRoutesEntity_Id(Long busRoutesEntity_Id) {
              this.busRoutesEntity_Id = busRoutesEntity_Id;
       }

       public LocalTime getDepartureTime() {
              return departureTime;
       }

       public void setDepartureTime(LocalTime departureTime) {
              this.departureTime = departureTime;
       }

       public Boolean getIsDelete() {
              return isDelete;
       }

       public void setIsDelete(Boolean isDelete) {
              this.isDelete = isDelete;
       }

       public List<Long> getListTicketEntities_Id() {
              return listTicketEntities_Id;
       }

       public void setListTicketEntities_Id(List<Long> listTicketEntities_Id) {
              this.listTicketEntities_Id = listTicketEntities_Id;
       }

}
