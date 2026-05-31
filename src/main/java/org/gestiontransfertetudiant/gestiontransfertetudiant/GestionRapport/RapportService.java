package org.gestiontransfertetudiant.gestiontransfertetudiant.GestionRapport;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RapportService {

    public byte[] genererExcelTransferts(List<Object[]> data) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Transferts");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Date");
            header.createCell(2).setCellValue("Statut");
            // ...
            int rowNum = 1;
            for (Object[] rowData : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowData[0].toString());
                row.createCell(1).setCellValue(rowData[1].toString());
                row.createCell(2).setCellValue(rowData[2].toString());
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }
}
