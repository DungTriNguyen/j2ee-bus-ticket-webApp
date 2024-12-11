package com.bus_station_ticket.project.ProjectEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "bus_routes", indexes = @Index(columnList = "routes_id"))
public class BusRoutesEntity {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "routes_id")
       private Long routesId;

       @Column(name = "departure_location", columnDefinition = "VARCHAR(50)")
       private String departureLocation;

       @Column(name = "destination_location", columnDefinition = "VARCHAR(50)")
       private String destinationLocation;

       @Column(name = "distance_location", columnDefinition = "FLOAT", length = 5)
       private float distanceKilometer;

       @Column(name = "trip_time", columnDefinition = "TIME")
       private LocalTime tripTime;

       @Column(name = "price", columnDefinition = "FLOAT", length = 5)
       private float price;

       @Column(name = "is_delete", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
       private Boolean isDelete;

       // @OneToMany(mappedBy = "busRoutesEntity", fetch = FetchType.LAZY)
       // private List<TicketEntity> listTicketEntities;

       @OneToMany(mappedBy = "busRoutesEntity", fetch = FetchType.LAZY)
       private List<BusRouteScheduleEntity> listBusRouteSchedules;

       public BusRoutesEntity(String departureLocation, String destinationLocation, float distanceKilometer,
                     LocalTime tripTime, float price, Boolean isDelete, List<BusRouteScheduleEntity> listBusRouteSchedules) {
              this.departureLocation = departureLocation;
              this.destinationLocation = destinationLocation;
              this.distanceKilometer = distanceKilometer;
              this.tripTime = tripTime;
              this.price = price;
              this.isDelete = isDelete;
              this.listBusRouteSchedules = listBusRouteSchedules;
       }

       public BusRoutesEntity() {
       }

       public Long getRoutesId() {
              return routesId;
       }

       public void setRoutesId(Long routesId) {
              this.routesId = routesId;
       }

       public String getDepartureLocation() {
              return departureLocation;
       }

       public void setDepartureLocation(String departureLocation) {
              this.departureLocation = departureLocation;
       }

       public String getDestinationLocation() {
              return destinationLocation;
       }

       public void setDestinationLocation(String destinationLocation) {
              this.destinationLocation = destinationLocation;
       }

       public float getDistanceKilometer() {
              return distanceKilometer;
       }

       public void setDistanceKilometer(float distanceKilometer) {
              this.distanceKilometer = distanceKilometer;
       }

       public float getPrice() {
              return price;
       }

       public void setPrice(float price) {
              this.price = price;
       }

       public Boolean getIsDelete() {
              return isDelete;
       }

       public void setIsDelete(Boolean isDelete) {
              this.isDelete = isDelete;
       }

       public LocalTime getTripTime() {
              return tripTime;
       }

       public void setTripTime(LocalTime tripTime) {
              this.tripTime = tripTime;
       }

       public List<BusRouteScheduleEntity> getListBusRouteSchedules() {
              return listBusRouteSchedules;
       }

       public void setListBusRouteSchedules(List<BusRouteScheduleEntity> listBusRouteSchedules) {
              this.listBusRouteSchedules = listBusRouteSchedules;
       }

}
