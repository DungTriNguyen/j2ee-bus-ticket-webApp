package com.bus_station_ticket.project.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bus_station_ticket.project.ProjectEntity.EmployeeEntity;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {

       // Truy suất theo thuộc tính
       public Optional<EmployeeEntity> findByDriverId(Long driverId);

       public List<EmployeeEntity> findByIsDriver(Boolean isDriver);

       public List<EmployeeEntity> findByDriverName(String driverName);

       public Optional<EmployeeEntity> findByLicenseNumber(String licenseNumber);

       public Optional<EmployeeEntity> findByPhoneNumber(String phoneNumber);

       public List<EmployeeEntity> findByIsDelete(Boolean isDelete);

       @Query(value = """
                            SELECT e.driver_id, e.is_driver, e.driver_name, e.license_number, e.phone_number, e.is_delete
                            FROM employee e, bus b, bus_employee be
                            WHERE e.driver_id = be.driver_id AND be.bus_id = b.bus_id AND b.bus_id = :busId
                     """, nativeQuery = true)
       public List<EmployeeEntity> findByBusEntityId(@Param("busId") Long busId);

       @Modifying
       @Query(value = """
                            DELETE FROM bus_employee be
                            WHERE be.driver_id = :driverId AND be.bus_id = :busId
                     """, nativeQuery = true)
       public int deleteBusAndEmplyee(@Param("busId") Long busId, @Param("driverId") Long driverId);

       @Modifying
       @Query(value = """
                            INSERT INTO bus_employee (bus_id, driver_id)
                            VALUES (:busId, :driverId)
                     """, nativeQuery = true)
       public int insertBusAndEmplyee(@Param("busId") Long busId, @Param("driverId") Long driverId);

       // @Query(
       //        value = """
       //               select be.bus_id, be.driver_id
       //               from bus_employee be                           
       //        """, nativeQuery =  true
       // )
       // List<BusAndDriverDTO> findAllDriverBusRelationsAsDTO();
}
