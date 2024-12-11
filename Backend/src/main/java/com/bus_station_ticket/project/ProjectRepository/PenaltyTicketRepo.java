package com.bus_station_ticket.project.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bus_station_ticket.project.ProjectEntity.PenaltyTicketEntity;

public interface PenaltyTicketRepo extends JpaRepository<PenaltyTicketEntity, Long> {

       // Truy suất theo thuộc tính
       public Optional<PenaltyTicketEntity> findByPenaltyTicketId(Long penaltyTicketId);

       public Optional<PenaltyTicketEntity> findByPenaltyDay(LocalDateTime penaltyDay);

       public List<PenaltyTicketEntity> findByIsDelete(Boolean isDelete);

       public List<PenaltyTicketEntity> findByPrice(Float price);

       @Query(value = """
                            select pt.penalty_ticket_id, pt.bus_id, pt.driver_id, pt.penalty_day, pt.description, pt.responsibility, pt.price, pt.is_delete
                            from penalty_ticket pt, employee e
                            where pt.driver_id = e.driver_id and pt.driver_id = :driverId
                     """, nativeQuery = true

       )
       public List<PenaltyTicketEntity> findByEmployeeEntity_Id(@Param("driverId") Long driverId);

       @Query(value = """
                            select pt.penalty_ticket_id, pt.bus_id, pt.driver_id, pt.penalty_day, pt.description, pt.responsibility, pt.price, pt.is_delete
                            from penalty_ticket pt, bus b
                            where pt.bus_id = b.bus_id and pt.bus_id = :busId
                     """, nativeQuery = true

       )
       public List<PenaltyTicketEntity> findByBusEntity_Id(@Param("busId") Long busId);


       
       @Query(value = """
                     select pt.penalty_ticket_id, pt.bus_id, pt.driver_id, pt.penalty_day, pt.description, pt.responsibility, pt.price, pt.is_delete
                     from penalty_ticket pt
                     where pt.penalty_day between :dateA and :dateB
                     """, nativeQuery = true)
       public List<PenaltyTicketEntity> findByPenaltyDayBetween(
                     @Param("dateA") LocalDateTime dateA,
                     @Param("dateB") LocalDateTime dateB);

}
