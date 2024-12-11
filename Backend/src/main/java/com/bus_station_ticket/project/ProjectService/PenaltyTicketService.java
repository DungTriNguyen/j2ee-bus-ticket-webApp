package com.bus_station_ticket.project.ProjectService;

import java.time.LocalDateTime;
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
import com.bus_station_ticket.project.ProjectDTO.PenaltyTicketDTO;
import com.bus_station_ticket.project.ProjectEntity.BusEntity;
import com.bus_station_ticket.project.ProjectEntity.EmployeeEntity;
import com.bus_station_ticket.project.ProjectEntity.PenaltyTicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.PenaltyTicketMapping;
import com.bus_station_ticket.project.ProjectRepository.PenaltyTicketRepo;

@Service
public class PenaltyTicketService implements SimpleServiceInf<PenaltyTicketEntity, PenaltyTicketDTO, Long> {

       @Autowired
       private PenaltyTicketRepo repo;

       @Autowired
       private PenaltyTicketMapping penaltyTicketMapping;

       // Lấy một đối tượng PenaltyTicketEntity theo giá trị
       // Input: penaltyTicketId (Long)
       // Output: PenaltyTicketEntity có giá trị penaltyTicketId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public PenaltyTicketEntity getById(Long penaltyTicketId) {
              return this.repo.findByPenaltyTicketId(penaltyTicketId).orElse(null);
       }

       // Mapping đối tượng PenaltyTicketEntity --> PenaltyTicketDTO
       // Input: penaltyTicketId (Long)
       // Output: PenaltyTicketDTO có giá trị penaltyTicketId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public PenaltyTicketDTO getById_toDTO(Long penaltyTicketId) {
              PenaltyTicketEntity penaltyTicketEntity = this.repo.findByPenaltyTicketId(penaltyTicketId).orElse(null);

              if (penaltyTicketEntity != null) {
                     return this.penaltyTicketMapping.toDTO(penaltyTicketEntity);
              }
              return null;
       }

       // Lấy tất cả các đối tượng PenaltyTicketEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<PenaltyTicketEntity> getAll() {
              return this.repo.findAll();
       }

       // Mapping đối tượng List<PenaltyTicketEntity> --> List<PenaltyTicketDTO>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<PenaltyTicketDTO> getAll_toDTO() {
              List<PenaltyTicketEntity> listPenaltyTicketEntities = this.repo.findAll();

              List<PenaltyTicketDTO> listPenaltyTicketDTOs = new ArrayList<>();

              if (listPenaltyTicketEntities.isEmpty() == false) {
                     for (PenaltyTicketEntity e : listPenaltyTicketEntities) {
                            listPenaltyTicketDTOs.add(this.penaltyTicketMapping.toDTO(e));
                     }
                     return listPenaltyTicketDTOs;
              }
              return listPenaltyTicketDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<PenaltyTicketEntity> optional = this.repo.findByPenaltyTicketId(id);

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
       public ResponseBoolAndMess save(PenaltyTicketEntity entityObj) {

              // Optional<PenaltyTicketEntity> optional =
              // this.repo.findByPenaltyTicketId(entityObj.getPenaltyTicketId());

              if (isForeignKeyEmpty(entityObj) == false) {
                     entityObj.setPenaltyTicketId(null);
                     this.repo.save(entityObj);
                     // System.out.println(entityObj.getBusEntity().getBusNumber());
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(PenaltyTicketDTO dtoObj) {

              // kiem tra thuoc tinh khoa ngoai

              PenaltyTicketEntity penaltyTicketEntity = this.penaltyTicketMapping.toEntity(dtoObj);

              return save(penaltyTicketEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(PenaltyTicketEntity entityObj) {

              Optional<PenaltyTicketEntity> optional = this.repo.findByPenaltyTicketId(entityObj.getPenaltyTicketId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false && foreignKeyViolationIfHidden(entityObj) == false) {
                     
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }
              return new ResponseBoolAndMess(false,
                            MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or " + MESS_FOREIGN_KEY_VIOLATION);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(PenaltyTicketDTO dtoObj) {

              // kiem tra thuoc tinh khoa ngoai

              PenaltyTicketEntity penaltyTicketEntity = this.penaltyTicketMapping.toEntity(dtoObj);

              return update(penaltyTicketEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<PenaltyTicketEntity> optional = this.repo.findByPenaltyTicketId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            PenaltyTicketEntity penaltyTicketEntity = optional.get();
                            penaltyTicketEntity.setIsDelete(true);
                            this.repo.save(penaltyTicketEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(PenaltyTicketEntity entityObj) {

              // PenaltyTicket has no foreign key
              return false;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(PenaltyTicketEntity entityObj) {

              return false;
       }

       @Transactional
       @Override
       public Boolean isForeignKeyEmpty(PenaltyTicketEntity entityObj) {
              // PenaltyTicket co 2 thuocj tinh khoa ngoai bus_number va driverId
              // kiem tra
              BusEntity busEntity = entityObj.getBusEntity();
              EmployeeEntity employeeEntity = entityObj.getEmployeeEntity();

              if (busEntity != null && employeeEntity != null) {
                     return false;
              }
              return true;
       }

       // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation
       // = Isolation.READ_COMMITTED)
       // @Override
       // public Boolean isHasForeignKeyEntity(PenaltyTicketDTO dtoObj) {
       // // PenaltyTicket co 2 thuocj tinh khoa ngoai bus_number va driverId
       // // kiem tra

       // BusEntity busEntity =
       // this.busRepo.findByBusId(dtoObj.getBusEntity_Id()).orElse(null);
       // EmployeeEntity employeeEntity =
       // this.employeeRepo.findByDriverId(dtoObj.getEmployeeEntity_Id())
       // .orElse(null);

       // if (busEntity != null && employeeEntity != null) {
       // return true;
       // }

       // return false;
       // }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject statisticPenaltyTicketRangeDay(LocalDateTime dateA, LocalDateTime dateB) {

              ResponseObject responseObject = new ResponseObject();
              List<PenaltyTicketEntity> ticketEntities = this.repo.findByPenaltyDayBetween(dateA, dateB);

              if (ticketEntities.isEmpty() == false) {

                     responseObject.setStatus("success");
                     responseObject.addMessage("mess", "Statistics from day " + dateA + " to day " + dateB);

                     List<PenaltyTicketDTO> penaltyTicketDTOs = new ArrayList<>();

                     List<Long> driverIds = new ArrayList<>();

                     float sumMoneyPenalty = 0;
                     float sumMoneyPenaltyNoProcess = 0;
                     // mapping
                     for (PenaltyTicketEntity e : ticketEntities) {
                            penaltyTicketDTOs.add(this.penaltyTicketMapping.toDTO(e));

                            if (e.getResponsibility() == true) {
                                   sumMoneyPenalty += e.getPrice();
                            }

                            if (e.getResponsibility() == false) {
                                   sumMoneyPenaltyNoProcess += e.getPrice();
                            }

                            if (driverIds.contains(e.getEmployeeEntity().getDriverId()) == false) {
                                   driverIds.add(e.getEmployeeEntity().getDriverId());
                            }
                     }

                     responseObject.setData(penaltyTicketDTOs);
                     responseObject.addMessage("size", penaltyTicketDTOs.size());

                     responseObject.addMessage("sumMoneyPenalty", sumMoneyPenalty);
                     responseObject.addMessage("sumMoneyPenaltyNoProcess", sumMoneyPenaltyNoProcess);

                     // so tien phat cua tung tai xe
                     List<Object> list = new ArrayList<>();
                     Map<Long, Float> info = new HashMap<>();

                     for (Long id : driverIds) {

                            float sumMoney = 0;
                            for (PenaltyTicketEntity e : ticketEntities) {
                                   if (e.getEmployeeEntity().getDriverId().equals(id)) {
                                          sumMoney += e.getPrice();
                                   }
                            }
                            info.put(id, sumMoney);
                     }
                     list.add(info);
                     responseObject.addMessage("sumMoneyPenaltyDriverId", list);

                     return responseObject;

              }
              responseObject.setStatus("failure");
              responseObject.setData(ticketEntities);
              responseObject.addMessage("mess", "There are no statistics from day " + dateA + " to day " + dateB);

              return responseObject;
       }

}
