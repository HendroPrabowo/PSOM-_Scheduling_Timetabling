package TugasAkhir.penjadwalan.spreadsheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApachePOIExcelWrite {
    public ApachePOIExcelWrite(){

    }

    public void exportExcel(){
        String FILE_NAME = "myExcel.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Jadwal");

        List<String> header = new ArrayList<>();
        header.add("ID Partikel");
        header.add("Mata Kuliah");
        header.add("Hari");
        header.add("Waktu");
        header.add("Ruangan");
        header.add("Dosen 1");
        header.add("Dosen 2");
        header.add("Dosen 3");
        header.add("Dosen 4");
        header.add("AsDos 1");
        header.add("AsDos 2");
        header.add("AsDos 3");
        header.add("Kelas 1");
        header.add("Kelas 2");
        header.add("Kelas 3");
        header.add("Kelas 4");
        header.add("Jenis");
        header.add("Nilai Fitness");
        header.add("Keterangan");


        int rowNum = 0;
        int colNum = 0;
        System.out.println("Creating excel");

        Row row = sheet.createRow(0);
        for(String text : header){
            Cell cell = row.createCell(colNum);
            cell.setCellValue(text);
            colNum++;
        }

//        for (Object[] datatype : datatypes) {
//            Row row = sheet.createRow(rowNum++);
//            int colNum = 0;
//            for (Object field : datatype) {
//                Cell cell = row.createCell(colNum++);
//                if (field instanceof String) {
//                    cell.setCellValue((String) field);
//                } else if (field instanceof Integer) {
//                    cell.setCellValue((Integer) field);
//                }
//            }
//        }
//
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
            System.out.println("Done");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
