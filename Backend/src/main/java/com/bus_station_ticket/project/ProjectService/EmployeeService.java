package com.bus_station_ticket.project.ProjectService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;
import com.bus_station_ticket.project.ProjectConfig.ResponseObject;
import com.bus_station_ticket.project.ProjectDTO.EmployeeDTO;
import com.bus_station_ticket.project.ProjectEntity.BusEntity;
import com.bus_station_ticket.project.ProjectEntity.EmployeeEntity;
import com.bus_station_ticket.project.ProjectEntity.PenaltyTicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.EmployeeMapping;
import com.bus_station_ticket.project.ProjectRepository.BusRepo;
import com.bus_station_ticket.project.ProjectRepository.EmployeeRepo;
import com.bus_station_ticket.project.ProjectRepository.PenaltyTicketRepo;

@Service
public class EmployeeService implements SimpleServiceInf<EmployeeEntity, EmployeeDTO, Long> {

       @Autowired
       private EmployeeRepo repo;

       @Autowired
       private PenaltyTicketRepo penaltyTicketRepo;

       @Autowired
       private BusRepo busRepo;

       @Autowired
       private EmployeeMapping employeeMapping;

       // Lấy một đối tượng EmployeeEntity theo giá trị driverId
       // Input: driverId (Long)
       // Output: EmployeeEntity có giá trị driverId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public EmployeeEntity getById(Long driverId) {
              return this.repo.findByDriverId(driverId).orElse(null);
       }

       // Mapping đối tượng EmployeeEntity --> EmployeeDTO
       // Input: driverId (Long)
       // Output: EmployeeDTO có giá trị driverId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public EmployeeDTO getById_toDTO(Long driverId) {
              EmployeeEntity employeeEntity = this.repo.findByDriverId(driverId).orElse(null);

              if (employeeEntity != null) {
                     return this.employeeMapping.toDTO(employeeEntity);
              }
              return null;
       }

       // Lấy tất cả các đối tượng EmployeeEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<EmployeeEntity> getAll() {
              return this.repo.findAll();

       }

       // Mapping đối tượng List<EmployeeEntity> --> List<EmployeeDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<EmployeeDTO> getAll_toDTO() {
              List<EmployeeEntity> listEmployeeEntities = this.repo.findAll();

              List<EmployeeDTO> listEmployeeDTOs = new ArrayList<>();

              if (listEmployeeEntities.isEmpty() == false) {
                     for (EmployeeEntity e : listEmployeeEntities) {
                            listEmployeeDTOs.add(this.employeeMapping.toDTO(e));
                     }
                     return listEmployeeDTOs;
              }
              return listEmployeeDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<EmployeeEntity> optional = this.repo.findByDriverId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfDelete(optional.get());

                     if (check == false) {
                            this.repo.delete(optional.get());
                            return new ResponseBoolAndMess(true, MESS_DELETE_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_DELETE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save(EmployeeEntity entityObj) {

              // Optional<EmployeeEntity> optional =
              // this.repo.findByDriverId(entityObj.getDriverId());

              if (isForeignKeyEmpty(entityObj) == false) {
                     entityObj.setDriverId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }

              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(EmployeeDTO dtoObj) {
              EmployeeEntity employeeEntity = this.employeeMapping.toEntity(dtoObj);

              return save(employeeEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(EmployeeEntity entityObj) {
              Optional<EmployeeEntity> optional = this.repo.findByDriverId(entityObj.getDriverId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false
                            && foreignKeyViolationIfHidden(entityObj) == false) {
                     // entityObj.setDriverId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }
              return new ResponseBoolAndMess(false,
                            MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or " + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(EmployeeDTO dtoObj) {

              EmployeeEntity employeeEntity = this.employeeMapping.toEntity(dtoObj);

              return update(employeeEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {
              Optional<EmployeeEntity> optional = this.repo.findByDriverId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            EmployeeEntity employeeEntity = optional.get();
                            employeeEntity.setIsDelete(check);
                            this.repo.save(employeeEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }

              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(EmployeeEntity entityObj) {

              // Employee foreign key bus, penalty_ticket
              List<BusEntity> busEntities = this.busRepo.findByEmployeeEntity_Id(entityObj.getDriverId());

              List<PenaltyTicketEntity> penaltyTicketEntities = this.penaltyTicketRepo
                            .findByEmployeeEntity_Id(entityObj.getDriverId());

              if (busEntities.isEmpty() == true && penaltyTicketEntities.isEmpty() == true) {
                     return false;
              }

              return true;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(EmployeeEntity entityObj) {

              if (entityObj.getIsDelete()) {
                     // Employee foreign key bus, penalty_ticket
                     List<BusEntity> busEntities = this.busRepo.findByEmployeeEntity_Id(entityObj.getDriverId());

                     List<PenaltyTicketEntity> penaltyTicketEntities = this.penaltyTicketRepo
                                   .findByEmployeeEntity_Id(entityObj.getDriverId());

                     if (busEntities.isEmpty() == false) {

                            for (BusEntity e : busEntities) {
                                   if (e.getIsDelete() == false) {
                                          return true;
                                   }
                            }
                     }
                     if (penaltyTicketEntities.isEmpty() == false) {
                            for (PenaltyTicketEntity e : penaltyTicketEntities) {
                                   if (e.getIsDelete() == false) {
                                          return true;
                                   }
                            }
                     }

                     return false;
              }
              return false;

       }

       @Transactional
       @Override
       public Boolean isForeignKeyEmpty(EmployeeEntity entityObj) {
              // Employee khong co thuoc tinh khoa ngoai
              return false;
       }

       // @Override
       // public Boolean isHasForeignKeyEntity(EmployeeDTO dtoObj) {
       // // Employee khong co thuoc tinh khoa ngoai
       // return true;
       // }

       // Phan nhan vien lai xe
       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       public ResponseBoolAndMess addEmployeeDriverBus(Long driverId, Long busId) {

              EmployeeEntity employeeEntity = this.repo.findByDriverId(driverId).orElse(null);
              BusEntity busEntity = this.busRepo.findByBusId(busId).orElse(null);

              if (employeeEntity != null && busEntity != null) {

                     // kiem tra xem co duoc phan tu truoc khonng
                     if (busEntity.getListEmployeeEntities().contains(employeeEntity) == false) {
                            int value = this.repo.insertBusAndEmplyee(busId, driverId);
                            if (value > 0) {
                                   return new ResponseBoolAndMess(true,
                                                 "Assign bus driver with " + busId + " code successfully");
                            }
                            return new ResponseBoolAndMess(false,
                                          "Assign bus driver with " + busId + " code no successfully");
                     }
                     return new ResponseBoolAndMess(false,
                                   "Assign bus driver with " + busId
                                                 + " code no successfully because it had already been assigned before");
              }
              return new ResponseBoolAndMess(false, "Assign bus driver with " + busId + " code no successfully");

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       public ResponseBoolAndMess deleteEmployeeDriverBus(Long driverId, Long busId) {

              int value = this.repo.deleteBusAndEmplyee(busId, driverId);

              if (value > 0) {
                     return new ResponseBoolAndMess(true,
                                   "Deletion of bus driver assignment with " + busId + " code successfully");
              }
              return new ResponseBoolAndMess(false,
                            "Deletion of bus driver assignment with " + busId + " code no successfully");
       }

       // trả dữ liệu mã nhân viên và mã xe
       public ResponseObject getAllDriverIdAndBusId() {

              Map<Long, Long> busId_driverId = new HashMap<>();

              ResponseObject responseObject = new ResponseObject();

              List<EmployeeEntity> employeeEntities = this.repo.findAll();

              for (EmployeeEntity e : employeeEntities) {
                     List<BusEntity> busEntities = e.getListBusEntity();
                     for (BusEntity b : busEntities) {
                            busId_driverId.put(b.getBusId(), e.getDriverId());;
                     }
              }

              responseObject.setStatus("success");
              responseObject.addMessage("mess", "All data BusEmployee Table. <busId : driverId>");
              responseObject.setData(busId_driverId);

              return responseObject;
       }
}
