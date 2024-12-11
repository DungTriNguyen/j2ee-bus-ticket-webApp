package com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bus_station_ticket.project.ProjectDTO.BusDTO;
import com.bus_station_ticket.project.ProjectEntity.BusEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.EmployeeEntity;
import com.bus_station_ticket.project.ProjectEntity.PenaltyTicketEntity;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;
import com.bus_station_ticket.project.ProjectRepository.EmployeeRepo;
import com.bus_station_ticket.project.ProjectRepository.PenaltyTicketRepo;
@Component
public class BusMapping implements MappingInterface<BusEntity, BusDTO> {

       @Autowired
       private EmployeeRepo employeeRepo;

       @Autowired
       private BusRouteScheduleRepo busRouteScheduleRepo;

       @Autowired
       private PenaltyTicketRepo penaltyTicketRepo;

       @Override
       public BusDTO toDTO(BusEntity entity) {

              BusDTO busDTO = new BusDTO();
              // Mapping các thuộc tính cơ bản
              busDTO.setBusId(entity.getBusId());
              busDTO.setBusNumber(entity.getBusNumber());
              busDTO.setCapacity(entity.getCapacity());
              busDTO.setBrand(entity.getBrand());
              busDTO.setIsDelete(entity.getIsDelete());

              // Mapping các thuộc tính list
              List<Long> listEmployeeEntities_Id = new ArrayList<>();
              List<Long> listBusRouteSchedules_Id = new ArrayList<>();
              List<Long> listPenaltyTicketEntities_Id = new ArrayList<>();

              if (entity.getListEmployeeEntities() != null) {
                     for (EmployeeEntity e : entity.getListEmployeeEntities()) {
                            listEmployeeEntities_Id.add(e.getDriverId());
                     }
              }

              if (entity.getListBusRouteSchedules() != null) {
                     for (BusRouteScheduleEntity e : entity.getListBusRouteSchedules()) {
                            listBusRouteSchedules_Id.add(e.getScheduleId());
                     }
              }

              if (entity.getListPenaltyTicketEntities() != null) {
                     for (PenaltyTicketEntity e : entity.getListPenaltyTicketEntities()) {
                            listPenaltyTicketEntities_Id.add(e.getPenaltyTicketId());
                     }
              }

              busDTO.setListEmployeeEntities_Id(listEmployeeEntities_Id);
              busDTO.setListBusRouteSchedules_Id(listBusRouteSchedules_Id);
              busDTO.setListPenaltyTicketEntities_Id(listPenaltyTicketEntities_Id);

              return busDTO;
       }

       @Override
       public BusEntity toEntity(BusDTO dto) {

              BusEntity busEntity = new BusEntity();

              // Mapping các thuộc tính cơ bản
              busEntity.setBusId(dto.getBusId());
              busEntity.setBusNumber(dto.getBusNumber());
              busEntity.setCapacity(dto.getCapacity());
              busEntity.setBrand(dto.getBrand());
              busEntity.setIsDelete(dto.getIsDelete());

              // Mapping các thộc tính List
              List<EmployeeEntity> listEmployeeEntities = new ArrayList<>();

              if (dto.getListEmployeeEntities_Id() != null) {
                     for (Long value : dto.getListEmployeeEntities_Id()) {
                            EmployeeEntity employeeEntity = this.employeeRepo.findByDriverId(value).orElse(null);
                            listEmployeeEntities.add(employeeEntity);
                     }
              }
              busEntity.setListEmployeeEntities(listEmployeeEntities);

              List<BusRouteScheduleEntity> listBusRouteScheduleEntities = new ArrayList<>();
              if (dto.getListBusRouteSchedules_Id() != null) {
                     for (Long value : dto.getListBusRouteSchedules_Id()) {
                            BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo
                                          .findByScheduleId(value).orElse(null);
                            listBusRouteScheduleEntities.add(busRouteScheduleEntity);
                     }
              }
              busEntity.setListBusRouteSchedules(listBusRouteScheduleEntities);

              List<PenaltyTicketEntity> listPenaltyTicketEntities = new ArrayList<>();
              if (dto.getListPenaltyTicketEntities_Id() != null) {
                     for (Long value : dto.getListPenaltyTicketEntities_Id()) {
                            PenaltyTicketEntity penaltyTicketEntity = this.penaltyTicketRepo
                                          .findByPenaltyTicketId(value).orElse(null);
                            listPenaltyTicketEntities.add(penaltyTicketEntity);
                     }
              }
              busEntity.setListPenaltyTicketEntities(listPenaltyTicketEntities);

              return busEntity;
       }

}
