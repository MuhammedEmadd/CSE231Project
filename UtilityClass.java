package com.company;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class UtilityClass {
    int i = 0;
    String [] dataArray = new String[5];
    boolean condition, again = false;
    private ArrayList<Fields> fieldsArrayList = new ArrayList<>();
    private ArrayList<Fields> fieldsArrayList2 = new ArrayList<>();
    private ArrayList<Fields> fieldsArrayList3 = new ArrayList<>();
    private ArrayList<ArrayList<Fields>> arrayListOfFieldsArrayList = new ArrayList<>();
    private static String filePath;
    private ArrayList<String> APINames = new ArrayList<>();
    public UtilityClass() {
    }
    public static void setFilePath (String newFilePath) {
        filePath = newFilePath;
    }

    public ArrayList<ArrayList<Fields>> readExcel() throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator iterator1 = sheet.iterator();
        while (iterator1.hasNext()) {
            XSSFRow row = (XSSFRow) iterator1.next();
            try {
                XSSFCell APICell = row.getCell(0);
                if (APICell.getStringCellValue().contains("REST Operation Mapping")) {
                    APINames.add(APICell.getStringCellValue().substring((APICell.getStringCellValue().indexOf('(') + 1), APICell.getStringCellValue().lastIndexOf(')')));
                }
            }
            catch (NullPointerException nullPointerException) {
            }
            Iterator cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                XSSFCell cell = (XSSFCell) cellIterator.next();
                if ((cell.getStringCellValue().equalsIgnoreCase("Mandatory"))) {
                    condition = true;
                    while (iterator1.hasNext()) {
                        row = (XSSFRow) iterator1.next();
                        Iterator cellIterator2 = row.cellIterator();
                        while (cellIterator2.hasNext()) {
                            XSSFCell cell2 = (XSSFCell) cellIterator2.next();
                            if (cell2.getStringCellValue().contains("REST Operation Mapping")) {
                                APINames.add(cell2.getStringCellValue().substring((cell2.getStringCellValue().indexOf('(') + 1), cell2.getStringCellValue().lastIndexOf(')')));
                                if (again) {
                                    fieldsArrayList3.clear();
                                    for (int k = 0; k<fieldsArrayList.size(); k++){
                                        fieldsArrayList3.add(fieldsArrayList.get(k));
                                    }
                                    arrayListOfFieldsArrayList.add(fieldsArrayList3);
                                }
                                else {
                                    fieldsArrayList2.clear();
                                    for (int k = 0; k<fieldsArrayList.size(); k++){
                                        fieldsArrayList2.add(fieldsArrayList.get(k));
                                    }
                                    arrayListOfFieldsArrayList.add(fieldsArrayList2);
                                }
                                fieldsArrayList.clear();
                                condition = false;
                                again = true;
                                continue;
                            }
                            else if (condition) {
                                dataArray[i] = cell2.getStringCellValue();
                                i++;
                            }
                            else if ((cell2.getStringCellValue().equalsIgnoreCase("Mandatory"))) {
                                condition = true;
                                continue;
                            }
                            else {
                                continue;
                            }
                        }
                        if (condition) {
                            i = 0;
                            fieldsArrayList.add(new Fields(dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4]));
                        }
                    }
                }
            }
        }
        arrayListOfFieldsArrayList.add(fieldsArrayList);
        return arrayListOfFieldsArrayList;
    }
    public void printData(){
        for (int counter = 0; counter<arrayListOfFieldsArrayList.size(); counter++)
        {
            System.out.println(APINames.get(counter));
            for (int j = 0; j < arrayListOfFieldsArrayList.get(counter).size(); j++) {
                System.out.println(arrayListOfFieldsArrayList.get(counter).get(j).toString());
            }
            System.out.println(arrayListOfFieldsArrayList.get(counter).size());
        }
    }

    public ArrayList<String> getAPINames() {
        return APINames;
    }
}

