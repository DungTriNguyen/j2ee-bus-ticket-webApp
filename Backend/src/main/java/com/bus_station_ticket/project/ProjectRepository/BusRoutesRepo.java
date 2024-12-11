package com.bus_station_ticket.project.ProjectRepository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bus_station_ticket.project.ProjectEntity.BusRoutesEntity;

@Repository
public interface BusRoutesRepo extends JpaRepository<BusRoutesEntity, Long> {

       // Truy suất theo thuộc tính
       public Optional<BusRoutesEntity> findByRoutesId(Long routesId);

       // public Optional<BusRoutesEntity> findByDepartureLocation(String departureLocation);

       // public Optional<BusRoutesEntity> findByDestinationLocation(String destinationLocation);

       public Optional<BusRoutesEntity> findByDepartureLocationAndDestinationLocation(String departureLocation,
                     String destinationLocation);

       // public List<BusRoutesEntity> findByDistanceKilometer(float distanceKilometer);

       // public List<BusRoutesEntity> findByDepartureTime(LocalDateTime departureTime);

       // public List<BusRoutesEntity> findByArivalTime(LocalDateTime arivalTime);

       // public List<BusRoutesEntity> findByPrice(float price);

       // public List<BusRoutesEntity> findByIsDelete(Boolean isDelete);

       // @Query(
       // value = """
       // select br.routes_id, br.departure_location, br.destination_location,
       // br.distance_location, br.departure_time, br.arival_time, br.price,
       // br.is_delete
       // from bus_routes br, ticket tk
       // where br.routes_id = tk.routes_id and tk.ticket_id = :ticketId
       // """,
       // nativeQuery = true
       // )
       // public Optional<BusRoutesEntity> findByTicketEntity_Id(@Param("ticketId")
       // Long ticketId);

       // @Query(value = """
       //                      select br.routes_id, br.departure_location, br.destination_location, br.distance_location, br.departure_time, br.arival_time, br.price, br.is_delete
       //                      from bus_routes br, bus b
       //                      where br.routes_id = b.routes_id and b.bus_id = :busId
       //               """, nativeQuery = true)
       // public Optional<BusRoutesEntity> findByBusEntity_Id(@Param("busId") Long busId);

       @Query(value = """
                            select br.routes_id, br.departure_location, br.destination_location, br.distance_location, br.trip_time, br.price, br.is_delete
                            from bus_routes br, bus_routes_schedule brs
                            where br.bus_routes = brs.bus_routes and brs.schedule_id = :scheduleId
                     """, nativeQuery = true)

       public Optional<BusRoutesEntity> findByBusRouteSchedules_Id(@Param("scheduleId") Long scheduleId);

}
