package com.crispysoft.tracky.upload;

import com.crispysoft.tracky.model.Product;
import com.crispysoft.tracky.repo.FoodyRepo;
import com.crispysoft.tracky.repo.ProductRepo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

/**
 * Created by kristina on 26.09.2016.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.crispysoft.tracky")
public class ProductUpload {

    @Autowired
    private ProductRepo productRepo;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProductUpload.class);
        context.close();
    }

    @PostConstruct
    public void getFoodTable() {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(ProductUpload.class.getClassLoader().getResourceAsStream("food.xls"));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for (int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if (tmp > cols) cols = tmp;
                }
            }

            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                Product info = new Product();
                if (row != null) {
                    for (int c = 0; c < cols; c++) {
                        cell = row.getCell(c);
                        if (cell != null) {
                            if (c == 0) {
                                info.setRuname(cell.getStringCellValue());
                            } else if (c == 1) {
                                info.setProtein(cell.getNumericCellValue());
                            } else if (c == 2) {
                                info.setFat(cell.getNumericCellValue());
                            } else if (c == 3) {
                                info.setCarbs(cell.getNumericCellValue());
                            } else if (c == 4) {
                                info.setCalories(cell.getNumericCellValue());
                            } else if (c == 5) {
                                info.setName(cell.getStringCellValue());
                            }
                            // Your code here
                        }
                    }

                    System.out.println(info.getCalories() + " " + info.getName());
                  //  productRepo.save(info);
                }
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

}