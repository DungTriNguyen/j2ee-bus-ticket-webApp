package com.bus_station_ticket.project.ProjectService;

import java.time.LocalDate;
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
import com.bus_station_ticket.project.ProjectDTO.BusDTO;
import com.bus_station_ticket.project.ProjectEntity.BusEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.EmployeeEntity;
import com.bus_station_ticket.project.ProjectEntity.PenaltyTicketEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice.BusMapping;
import com.bus_station_ticket.project.ProjectRepository.BusRepo;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;
import com.bus_station_ticket.project.ProjectRepository.EmployeeRepo;
import com.bus_station_ticket.project.ProjectRepository.PenaltyTicketRepo;
import com.bus_station_ticket.project.ProjectRepository.TicketRepo;

@Service
public class BusService implements SimpleServiceInf<BusEntity, BusDTO, Long> {

       @Autowired
       private BusRepo repo;

       @Autowired
       private EmployeeRepo employeeRepo;

       // @Autowired
       // private BusRoutesRepo busRoutesRepo;

       @Autowired
       private BusRouteScheduleRepo busRouteScheduleRepo;

       @Autowired
       private PenaltyTicketRepo penaltyTicketRepo;

       @Autowired
       private TicketRepo ticketRepo;

       @Autowired
       private BusMapping busMapping;

       // Lấy một đối tượng BusEntity theo giá trị busId
       // Input: busId (Long)
       // Output: BusEntity có giá trị busId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public BusEntity getById(Long busIs) {
              return this.repo.findByBusId(busIs).orElse(null);

       }

       // Mapping đối tượng BusEntity --> BusDTO
       // Input: busId (Long)
       // Output: BusDTO có giá trị busId tương ứng
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public BusDTO getById_toDTO(Long busId) {

              BusEntity busEntity = this.repo.findByBusId(busId).orElse(null);

              if (busEntity != null) {
                     return this.busMapping.toDTO(busEntity);

              }
              return null;
       }

       // Lấy tất cả các đối tượng BusEntity
       // Input:
       // Output: List
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<BusEntity> getAll() {
              return this.repo.findAll();

       }

       // Mapping đối tượng List<BusEntity> --> List<BusEntity>
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       @Override
       public List<BusDTO> getAll_toDTO() {
              List<BusEntity> listBusEntities = this.repo.findAll();
              List<BusDTO> listBusDTOs = new ArrayList<>();

              if (listBusEntities.isEmpty() == false) {
                     for (BusEntity e : listBusEntities) {
                            listBusDTOs.add(this.busMapping.toDTO(e));
                     }
                     return listBusDTOs;
              }
              return listBusDTOs;
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess delete(Long id) {

              Optional<BusEntity> optional = this.repo.findByBusId(id);

              if (optional.isPresent()) {
                     Boolean check = foreignKeyViolationIfDelete(optional.get());

                     if (check == false) {
                            // xoa
                            this.repo.delete(optional.get());
                            return new ResponseBoolAndMess(true, MESS_DELETE_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_DELETE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save(BusEntity entityObj) {

              // Optional<BusEntity> optional = this.repo.findByBusId(entityObj.getBusId());
              entityObj.setBusId(-1L);
              if (isForeignKeyEmpty(entityObj) == false && isDuplicatBusNumber(entityObj) == false) {
                     entityObj.setBusId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_SAVE_SUCCESS);
              }
              return new ResponseBoolAndMess(false, MESS_SAVE_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION + " or "
                            + " Matching license plate number");
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess save_toDTO(BusDTO dtoObj) {

              // Kiểm tra giá trị thuộc tính khóa ngoạ
              BusEntity busEntity = this.busMapping.toEntity(dtoObj);
              return save(busEntity);

       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update(BusEntity entityObj) {
              Optional<BusEntity> optional = this.repo.findByBusId(entityObj.getBusId());

              if (optional.isPresent() && isForeignKeyEmpty(entityObj) == false
                            && isDuplicatBusNumber(entityObj) == false
                            && foreignKeyViolationIfHidden(entityObj) == false) {

                     // entityObj.setBusId(null);
                     this.repo.save(entityObj);
                     return new ResponseBoolAndMess(true, MESS_UPDATE_SUCCESS);
              }
              return new ResponseBoolAndMess(false,
                            MESS_UPDATE_FAILURE + "," + MESS_OBJECT_NOT_EXIST + " or " + MESS_FOREIGN_KEY_VIOLATION
                                          + " or " + " Matching license plate number");
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess update_toDTO(BusDTO dtoObj) {

              BusEntity busEntity = this.busMapping.toEntity(dtoObj);
              return update(busEntity);
       }

       @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
       @Override
       public ResponseBoolAndMess invisibleWithoutDelete(Long id) {

              Optional<BusEntity> optional = this.repo.findByBusId(id);

              if (optional.isPresent()) {
                     // kiem tra
                     Boolean check = foreignKeyViolationIfHidden(optional.get());

                     if (check == false) {
                            BusEntity busEntity = optional.get();
                            busEntity.setIsDelete(true);

                            this.repo.save(busEntity);
                            return new ResponseBoolAndMess(true, MESS_HIDDEN_SUCCESS);
                     }
                     return new ResponseBoolAndMess(false, MESS_HIDDEN_FAILURE + "," + MESS_FOREIGN_KEY_VIOLATION);
              }
              return new ResponseBoolAndMess(false, MESS_OBJECT_NOT_EXIST);

       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfDelete(BusEntity entityObj) {

              // Bus foreign key employee, bus_routes_schedule , penalty_tiket

              List<EmployeeEntity> employeeEntities = this.employeeRepo.findByBusEntityId(entityObj.getBusId());

              List<BusRouteScheduleEntity> listBusRouteScheduleEntities = this.busRouteScheduleRepo
                            .findByBusEntity_Id(entityObj.getBusId());

              List<PenaltyTicketEntity> penaltyTicketEntities = this.penaltyTicketRepo
                            .findByBusEntity_Id(entityObj.getBusId());

              // kiem tra
              if (employeeEntities.isEmpty() == true && listBusRouteScheduleEntities.isEmpty() == true
                            && penaltyTicketEntities.isEmpty() == true) {
                     return false;
              }
              return true;
       }

       @Transactional
       @Override
       public Boolean foreignKeyViolationIfHidden(BusEntity entityObj) {
              // Bus foreign key employee, ticket, penalty_tiket

              if (entityObj.getIsDelete() == true) {
                     List<EmployeeEntity> employeeEntities = this.employeeRepo.findByBusEntityId(entityObj.getBusId());

                     List<BusRouteScheduleEntity> listBusRouteScheduleEntities = this.busRouteScheduleRepo
                                   .findByBusEntity_Id(entityObj.getBusId());

                     List<PenaltyTicketEntity> penaltyTicketEntities = this.penaltyTicketRepo
                                   .findByBusEntity_Id(entityObj.getBusId());

                     // kiem tra
                     if (employeeEntities.isEmpty() == false) {

                            for (EmployeeEntity e : employeeEntities) {
                                   if (e.getIsDelete() == false) {
                                          return true;
                                   }
                            }
                     }
                     if (listBusRouteScheduleEntities.isEmpty() == false) {

                            for (BusRouteScheduleEntity e : listBusRouteScheduleEntities) {
                                   if (e.getIsDelete() == false) {
                                          return true;
                                   }
                            }
                     }
                     if (penaltyTicketEntities.isEmpty() == false) {

                            for (PenaltyTicketEntity penaltyTicketEntity : penaltyTicketEntities) {
                                   if (penaltyTicketEntity.getIsDelete() == false) {
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
       public Boolean isForeignKeyEmpty(BusEntity entityObj) {
              // Bus có thuộc tính khóa ngoại là BusRoutes nhưng vì thuộc tính đó có thể null
              // nên không cần kiểm tra
              return false;
       }

       // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation
       // = Isolation.READ_COMMITTED)
       // @Override
       // public Boolean isHasForeignKeyEntity(BusDTO dtoObj) {
       // // Bus co thuoc tinh khoa ngoai BusRoute
       // BusRoutesEntity busRoutesEntity =
       // this.busRoutesRepo.findByRoutesId(dtoObj.getRoutesId())
       // .orElse(null);
       // if (busRoutesEntity != null) {
       // return true;
       // }
       // return false;
       // }

       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public Boolean isDuplicatBusNumber(BusEntity busEntity) {
              // Kiểm tra biển số xe trong database
              
              Optional<BusEntity> existingBusEntity = this.repo.findByBusNumber(busEntity.getBusNumber());

              // Nếu không tìm thấy xe nào, không trùng
              if (existingBusEntity.isEmpty()) {
                     return false;
              }

              // Nếu tìm thấy xe nhưng là chính nó (cùng ID), không trùng
              if (existingBusEntity.get().getBusId().equals(busEntity.getBusId())) {
                     return false;
              }

              // Nếu tìm thấy xe khác với cùng biển số, trùng
              return true;
       }

       // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation
       // = Isolation.READ_COMMITTED)
       // public List<BusDTO> getByDepartureLocationAndDestinationLocation(String
       // departureLocation,
       // String destinationLocation) {
       // BusRoutesEntity busRoutesEntity = this.busRoutesRepo
       // .findByDepartureLocationAndDestinationLocation(departureLocation,
       // destinationLocation)
       // .orElse(null);

       // if (busRoutesEntity != null) {

       // List<BusEntity> listBusEntities =
       // this.repo.findByRoutes_Id(busRoutesEntity.getRoutesId());

       // List<BusDTO> listBusDTOs = new ArrayList<>();

       // for (BusEntity e : listBusEntities) {
       // BusDTO busDTO = this.busMapping.toDTO(e);
       // listBusDTOs.add(busDTO);
       // }

       // return listBusDTOs;
       // }
       // return new ArrayList<>();
       // }

       // @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation
       // = Isolation.READ_COMMITTED)
       // public Integer numberSeatRemain(Long busId, String departureLocation, String
       // destinationLocation,
       // LocalDateTime departureTime, LocalDateTime arivalTime) {

       // List<TicketEntity> ticketEntities = this.ticketRepo.findByBusAndRoutes(busId,
       // departureLocation,
       // destinationLocation, departureTime, arivalTime);

       // int countSeatNotAval = 0;
       // for (TicketEntity e : ticketEntities) {
       // if (e.getStatus().equals("failure") == false) {
       // countSeatNotAval++;
       // }
       // }

       // BusEntity busEntity = this.repo.findByBusId(busId).orElse(null);

       // int numberSeatRemain = busEntity.getCapacity() - countSeatNotAval;

       // return numberSeatRemain;
       // }

       // Kiểm tra ghế có thỏa không và trống không
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseBoolAndMess isValSeat(String seat, Long scheduleId, LocalDate departureDate) {

              BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo.findByScheduleId(scheduleId).orElse(null);

              if(busRouteScheduleEntity == null){
                     return new ResponseBoolAndMess(false,
                                   "Schedule is not availible. Schedule id " + scheduleId + " may not exsit");
              }
              Long busId = busRouteScheduleEntity.getBusEntity().getBusId();

              if (isValSeatFormat(busId, seat) == false) {
                     return new ResponseBoolAndMess(false,
                                   "Seat is not availible. Seat " + seat + " may not in the correct format");
              }

              List<TicketEntity> ticketEntities = this.ticketRepo.findByScheduleIdAndDepartureDate(scheduleId,
                            departureDate);
              if (ticketEntities.isEmpty() == false) {
                     for (TicketEntity e : ticketEntities) {
                            if (e.getSeatNumber().equals(seat) && e.getStatus().equals("failure") == false) {
                                   return new ResponseBoolAndMess(false,
                                                 "Seat is not availible. Seat " + seat + " may be overbooked");
                            }
                     }
                     return new ResponseBoolAndMess(true, "Seat is availible.");
              }

              return new ResponseBoolAndMess(true, "Seat is availible");
       }

       // kiểm tra các ghế còn trống
       // Mặc định các ghế sẽ có dạng mã <busId> + "_"+ [A01,A40] ví dụ 1_A01,1_A02
       @Transactional(propagation = Propagation.REQUIRED, readOnly = true, isolation = Isolation.READ_COMMITTED)
       public ResponseObject getListSeatBusEmpty(Long scheduleId, LocalDate departureDate) {
              BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo.findByScheduleId(scheduleId).orElse(null);

              ResponseObject responseObject = new ResponseObject();

              if(busRouteScheduleEntity == null){
                     responseObject.setStatus("failure");
                     responseObject.addMessage("mess", "Not found bus route schedule with schedule id " + scheduleId);
                     responseObject.setData(null);
                     return responseObject;      
              }

              Long busId = busRouteScheduleEntity.getBusEntity().getBusId();
              
              List<String> seatData = getDataSeatFromBus(busId);

              Map<String, Object> datas = new HashMap<>();

              List<Object> rs = new ArrayList<>();

              // ma ghe va trang thai co trong hay khong
              Map<String, Boolean> seatStatus = new HashMap<>();

              for (String seat : seatData) {
                     ResponseBoolAndMess check = isValSeat(seat, scheduleId, departureDate);
                     seatStatus.put(seat, check.getValueBool());
              }
              rs.add(seatStatus);
              datas.put("listSeatEmpty", rs);

              responseObject.setStatus("success");
              responseObject.addMessage("mess", "List seat empty on bus id " + busId);
              datas.put("busId", busId);

              responseObject.setData(datas);

              return responseObject;
       }

       // Lấy dữ liệu ghế của xe buyết có liên quan
       @Transactional
       public List<String> getDataSeatFromBus(Long busId) {

              List<String> seatData = new ArrayList<>();
              Optional<BusEntity> optional = this.repo.findByBusId(busId);

              if (optional.isPresent()) {
                     for (int i = 1; i <= 40; i++) {
                            String seat = "";
                            if (i < 10) {
                                   seat = busId + "_" + "A0" + i;
                            } else {
                                   seat = busId + "_" + "A" + i;
                            }
                            seatData.add(seat);
                     }
              }

              return seatData;

       }

       @Transactional
       // Kiểm tra ghế có hợp lệ không
       public Boolean isValSeatFormat(Long busId, String seat) {

              List<String> dataSeat = getDataSeatFromBus(busId);

              if (dataSeat.contains(seat)) {
                     return true;
              }
              return false;
       }

}
