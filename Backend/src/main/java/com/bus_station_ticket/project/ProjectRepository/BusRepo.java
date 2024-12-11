package com.bus_station_ticket.project.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bus_station_ticket.project.ProjectEntity.BusEntity;

@Repository
public interface BusRepo extends JpaRepository<BusEntity,Long> {
       
       // Truy suất theo thuộc tính
       public Optional<BusEntity> findByBusId (Long busId);
       public Optional<BusEntity> findByBusNumber (String busNumber);
       public List<BusEntity> findByCapacity (int capacity);
       public List<BusEntity> findByBrand (String brand);
       public List<BusEntity> findByIsDelete (Boolean isDelete);

       @Query(
              value = """
                     select b.bus_id, b.bus_number, b.capacity, b.brand, b.is_delete 
                     from bus b, employee e, bus_employee be
                     where e.driver_id = be.driver_id and be.bus_id = b.bus_id and e.driver_id = :driverId
              """,
              nativeQuery = true

       )
       public List<BusEntity> findByEmployeeEntity_Id (@Param("driverId") Long driverId);

       // @Query(
       //        value = """
       //               select b.bus_id, b.bus_number, b.capacity, b.brand, b.is_delete , b.routes_id
       //               from bus b, ticket tk
       //               where b.bus_id = tk.bus_id and tk.ticket_id = :ticketId
       //        """,
       //        nativeQuery = true 
       // )
       // public Optional<BusEntity> findByTicketEntity_Id (@Param("ticketId") Long ticketId);

       @Query(
              value = """
                     select b.bus_id, b.bus_number, b.capacity, b.brand, b.is_delete 
                     from bus b, bus_routes_schedule brs
                     where b.bus_id = brs.bus_id and brs.schedule_id = :scheduleId
              """,
              nativeQuery = true
       )

       public Optional<BusEntity> findByBusRouteSchedules_Id (@Param("scheduleId") Long scheduleId);


       @Query(
              value = """
                     select b.bus_id, b.bus_number, b.capacity, b.brand, b.is_delete 
                     from bus b,  penalty_ticket pt
                     where b.bus_id = pt.bus_id and pt.penalty_ticket_id = :penaltyTicketId
              """,
              nativeQuery = true

       )
       public Optional<BusEntity> findByPenaltyTicket_Id(@Param("penaltyTicketId") Long penaltyTicketId);

       // @Query(
       //        value = """
       //               select b.bus_id, b.bus_number, b.capacity, b.brand, b.routes_id, b.is_delete
       //               from bus b, bus_routes br
       //               where b.routes_id = br.routes_id and br.routes_id = :routesId
       //        """,
       //        nativeQuery = true 
       // )
       // public List<BusEntity> findByRoutes_Id (@Param("routesId") Long routesId);

}
