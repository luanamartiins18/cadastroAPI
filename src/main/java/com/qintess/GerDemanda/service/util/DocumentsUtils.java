package com.qintess.GerDemanda.service.util;

import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class DocumentsUtils {

    public static byte[] exportToExcell(List<?> list, String tabName, List<String> headers) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(tabName);
        AtomicInteger rowKey = new AtomicInteger(-1);
        generateHeaderXlsx(headers, workbook, sheet, rowKey);
        generateRowsXlsx(list, sheet, rowKey, workbook);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            workbook.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public static byte[] exportToExcell(List<?> list, String tabName, List<String> headers, String subtotal) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(tabName);
        AtomicInteger rowKey = new AtomicInteger(-1);
        generateHeaderXlsx(headers, workbook, sheet, rowKey);
        if (subtotal.equals("SUB_TOTAL_SIGLA")) {
            generateRowsXlsxSubTotalSigla((List<RelatorioDTO>) list, sheet, rowKey, workbook);
        } else {
            generateRowsXlsx(list, sheet, rowKey, workbook);
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            workbook.write(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public static void generateHeaderXlsx(List<String> headers, Workbook workbook, Sheet sheet, AtomicInteger rowKey) {
        Row header = sheet.createRow(rowKey.incrementAndGet());
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        AtomicInteger index = new AtomicInteger(-1);
        headers.stream().forEach(obj -> {
            header.createCell(index.incrementAndGet()).setCellValue(obj);
            sheet.getRow(rowKey.get()).getCell(index.get()).setCellStyle(headerStyle);
        });
        header.setHeight((short) 500);
    }

    public static void generateRowsXlsx(List<?> list, Sheet sheet, AtomicInteger rowKey, Workbook workbook) {
        list.stream().forEach(obj -> {
            Row row = sheet.createRow(rowKey.incrementAndGet());
            AtomicInteger cellKey = new AtomicInteger(-1);
            ReflectionUtils.doWithFields(obj.getClass(), field -> {
                field.setAccessible(true);
                String value = "";
                if (Objects.nonNull(field.get(obj))) {
                    value = field.get(obj).toString();
                }
                row.createCell(cellKey.incrementAndGet()).setCellValue(value);
                sheet.autoSizeColumn(cellKey.get());
            });
        });
    }

    public static void generateRowsXlsxSubTotalSigla(List<RelatorioDTO> list, Sheet sheet, AtomicInteger rowKey, Workbook workbook) {
        IntStream.range(0, list.size())
                .forEach(i -> {
                    if (i > 0 && !list.get(i).getSigla().equals(list.get(i - 1).getSigla())) {
                        List<RelatorioDTO> listFiltradaPorSigla = list.stream()
                                .filter(fil -> fil.getSigla().equals(list.get(i - 1).getSigla()))
                                .collect(Collectors.toList());
                        subTotalSigla(sheet, rowKey, listFiltradaPorSigla, workbook);
                    }
                    Row row = sheet.createRow(rowKey.incrementAndGet());
                    AtomicInteger cellKey = new AtomicInteger(-1);
                    ReflectionUtils.doWithFields(list.get(i).getClass(), field -> {
                        field.setAccessible(true);
                        String value = "";
                        if (Objects.nonNull(field.get(list.get(i)))) {
                            value = field.get(list.get(i)).toString();
                        }
                        row.createCell(cellKey.incrementAndGet()).setCellValue(value);
                        sheet.autoSizeColumn(cellKey.get());
                    });
                });
        if (!list.isEmpty()) {
            List<RelatorioDTO> ultimoBlocoSiglas = list.stream()
                    .filter(fil -> fil.getSigla().equals(list.get(list.size() - 1).getSigla())).collect(Collectors.toList());
            subTotalSigla(sheet, rowKey, ultimoBlocoSiglas, workbook);
            subTotalSigla(sheet, rowKey, list, workbook);
        }
    }

    private static void subTotalSigla(Sheet sheet, AtomicInteger rowKey, List<RelatorioDTO> listFiltrada, Workbook workbook) {
        // groupBy listFiltrada
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Long qtdOf = listFiltrada.stream().count();
        Double ustibbTotal = listFiltrada.stream().mapToDouble(f -> f.getValor_ustibb()).sum();
        Double valorTotal = listFiltrada.stream().mapToDouble(f -> f.getValor()).sum();
        Row row = sheet.createRow(rowKey.incrementAndGet());
        row.createCell(0).setCellValue("SUBTOTAL QTD OF: " + qtdOf);
        row.createCell(3).setCellValue("Ustibb: " + ustibbTotal);
        row.createCell(4).setCellValue("Valor: " + valorTotal);
        row.getCell(0).setCellStyle(headerStyle);
        row.getCell(3).setCellStyle(headerStyle);
        row.getCell(4).setCellStyle(headerStyle);
    }
}