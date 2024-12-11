package com.bus_station_ticket.project.ProjectController;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.bus_station_ticket.project.ProjectConfig.ResponseObject;

import jakarta.validation.Valid;

// quy định các method phản hồi cơ bản gồm
// GET: getAll()
// GET: getById(id)
// POST: save(obj)
// PUT: update(obj)
// DELETE: delete(id)
// DELETE: hidden(id)
public interface RestApiSimpleControllerInf<T,ID> {

       public final String MESS_SUCCESS = "success";
       public final String MESS_FAILURE = "failure";

       


       public ResponseEntity<ResponseObject> getAll();
       
       public ResponseEntity<ResponseObject> getById(@PathVariable ID id);
       
       public ResponseEntity<ResponseObject> save(@Valid @RequestBody T obj);
       
       public ResponseEntity<ResponseObject> update(@Valid @RequestBody T obj);

       public ResponseEntity<ResponseObject> delete(@PathVariable ID id);

       public ResponseEntity<ResponseObject> hidden(@PathVariable ID id);

       default Boolean isValidId (ID id){
              if(id != null){
                     if(id instanceof String){
                            return isWhitespaceStringId(String.valueOf(id));
                     }
                     return true;
              }
              return false;
       }

       default Boolean isWhitespaceStringId(String id){
              // tách thành mảng theo khoảng trắng
              String[] words = id.split("\\s+");
              List<String> wordList =  Arrays.asList(words);
              
              // Nếu mảng có 1 phần tử --> false
              if(wordList.size() == 1){
                     return true;
              }
              return false;
       }


}
