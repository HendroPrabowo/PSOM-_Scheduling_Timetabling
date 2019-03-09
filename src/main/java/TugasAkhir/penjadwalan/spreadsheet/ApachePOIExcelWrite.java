package TugasAkhir.penjadwalan.spreadsheet;

import TugasAkhir.penjadwalan.model.Matakuliah;
import TugasAkhir.penjadwalan.model.Partikel;
import TugasAkhir.penjadwalan.model.Ruangan;
import TugasAkhir.penjadwalan.service.MatakuliahService;
import TugasAkhir.penjadwalan.service.RuanganService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApachePOIExcelWrite {

    public ApachePOIExcelWrite(){

    }

    public void exportExcel(List<Partikel> partikels, List<Matakuliah> matakuliahs, List<Ruangan> ruangans){
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

        // Header excelnya
        Row row = sheet.createRow(rowNum);
        for(String text : header){
            Cell cell = row.createCell(colNum);
            cell.setCellValue(text);
            colNum++;
        }
        rowNum++;

        for(Partikel partikel : partikels){
            row = sheet.createRow(rowNum);
            Matakuliah matakuliah = findMatakuliah(partikel.getIdmatakuliah(), matakuliahs);

            row.createCell(0).setCellValue(partikel.getId());
            row.createCell(1).setCellValue(matakuliah.getNama());

            // Set nama hari
            String hari = new String();
            if((int)partikel.getPosisihari() == 1)
                hari = "Senin";
            else if((int)partikel.getPosisihari() == 2)
                hari = "Selasa";
            else if((int)partikel.getPosisihari() == 3)
                hari = "Rabu";
            else if((int)partikel.getPosisihari() == 4)
                hari = "Kamis";
            else
                hari = "Jumat";
            row.createCell(2).setCellValue(hari);

            // Set nama waktu
            String sesi = new String();
            if((int)partikel.getPosisisesi() == 1)
                sesi = "08:00 - 08:50";
            else if((int)partikel.getPosisisesi() == 2)
                sesi = "09:00 - 09:50";
            else if((int)partikel.getPosisisesi() == 3)
                sesi = "10:00 - 10:50";
            else if((int)partikel.getPosisisesi() == 4)
                sesi = "11:00 - 11:50";
            else if((int)partikel.getPosisisesi() == 5)
                sesi = "13:00 - 13:50";
            else if((int)partikel.getPosisisesi() == 6)
                sesi = "14:00 - 14:50";
            else if((int)partikel.getPosisisesi() == 7)
                sesi = "15:00 - 15:50";
            else
                sesi = "16:00 - 16:50";
            row.createCell(3).setCellValue(sesi);

            String ruangan = findRuanganByPosisi((int)partikel.getPosisiruangan(), ruangans).getNama();
            row.createCell(4).setCellValue(ruangan);
            row.createCell(5).setCellValue(matakuliah.getDosen1());
            row.createCell(6).setCellValue(matakuliah.getDosen2());
            row.createCell(7).setCellValue(matakuliah.getDosen3());
            row.createCell(8).setCellValue(matakuliah.getDosen4());
            row.createCell(9).setCellValue(matakuliah.getAsistendosen1());
            row.createCell(10).setCellValue(matakuliah.getAsistendosen2());
            row.createCell(11).setCellValue(matakuliah.getAsistendosen3());
            row.createCell(12).setCellValue(matakuliah.getKelas1());
            row.createCell(13).setCellValue(matakuliah.getKelas2());
            row.createCell(14).setCellValue(matakuliah.getKelas3());
            row.createCell(15).setCellValue(matakuliah.getKelas4());
            row.createCell(16).setCellValue(matakuliah.getJenis());
            row.createCell(17).setCellValue(partikel.getNilaifitness());
            row.createCell(18).setCellValue(partikel.getKeterangan());
            rowNum++;
        }
        System.out.println("Creating excel");

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

    public Matakuliah findMatakuliah(int id, List<Matakuliah> matakuliahs){
        for (Matakuliah matakuliah : matakuliahs){
            if(matakuliah.getId().equals(id)){
                return matakuliah;
            }
        }
        return null;
    }

    public Ruangan findRuanganByPosisi(int posisi, List<Ruangan> ruangans){
        for (Ruangan ruangan : ruangans){
            if(ruangan.getPosisi().equals(posisi)){
                return ruangan;
            }
        }
        return null;
    }
}
