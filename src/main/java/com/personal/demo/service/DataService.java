package com.personal.demo.service;

import com.personal.demo.entity.Mapper;
import com.personal.demo.entity.Material;
import com.personal.demo.entity.SignedInDetails;
import com.personal.demo.dao.CheckDao;
import com.personal.demo.dao.DataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private DataDao dataDao;

    @Autowired
    private CheckDao check;

    public boolean isExcelFile(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    private boolean signedIn(String ipAddr){
        return check.existsById(ipAddr);
    }
    public Object saveExcel(MultipartFile file, String ipAddr) throws Exception{
        if (signedIn(ipAddr)) {
            try (XSSFWorkbook workBook = new XSSFWorkbook(file.getInputStream())) {
                XSSFSheet sheet = workBook.getSheet("Sheet1");
                int rowNumber = 1;

                List<Material> list = new ArrayList<>();
                for (Row row : sheet) {

                    if (rowNumber == 1) {
                        rowNumber++;
                        continue;
                    }
                    boolean add=true;
                    Material data = new Material();
                    for (int cId = 0; cId < 4; cId++) {
                        Cell cell = row.getCell(cId, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        if (cell.getCellType().toString().equals("BLANK") && cId != 1) {
                            add=false;
                            System.out.println("Row " + rowNumber + " contains Blank entries");
                            break;
                        }
                        switch (cId) {
                            case 0:
                                data.setItem(cell.getStringCellValue());
                                break;
                            case 1:
                                data.setSize(cell.getStringCellValue());
                                break;
                            case 2:
                                data.setQuantity((int) cell.getNumericCellValue());
                                break;
                            case 3:
                                data.setPrice(cell.getNumericCellValue());
                                break;
                        }
                    }
                    data.setSNo(rowNumber);
                    if (add){
                        list.add(data);
                    }
                    rowNumber++;
                }
                String userName = check.findById(ipAddr).get().getUserName();
                dataDao.save(new Mapper(userName, list));
            } catch (Exception e) {
                throw new Exception(e);
                //return "Excel Sheet Corrupted";
            }
            return true;
        }
        else {
            return "No user SignedIn";
        }
    }

    public Object getSheetData(String ipAddr){
        Optional<SignedInDetails> optional = check.findById(ipAddr);
        if (optional.isPresent()){
            Optional<Mapper> optionalMaterial = dataDao.findByUserName(optional.get().getUserName());
            if (optionalMaterial.isPresent()){
                return optionalMaterial.get();
            }
            else {
                return "No Data Exists";
            }
        }
        return "You need to SignIn First";
    }
}
