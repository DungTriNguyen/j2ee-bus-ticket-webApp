package com.bus_station_ticket.project.ProjectConfig;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerConfig {


       // Ghi log dữ liệu, mức độ Info
       public static void writeInfoLevel(Class<?> clazz, String methodName, String mess) {

              Logger logger = LoggerFactory.getLogger(clazz);

              // Lấy thời gian hiện tại
              LocalDateTime presenTime = LocalDateTime.now();

               String messInfo = "[" + presenTime + "][" + clazz.getName() + "]["+ methodName +"]: " + mess;

              logger.info(messInfo);
       }

       // Ghi log dữ liệu, mức độ Warning
       public static void writeWarningLevel(Class<?> clazz, String methodName, String mess) {

              Logger logger = LoggerFactory.getLogger(clazz);

              // Lấy thời gian hiện tại
              LocalDateTime presenTime = LocalDateTime.now();

               String messInfo = "[" + presenTime + "][" + clazz.getName() + "]["+ methodName +"]: " + mess;

              logger.warn(messInfo);
       }

       // Ghi log dữ liệu, mức độ Error
       public static void writeErrorLevel(Class<?> clazz, String methodName, String mess) {

              Logger logger = LoggerFactory.getLogger(clazz);

              // Lấy thời gian hiện tại
              LocalDateTime presenTime = LocalDateTime.now();

              String messInfo = "[" + presenTime + "][" + clazz.getName() + "]["+ methodName +"]: " + mess;

              logger.error(messInfo);
       }
}
