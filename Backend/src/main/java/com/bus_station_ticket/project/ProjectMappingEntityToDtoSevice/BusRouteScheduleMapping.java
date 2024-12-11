package com.bus_station_ticket.project.ProjectMappingEntityToDtoSevice;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bus_station_ticket.project.ProjectDTO.BusRouteScheduleDTO;
import com.bus_station_ticket.project.ProjectEntity.BusRouteScheduleEntity;
import com.bus_station_ticket.project.ProjectEntity.TicketEntity;
import com.bus_station_ticket.project.ProjectRepository.BusRepo;
import com.bus_station_ticket.project.ProjectRepository.BusRoutesRepo;
import com.bus_station_ticket.project.ProjectRepository.TicketRepo;

@Component
public class BusRouteScheduleMapping implements MappingInterface<BusRouteScheduleEntity, BusRouteScheduleDTO> {

       @Autowired
       private BusRoutesRepo busRoutesRepo;

       @Autowired
       private BusRepo busRepo;

       @Autowired
       private TicketRepo ticketRepo;

       @Override
       public BusRouteScheduleDTO toDTO(BusRouteScheduleEntity entity) {
              
              BusRouteScheduleDTO busRouteScheduleDTO = new BusRouteScheduleDTO();

              busRouteScheduleDTO.setScheduleId(entity.getScheduleId());
              busRouteScheduleDTO.setDayOfWeek(entity.getDayOfWeek());
              busRouteScheduleDTO.setDepartureTime(entity.getDepartureTime());
              busRouteScheduleDTO.setIsDelete(entity.getIsDelete());

              busRouteScheduleDTO.setBusEntity_Id(entity.getBusEntity() != null ? entity.getBusEntity().getBusId() : null);

              busRouteScheduleDTO.setBusRoutesEntity_Id(entity.getBusRoutesEntity() != null ? entity.getBusRoutesEntity().getRoutesId() : null);

              List<Long> listTicketEntities_Id = new ArrayList<>();

              if(entity.getListTicketEntities() != null){
                     for (TicketEntity e : entity.getListTicketEntities()) {
                            listTicketEntities_Id.add(e.getTicketId());
                     }
              }
              busRouteScheduleDTO.setListTicketEntities_Id(listTicketEntities_Id);

              return busRouteScheduleDTO;
              
       }

       @Override
       public BusRouteScheduleEntity toEntity(BusRouteScheduleDTO dto) {
              
              BusRouteScheduleEntity busRouteScheduleEntity = new BusRouteScheduleEntity();

              busRouteScheduleEntity.setScheduleId(dto.getScheduleId());
              busRouteScheduleEntity.setDayOfWeek(dto.getDayOfWeek());
              busRouteScheduleEntity.setDepartureTime(dto.getDepartureTime());
              busRouteScheduleEntity.setIsDelete(dto.getIsDelete());

              busRouteScheduleEntity.setBusEntity(this.busRepo.findByBusId(dto.getBusEntity_Id()).orElse(null));

              
              busRouteScheduleEntity.setBusRoutesEntity(this.busRoutesRepo.findByRoutesId(dto.getBusRoutesEntity_Id()).orElse(null));

              List<TicketEntity> listTicketEntities = new ArrayList<>();

              if(dto.getListTicketEntities_Id() != null){
                     for (Long id : dto.getListTicketEntities_Id()) {
                            
                            TicketEntity ticketEntity = this.ticketRepo.findByTicketId(id).orElse(null);

                            listTicketEntities.add(ticketEntity);

                     }
              }
              busRouteScheduleEntity.setListTicketEntities(listTicketEntities);

              return busRouteScheduleEntity;
       }
}
