package com.bus_station_ticket.project.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;

@Repository
public interface BusRouteScheduleRepo extends JpaRepository<BusRouteScheduleEntity, Long> {

       public Optional<BusRouteScheduleEntity> findByScheduleId(Long scheduleId);

       @Query(value = """
                            select brs.schedule_id, brs.day_of_week, brs.bus_id, brs.routes_id, brs.departure_time, brs.is_delete
                            from bus_routes_schedule brs
                            where brs.bus_id = :busId
                     """, nativeQuery = true)
       public List<BusRouteScheduleEntity> findByBusEntity_Id(@Param("busId") Long busId);

       @Query(value = """
                            select brs.schedule_id, brs.day_of_week, brs.bus_id, brs.routes_id, brs.departure_time, brs.is_delete
                            from bus_routes_schedule brs
                            where brs.routes_id = :routesId
                     """, nativeQuery = true)
       public List<BusRouteScheduleEntity> findByBusRoutesEntity_Id(@Param("routesId") Long routesId);

}
