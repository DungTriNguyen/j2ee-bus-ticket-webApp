package com.bus_station_ticket.project.ProjectEntity;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bus_routes_schedule", indexes = @Index(columnList = "schedule_id"))
public class BusRouteScheduleEntity {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "schedule_id")
       private Long scheduleId;

       @Column(name = "day_of_week", columnDefinition = "ENUM('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') ")
       private String dayOfWeek;

       @ManyToOne
       @JoinColumn(name = "bus_id", referencedColumnName = "bus_id", nullable = false)
       private BusEntity busEntity;

       @ManyToOne
       @JoinColumn(name = "routes_id", referencedColumnName = "routes_id", nullable = false)
       private BusRoutesEntity busRoutesEntity;

       @Column(name = "departure_time", columnDefinition = "TIME")
       private LocalTime departureTime;

       @Column(name = "is_delete", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
       private Boolean isDelete;

       @OneToMany(mappedBy = "busRouteSchedule", fetch = FetchType.LAZY)
       private List<TicketEntity> listTicketEntities;

       public BusRouteScheduleEntity() {
       }

       public BusRouteScheduleEntity(String dayOfWeek, BusEntity busEntity, BusRoutesEntity busRoutesEntity,
                     LocalTime departureTime, Boolean isDelete, List<TicketEntity> listTicketEntities) {
              this.dayOfWeek = dayOfWeek;
              this.busEntity = busEntity;
              this.busRoutesEntity = busRoutesEntity;
              this.departureTime = departureTime;
              this.isDelete = isDelete;
              this.listTicketEntities = listTicketEntities;
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

       public BusEntity getBusEntity() {
              return busEntity;
       }

       public void setBusEntity(BusEntity busEntity) {
              this.busEntity = busEntity;
       }

       public BusRoutesEntity getBusRoutesEntity() {
              return busRoutesEntity;
       }

       public void setBusRoutesEntity(BusRoutesEntity busRoutesEntity) {
              this.busRoutesEntity = busRoutesEntity;
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

       public List<TicketEntity> getListTicketEntities() {
              return listTicketEntities;
       }

       public void setListTicketEntities(List<TicketEntity> listTicketEntities) {
              this.listTicketEntities = listTicketEntities;
       }

}
