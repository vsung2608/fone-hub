package com.example.fone_hub.utils;

import com.example.fone_hub.entity.Brand;
import com.example.fone_hub.entity.Category;
import com.example.fone_hub.entity.Product;
import com.example.fone_hub.entity.product_spec.*;
import com.example.fone_hub.enums.ProductStatus;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelProductHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Product> excelToProducts(InputStream is) {
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Product> products = new ArrayList<>();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row row = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue; // bỏ qua tiêu đề
                }

                Product product = new Product();
                product.setCreateDate(LocalDate.now()); // mặc định ngày tạo
                product.setUpdateDate(LocalDate.now()); // mặc định ngày cập nhật

                product.setName(row.getCell(0).getStringCellValue());
                product.setPrice((long) row.getCell(1).getNumericCellValue());
                product.setDiscount((long) row.getCell(2).getNumericCellValue());
                product.setColor(row.getCell(4).getStringCellValue());
                product.setQuantity((long) row.getCell(5).getNumericCellValue());

                String statusStr = row.getCell(6).getStringCellValue();
                product.setStatus(ProductStatus.valueOf(statusStr));

                String categoryName = row.getCell(7).getStringCellValue();
                Category category = new Category();
                category.setName(categoryName);
                product.setCategory(category);

                String brandName = row.getCell(8).getStringCellValue();
                Brand brand = new Brand();
                brand.setName(brandName);
                product.setBrand(brand);

                product.setGoodsInformation(GoodsInformation.builder()
                                .origin(row.getCell(9).getStringCellValue())
                                .launchDate(row.getCell(10).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                                .warrantyPeriod(Integer.parseInt(String.valueOf(row.getCell(11).getNumericCellValue())))
                        .build());

                product.setDesign(Design.builder()
                                .dimensions(row.getCell(12).getStringCellValue())
                                .weight(Integer.parseInt(String.valueOf(row.getCell(13).getNumericCellValue())))
                                .material(row.getCell(14).getStringCellValue())
                                .waterResistance(row.getCell(15).getStringCellValue())
                        .build());

                product.setDisplay(Display.builder()
                                .size(row.getCell(16).getStringCellValue())
                                .brightness(row.getCell(17).getStringCellValue())
                                .contrastRatio(row.getCell(18).getStringCellValue())
                                .displayResolution(row.getCell(19).getStringCellValue())
                                .glassMaterial(row.getCell(20).getStringCellValue())
                                .standard(row.getCell(21).getStringCellValue())
                                .technology(row.getCell(22).getStringCellValue())
                                .touchType(row.getCell(23).getStringCellValue())
                        .build());

                product.setCpu(Cpu.builder()
                                .ram(Integer.parseInt(String.valueOf(row.getCell(24).getNumericCellValue())))
                                .coreCount(Integer.parseInt(String.valueOf(row.getCell(25).getNumericCellValue())))
                                .coreType(row.getCell(26).getStringCellValue())
                                .cpuModel(row.getCell(27).getStringCellValue())
                                .gpuModel(row.getCell(28).getStringCellValue())
                        .build());

                product.setCamera(Camera.builder()
                                .rearCameraType(row.getCell(29).getStringCellValue())
                                .camera1Type(row.getCell(30).getStringCellValue())
                                .camera1Resolution(row.getCell(31).getStringCellValue())
                                .camera2Type(row.getCell(32).getStringCellValue())
                                .camera2Resolution(row.getCell(33).getStringCellValue())
                                .cameraVideoRecording(row.getCell(34).getStringCellValue())
                                .cameraFeatures(row.getCell(35).getStringCellValue())
                        .build());

                product.setSelfieCamera(SelfieCamera.builder()
                                .selfieCameraType(row.getCell(36).getStringCellValue())
                                .selfieCameraResolution(row.getCell(37).getStringCellValue())
                                .selfieCameraVideoRecording(row.getCell(38).getStringCellValue())
                                .selfieCameraFeatures(row.getCell(39).getStringCellValue())
                        .build());

                product.setConnectivity(Connectivity.builder()
                                .simType(row.getCell(40).getStringCellValue())
                                .simSlotCount(Integer.parseInt(String.valueOf(row.getCell(41).getNumericCellValue())))
                                .networkSupport(row.getCell(42).getStringCellValue())
                                .chargingPort(row.getCell(43).getStringCellValue())
                                .audioJack(row.getCell(44).getStringCellValue())
                                .wifi(row.getCell(45).getStringCellValue())
                                .bluetooth(row.getCell(46).getStringCellValue())
                                .otherConnection(row.getCell(47).getStringCellValue())
                                .gpsSystems(row.getCell(48).getStringCellValue())
                        .build());

                product.setStorage(Storage.builder()
                                .rom(Integer.parseInt(String.valueOf(row.getCell(49).getNumericCellValue())))
                                .hasExternalMemory(row.getCell(50).getStringCellValue())
                                .contactStorage(row.getCell(51).getStringCellValue())
                        .build());

                product.setBattery(Battery.builder()
                                .batteryType(row.getCell(52).getStringCellValue())
                                .batteryLife(row.getCell(53).getStringCellValue())
                                .additionalInfo(row.getCell(54).getStringCellValue())
                        .build());
                products.add(product);
            }

            return products;
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file Excel: " + e.getMessage());
        }
    }
}

