package com.personal.demo.controllers;

import com.personal.demo.service.DataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    @PostMapping(value="/uploadSheet")
    public ResponseEntity<?> enterData(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        if (dataService.isExcelFile(file)) {
            Object response = dataService.saveExcel(file, request.getRemoteAddr());
            if (response.getClass() == Boolean.class){
                return new ResponseEntity<>("Data Added Successfully", HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("Excel file Not Detected", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getSheet")
    public ResponseEntity<?> getData(HttpServletRequest request){
        String requestIp=request.getRemoteAddr();
        Object response = dataService.getSheetData(requestIp);
        if (response.getClass().equals("".getClass())){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
