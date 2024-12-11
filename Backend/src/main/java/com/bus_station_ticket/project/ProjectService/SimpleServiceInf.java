package com.bus_station_ticket.project.ProjectService;


import java.util.List;


import com.bus_station_ticket.project.ProjectConfig.ResponseBoolAndMess;

public interface SimpleServiceInf<ENTITY,DTO,ID> {

       public final String MESS_SAVE_SUCCESS = "Save data successfully";
       public final String MESS_SAVE_FAILURE = "Save data not successfully";

       public final String MESS_UPDATE_SUCCESS = "Update data successfully";
       public final String MESS_UPDATE_FAILURE = "Update data not successfully";
       
       public final String MESS_OBJECT_NOT_EXIST = "The object does not exist in the database";
       public final String MESS_NO_DATA = "No data exists in the database";
       
       public final String MESS_DELETE_SUCCESS = "Delete data successfully";
       public final String MESS_DELETE_FAILURE = "Delete data not successfully";
       
       public final String MESS_HIDDEN_SUCCESS = "Hidden data successfully";
       public final String MESS_HIDDEN_FAILURE = "Hidden data not successfully";
       
       
       public final String MESS_FOREIGN_KEY_VIOLATION = "Foreign key violation error";
       public final String MESS_FOREIGN_KEY_EMPTY = "Foreign key empty error";
       
       // Entity
       public ENTITY getById(ID id);
       public List<ENTITY> getAll();
       public ResponseBoolAndMess save (ENTITY entityObj);
       public ResponseBoolAndMess update (ENTITY entityObj);
       public ResponseBoolAndMess delete (ID id);
       public ResponseBoolAndMess invisibleWithoutDelete(ID id);

       // dto
       public DTO getById_toDTO(ID id);
       public List<DTO> getAll_toDTO();
       public ResponseBoolAndMess save_toDTO (DTO dtoObj);
       public ResponseBoolAndMess update_toDTO (DTO dtoObj);
       // public Boolean isHasForeignKeyEntity(DTO dtoObj);

       //
       public Boolean foreignKeyViolationIfDelete(ENTITY entityObj);
       public Boolean foreignKeyViolationIfHidden(ENTITY entityObj);
       public Boolean isForeignKeyEmpty(ENTITY entityObj);
       

       default <T> Boolean compareId (T id1, T id2){
              
              String idValue1 = String.valueOf(id1);
              String idValue2 = String.valueOf(id2);

              if(idValue1.equals(idValue2)){
                     return true;
              }
              return false;
       }


}
