package com.bus_station_ticket.project.ProjectRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bus_station_ticket.project.ProjectEntity.TicketEntity;

public interface TicketRepo extends JpaRepository<TicketEntity, Long> {

       // truy suất theo thuộc tính
       public Optional<TicketEntity> findByTicketId(Long tiketId);

       @Query(value = """
                            select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                            from account acc, ticket tk
                            where acc.username = tk.username_id and tk.username_id = :userName
                     """, nativeQuery = true)
       public List<TicketEntity> findByAccountEntity_userName(@Param("userName") String userName);

       // @Query(value = """
       // select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id,
       // tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone,
       // tk.status, tk.is_delete
       // from bus b, ticket tk
       // where b.bus_id = tk.bus_id and tk.bus_id = :busId
       // """, nativeQuery = true)
       // public List<TicketEntity> findByBusEntity_Id(@Param("busId") Long busId);

       // @Query(
       // value = """
       // select tk.ticket_id, tk.username_id, tk.bus_id, tk.routes_id, tk.payment_id,
       // tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone,
       // tk.status, tk.is_delete
       // from bus_routes br, ticket tk
       // where br.routes_id = tk.routes_id and tk.routes_id = :routesId
       // """,
       // nativeQuery = true
       // )
       // public List<TicketEntity> findByBusRoutesEntity_Id (@Param("routesId") Long
       // routesId);

       // public List<TicketEntity> findByRoutesId(Long routesId);

       @Query(value = """
                            select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                            from payment p, ticket tk
                            where p.payment_id = tk.payment_id and tk.payment_id = :paymentId
                     """, nativeQuery = true)
       public List<TicketEntity> findByPaymentEntity_Id(@Param("paymentId") Long paymentId);

       @Query(value = """
                            select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                            from discount d, ticket tk
                            where d.discount_id = tk.discount_id and tk.discount_id = :discountId
                     """, nativeQuery = true)
       public List<TicketEntity> findByDiscountEntity_Id(@Param("discountId") Long discountId);

       public List<TicketEntity> findBySeatNumber(String seatNumber);

       public List<TicketEntity> findByDepartureDate(LocalDateTime departureDate);

       public List<TicketEntity> findByPrice(float price);

       @Query(value = """
                            select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                            from feedback f, ticket tk
                            where f.ticket_id = tk.ticket_id and f.feedback_id = :feedbackId
                     """, nativeQuery = true)
       public Optional<TicketEntity> findByFeedbackEntity_Id(@Param("feedbackId") Long feedbackId);

       public List<TicketEntity> findByIsDelete(Boolean isDelete);

       @Query(value = """
                     select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                     from ticket tk, bus_routes_schedule brs
                     where tk.departure_date = :departureDate and tk.schedule_id = brs.schedule_id and tk.schedule_id = :scheduleId

                     """, nativeQuery = true)

       public List<TicketEntity> findByScheduleIdAndDepartureDate(@Param("scheduleId") Long scheduleId, @Param("departureDate") LocalDate departureDate);

       @Query(value = """
                                 select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                                from ticket tk, bus_routes_schedule brs
                                where tk.schedule_id = brs.schedule_id and brs.schedule_id = :scheduleId
                     """, nativeQuery = true)
       public List<TicketEntity> findByBusRouteScheduleEntity_Id(@Param("scheduleId") Long scheduleId);

       @Query(value = """
                         select tk.ticket_id, tk.username_id, tk.schedule_id, tk.payment_id, tk.discount_id, tk.seat_number, tk.departure_date, tk.price, tk.phone, tk.status, tk.is_delete
                         from ticket tk
                         where tk.departure_date between :dateA and :dateB
                     """, nativeQuery = true)
       public List<TicketEntity> findTicketsWithinDateRange(@Param("dateA") LocalDate dateA,
                     @Param("dateB") LocalDate dateB);

}
