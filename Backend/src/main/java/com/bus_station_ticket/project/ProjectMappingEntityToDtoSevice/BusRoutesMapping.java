package com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bus_station_ticket.project.ProjectDTO.BusRoutesDTO;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.BusRoutesEntity;
// import com.bus_station_ticket.project.ProjectEntity.TicketEntity
// import com.bus_station_ticket.project.ProjectRepository.BusRoutesRepo;
// import com.bus_station_ticket.project.ProjectRepository.TicketRepo;
import com.bus_station_ticket.project.ProjectRepository.BusRouteScheduleRepo;

@Component
public class BusRoutesMapping implements MappingInterface<BusRoutesEntity, BusRoutesDTO> {

       // @Autowired
       // private TicketRepo ticketRepo;

       @Autowired
       private BusRouteScheduleRepo busRouteScheduleRepo;


       @Override
       public BusRoutesDTO toDTO(BusRoutesEntity entity) {

              // Mapping các thuộc tính đơn giản
              BusRoutesDTO busRoutesDTO = new BusRoutesDTO();

              busRoutesDTO.setRoutesId(entity.getRoutesId());
              busRoutesDTO.setDepartureLocation(entity.getDepartureLocation());
              busRoutesDTO.setDestinationLocation(entity.getDestinationLocation());
              busRoutesDTO.setDistanceKilometer(entity.getDistanceKilometer());
              busRoutesDTO.setTripTime(entity.getTripTime());
              busRoutesDTO.setPrice(entity.getPrice());
              busRoutesDTO.setIsDelete(entity.getIsDelete());

              // mapping thuộc tính list
              // List<Long> listTicketEntities_Id = new ArrayList<>();

              // if (entity.getListTicketEntities() != null) {
              //        for (TicketEntity e : entity.getListTicketEntities()) {
              //               listTicketEntities_Id.add(e.getTicketId());b
              //        }
              // }

              // busRoutesDTO.setListTicketEntities_Id(listTicketEntities_Id);

              List<Long> listBusRouteSchedules_Id = new ArrayList<>();
              if(entity.getListBusRouteSchedules() != null){
                     for(BusRouteScheduleEntity e : entity.getListBusRouteSchedules()){
                            listBusRouteSchedules_Id.add(e.getScheduleId());
                     }
              }
              busRoutesDTO.setListBusRouteSchedules_Id(listBusRouteSchedules_Id);

              return busRoutesDTO;
       }

       @Override
       public BusRoutesEntity toEntity(BusRoutesDTO dto) {

              BusRoutesEntity busRoutesEntity = new BusRoutesEntity();
              // Mapping các thuộc tính đơn giản
              busRoutesEntity.setRoutesId(dto.getRoutesId());
              busRoutesEntity.setDepartureLocation(dto.getDepartureLocation());
              busRoutesEntity.setDestinationLocation(dto.getDestinationLocation());
              busRoutesEntity.setDistanceKilometer(dto.getDistanceKilometer());
              busRoutesEntity.setTripTime(dto.getTripTime());
              busRoutesEntity.setPrice(dto.getPrice());
              busRoutesEntity.setIsDelete(dto.getIsDelete());

              // Mapping cac thuoo tinh list
              // List<TicketEntity> listTicketEntities = new ArrayList<>();

              // if (dto.getListTicketEntities_Id() != null) {

              //        for (Long value : dto.getListTicketEntities_Id()) {
              //               TicketEntity ticketEntity = this.ticketRepo.findByTicketId(value).orElse(null);
              //               listTicketEntities.add(ticketEntity);
              //        }
              // }
              // busRoutesEntity.setListTicketEntities(listTicketEntities);

              List<BusRouteScheduleEntity> listBusRouteScheduleEntities = new ArrayList<>();
              if(dto.getListBusRouteSchedules_Id() != null){
                     for(Long id: dto.getListBusRouteSchedules_Id()){
                            BusRouteScheduleEntity busRouteScheduleEntity = this.busRouteScheduleRepo.findByScheduleId(id).orElse(null);
                            listBusRouteScheduleEntities.add(busRouteScheduleEntity);
                     }
              }
              busRoutesEntity.setListBusRouteSchedules(listBusRouteScheduleEntities);

              return busRoutesEntity;
       }

}
