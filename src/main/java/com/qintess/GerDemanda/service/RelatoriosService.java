package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.model.ValorUstibb;
import com.qintess.GerDemanda.repositories.TarefaOfRepository;
import com.qintess.GerDemanda.repositories.ValorUstibbRepository;
import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import com.qintess.GerDemanda.service.util.DocumentsUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RelatoriosService {

    @Autowired
    TarefaOfRepository tarefaOfRepository;

    @Autowired
    ValorUstibbRepository valorUstibbRepository;
    // Setar o ID da OF na QUERY

    private List<Object> queryRelatorioOrcamento(int idOf) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = " SELECT " +
                " usu.nome, " +
                " tg.tarefa, " +
                " cg.descricao AS complexidade, " +
                " tg.descricao AS desc_trf, " +
                " count(ig.id) AS qtd_artefatos, " +
                " sum(ig.valor) AS vlr_ustibb " +
                " FROM tarefa_of t " +
                " INNER JOIN usuario_x_of u ON t.fk_of_usuario = u.id " +
                " INNER JOIN  usuario usu ON usu.id = u.fk_usuario " +
                " INNER JOIN ordem_forn orf ON orf.id = u.fk_ordem_forn " +
                " INNER JOIN item_guia ig ON ig.id = t.fk_item_guia " +
                " INNER JOIN complex_guia cg ON cg.id = ig.fk_complex_guia " +
                " INNER JOIN tarefa_guia tg ON tg.id = ig.fk_tarefa_guia " +
                " WHERE u.fk_ordem_forn = :idOf " +
                " GROUP BY " +
                " usu.nome, " +
                " tg.tarefa, " +
                " tg.descricao, " +
                " cg.descricao  " +
                " ORDER BY" +
                " usu.nome,  " +
                " tg.fk_disciplina, " +
                " substring_index(substring_index(tg.tarefa, '.', 2), '.', -1), " +
                " substring_index(tg.tarefa, '.', -1), " +
                " cg.id ";
        Query query = em.createNativeQuery(sql);
        query.setParameter("idOf", idOf);
        List<Object> res = query.getResultList();
        em.close();
        return res;
    }

    private List<Object> queryRelatorioEntrega(int idOf) {
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        String sql = " SELECT " +
                " usu.nome, " +
                " tg.tarefa, " +
                " tg.descricao AS desc_trf, " +
                " cg.descricao AS complexidade, " +
                " t.num_tarefa, " +
                " t.historia, " +
                " t.sprint, " +
                " ig.valor, " +
                " concat(numero_OF_genti, '/', t.num_tarefa, '/', t.sprint, '/', t.artefato) AS alm, " +
                " t.artefato " +
                " FROM tarefa_of t " +
                " INNER JOIN usuario_x_of u ON t.fk_of_usuario = u.id " +
                " INNER JOIN usuario usu ON usu.id = u.fk_usuario " +
                " INNER JOIN ordem_forn orf ON orf.id = u.fk_ordem_forn " +
                " INNER JOIN item_guia ig ON ig.id = t.fk_item_guia " +
                " INNER JOIN complex_guia cg ON cg.id = ig.fk_complex_guia " +
                " INNER JOIN tarefa_guia tg ON tg.id = ig.fk_tarefa_guia " +
                " WHERE u.fk_ordem_forn = :idOf " +
                " ORDER BY " +
                " usu.nome, " +
                " tg.fk_disciplina, " +
                " substring_index(substring_index(tg.tarefa, '.', 2), '.', -1), " +
                " substring_index(tg.tarefa, '.', -1), " +
                " cg.id ";
        Query query = em.createNativeQuery(sql);
        query.setParameter("idOf", idOf);
        List<Object> res = query.getResultList();
        em.close();
        return res;
    }

    public HashMap<String, Object> getRelatorioOrcamento(int idOf) {
        List<Object> res = queryRelatorioOrcamento(idOf);
        HashMap<String, Object> relatorioOrcamento = new HashMap<String, Object>();
        relatorioOrcamento.put("listaUsuOf", new ArrayList<Object>());
        HashMap<String, Integer> tarefasInseridas = new HashMap<String, Integer>();
        String ultimoUsuInserido = "";
        Integer indexTarefa = 0;
        for (Object i : res) {
            JSONArray atual = new JSONArray(i);
            String nome = atual.getString(0);
            String tarefa = atual.getString(1);
            String complexidade = atual.getString(2);
            String descricaoTrf = atual.getString(3);
            Integer qtdArtefatos = atual.getInt(4);
            Double valorUstiBB = atual.getDouble(5);
            ArrayList<Object> listaUsuOf = (ArrayList<Object>) relatorioOrcamento.get("listaUsuOf");
            if (!ultimoUsuInserido.equals(nome)) {
                HashMap<String, Object> novoUsuario = new HashMap<String, Object>();
                novoUsuario.put("nome", nome);
                novoUsuario.put("listaTarefas", new ArrayList<Object>());
                listaUsuOf.add(novoUsuario);
                ultimoUsuInserido = nome;
                tarefasInseridas.clear();
                indexTarefa = 0;
            }
            if (tarefasInseridas.containsKey(tarefa)) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("complexidade", complexidade);
                item.put("descricaoTarefa", descricaoTrf);
                item.put("qtdArtefatos", qtdArtefatos);
                item.put("vlrUstiBB", valorUstiBB);
                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size() - 1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>) usuario.get("listaTarefas");
                HashMap<String, Object> tarefaObj = (HashMap<String, Object>) listaTarefas.get(tarefasInseridas.get(tarefa));
                ArrayList<Object> listaItens = (ArrayList<Object>) tarefaObj.get("listaItens");
                listaItens.add(item);
            } else {
                tarefasInseridas.put(tarefa, indexTarefa);
                indexTarefa++;
                HashMap<String, Object> item = new HashMap<String, Object>();
                HashMap<String, Object> tarefaObj = new HashMap<String, Object>();
                tarefaObj.put("tarefa", tarefa);
                tarefaObj.put("listaItens", new ArrayList<Object>());
                item.put("complexidade", complexidade);
                item.put("descricaoTarefa", descricaoTrf);
                item.put("qtdArtefatos", qtdArtefatos);
                item.put("vlrUstiBB", valorUstiBB);
                ArrayList<Object> listaItens = (ArrayList<Object>) tarefaObj.get("listaItens");
                listaItens.add(item);
                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size() - 1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>) usuario.get("listaTarefas");
                listaTarefas.add(tarefaObj);
            }
        }
        return relatorioOrcamento;
    }

    public HashMap<String, Object> getRelatorioEntrega(int idOf) {
        List<Object> res = queryRelatorioEntrega(idOf);
        HashMap<String, Object> relatorioEntrega = new HashMap<String, Object>();
        relatorioEntrega.put("listaUsuOf", new ArrayList<Object>());
        HashMap<String, Integer> tarefasInseridas = new HashMap<String, Integer>();
        String ultimoUsuInserido = "";
        Integer indexTarefa = 0;
        for (Object i : res) {
            JSONArray atual = new JSONArray(i);
            String nome = atual.getString(0);
            String tarefa = atual.getString(1);
            String descricaoTrf = atual.getString(2);
            String complexidade = atual.getString(3);
            String numTarefa = atual.getString(4);
            String historia = atual.getString(5);
            String sprint = atual.getString(6);
            Double valorUstiBB = atual.getDouble(7);
            String alm = atual.getString(8);
            String artefato = atual.getString(9);
            ArrayList<Object> listaUsuOf = (ArrayList<Object>) relatorioEntrega.get("listaUsuOf");
            if (!ultimoUsuInserido.equals(nome)) {
                HashMap<String, Object> novoUsuario = new HashMap<String, Object>();
                novoUsuario.put("nome", nome);
                novoUsuario.put("listaTarefas", new ArrayList<Object>());
                listaUsuOf.add(novoUsuario);
                ultimoUsuInserido = nome;
                tarefasInseridas.clear();
                indexTarefa = 0;
            }
            if (tarefasInseridas.containsKey(tarefa)) {
                HashMap<String, Object> item = new HashMap<String, Object>();
                item.put("complexidade", complexidade);
                item.put("numTarefa", numTarefa);
                item.put("historia", historia);
                item.put("sprint", sprint);
                item.put("alm", alm);
                item.put("artefato", artefato);
                item.put("descricaoTarefa", descricaoTrf);
                item.put("vlrUstiBB", valorUstiBB);
                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size() - 1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>) usuario.get("listaTarefas");
                HashMap<String, Object> tarefaObj = (HashMap<String, Object>) listaTarefas.get(tarefasInseridas.get(tarefa));
                ArrayList<Object> listaItens = (ArrayList<Object>) tarefaObj.get("listaItens");
                listaItens.add(item);
            } else {
                tarefasInseridas.put(tarefa, indexTarefa);
                indexTarefa++;
                HashMap<String, Object> item = new HashMap<String, Object>();
                HashMap<String, Object> tarefaObj = new HashMap<String, Object>();
                tarefaObj.put("tarefa", tarefa);
                tarefaObj.put("listaItens", new ArrayList<Object>());
                item.put("complexidade", complexidade);
                item.put("numTarefa", numTarefa);
                item.put("historia", historia);
                item.put("sprint", sprint);
                item.put("alm", alm);
                item.put("artefato", artefato);
                item.put("descricaoTarefa", descricaoTrf);
                item.put("vlrUstiBB", valorUstiBB);
                ArrayList<Object> listaItens = (ArrayList<Object>) tarefaObj.get("listaItens");
                listaItens.add(item);
                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size() - 1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>) usuario.get("listaTarefas");
                listaTarefas.add(tarefaObj);
            }
        }
        return relatorioEntrega;
    }

    public byte[] getRelatorioOrcamentoXlsx(int idOf) {
        List<Object> relatorioOrcamento = queryRelatorioOrcamento(idOf);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Orçamento");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Colaborador");
        header.createCell(1).setCellValue("Tarefa");
        header.createCell(2).setCellValue("Complexidade");
        header.createCell(3).setCellValue("Descrição da Tarefa");
        header.createCell(4).setCellValue("Quantidade de Artefatos");
        header.createCell(5).setCellValue("Valor USTI BB");
        header.setHeight((short) 500);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for (int i = 0; i < 6; i++) {
            sheet.getRow(0).getCell(i).setCellStyle(headerStyle);
        }
        int numRow = 1;
        String ultimoInserido = "";
        Double valorTotal = 0.0;
        for (Object obj : relatorioOrcamento) {
            JSONArray atual = new JSONArray(obj);
            String nome = atual.getString(0);
            if (!ultimoInserido.equals(nome) && numRow != 1) {
                Row totalRow = sheet.createRow(numRow++);
                totalRow.createCell(5).setCellValue("Total: " + Double.toString(valorTotal));
                valorTotal = 0.0;
            }
            ultimoInserido = nome;
            String tarefa = atual.getString(1);
            String complexidade = atual.getString(2);
            String descricaoTrf = atual.getString(3);
            Integer qtdArtefatos = atual.getInt(4);
            Double valorUstiBB = atual.getDouble(5);
            valorTotal += valorUstiBB;
            Row row = sheet.createRow(numRow++);
            row.createCell(0).setCellValue(nome);
            row.createCell(1).setCellValue(tarefa);
            row.createCell(2).setCellValue(complexidade);
            row.createCell(3).setCellValue(descricaoTrf);
            row.createCell(4).setCellValue(qtdArtefatos);
            row.createCell(5).setCellValue(valorUstiBB);
            row.setHeight((short) 400);
        }
        Row totalRow = sheet.createRow(numRow);
        totalRow.createCell(5).setCellValue("Total: " + Double.toString(valorTotal));
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(3, 8000);  //Descrição da tarefa pode ser muito grande
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

    public byte[] getRelatorioEntregaXlsx(int idOf) {
        List<Object> relatorioEntrega = queryRelatorioEntrega(idOf);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Entrega");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Colaborador");
        header.createCell(1).setCellValue("Tarefa");
        header.createCell(2).setCellValue("Descricao da Tarefa");
        header.createCell(3).setCellValue("Complexidade");
        header.createCell(4).setCellValue("Número da Tarefa");
        header.createCell(5).setCellValue("História");
        header.createCell(6).setCellValue("Sprint");
        header.createCell(7).setCellValue("Valor USTI BB");
        header.createCell(8).setCellValue("ALM");
        header.createCell(9).setCellValue("Artefato");
        header.setHeight((short) 500);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for (int i = 0; i < 10; i++) {
            sheet.getRow(0).getCell(i).setCellStyle(headerStyle);
        }
        int numRow = 1;
        Double valorTotal = 0.0;
        String ultimoNome = "";
        for (Object obj : relatorioEntrega) {
            JSONArray atual = new JSONArray(obj);
            String nome = atual.getString(0);
            String tarefa = atual.getString(1);
            String descricaoTrf = atual.getString(2);
            String complexidade = atual.getString(3);
            String numTarefa = atual.getString(4);
            String historia = atual.getString(5);
            String sprint = atual.getString(6);
            Double valor = atual.getDouble(7);
            String alm = atual.getString(8);
            String artefato = atual.getString(9);
            if (!ultimoNome.equals(nome) && numRow != 1) {
                Row totalRow = sheet.createRow(numRow++);
                totalRow.createCell(9).setCellValue("Total: " + Double.toString(valorTotal));
                valorTotal = 0.0;
            }
            valorTotal += valor;
            ultimoNome = nome;
            Row row = sheet.createRow(numRow++);
            row.createCell(0).setCellValue(nome);
            row.createCell(1).setCellValue(tarefa);
            row.createCell(2).setCellValue(descricaoTrf);
            row.createCell(3).setCellValue(complexidade);
            row.createCell(4).setCellValue(numTarefa);
            row.createCell(5).setCellValue(historia);
            row.createCell(6).setCellValue(sprint);
            row.createCell(7).setCellValue(artefato);
            row.createCell(8).setCellValue(alm);
            row.createCell(9).setCellValue(valor);
            row.setHeight((short) 400);
        }
        Row totalRow = sheet.createRow(numRow++);
        totalRow.createCell(9).setCellValue("Total: " + Double.toString(valorTotal));
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }
        sheet.setColumnWidth(2, 8000);  //Descrição da tarefa pode ser muito grande
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

    public byte[] getRelatorioOrcamentoTxt(int idOf) {
        List<Object> relatorioOrcamento = queryRelatorioOrcamento(idOf);
        ArrayList<ArrayList<String>> listaColaboradores = new ArrayList<ArrayList<String>>();
        listaColaboradores.add(new ArrayList<String>());
        listaColaboradores.get(0).add("Colaborador");
        listaColaboradores.get(0).add("Tarefa");
        listaColaboradores.get(0).add("Complexidade");
        listaColaboradores.get(0).add("Descrição");
        listaColaboradores.get(0).add("Quantidade de Artefatos");
        listaColaboradores.get(0).add("USTI BB\n");
        int index = 1;
        String ultimoUsuario = "";
        Double total = 0.0, totalGeral = 0.0;
        for (Object obj : relatorioOrcamento) {
            JSONArray atual = new JSONArray(obj);
            if (index != 1 && !ultimoUsuario.equals(atual.getString(0))) {
                listaColaboradores.add(new ArrayList<String>());
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("Total: " + Double.toString(total));
                total = 0.0;
                index++;
                ultimoUsuario = atual.getString(0);
            }
            total += atual.getDouble(5);
            totalGeral += atual.getDouble(5);
            listaColaboradores.add(new ArrayList<String>());
            listaColaboradores.get(index).add(atual.getString(0));
            listaColaboradores.get(index).add(atual.getString(1));
            listaColaboradores.get(index).add(atual.getString(2));
            listaColaboradores.get(index).add(atual.getString(3));
            listaColaboradores.get(index).add(Integer.toString(atual.getInt(4)));
            listaColaboradores.get(index).add(Double.toString(atual.getDouble(5)));
            index++;
        }
        listaColaboradores.add(new ArrayList<String>());
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("Subtotal por Colaborador: " + Double.toString(total));
        index++;
        listaColaboradores.add(new ArrayList<String>());
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("\nTotal Geral: " + Double.toString(totalGeral));
        String[] arrColaboradores = new String[listaColaboradores.size()];
        for (int i = 0; i < listaColaboradores.size(); i++) {
            arrColaboradores[i] = "";
        }
        for (int i = 0; i < listaColaboradores.get(0).size(); i++) {
            int maior = 0;
            for (int j = 0; j < listaColaboradores.size(); j++) {
                maior = Math.max(maior, listaColaboradores.get(j).get(i).length());
            }
            for (int j = 0; j < listaColaboradores.size(); j++) {
                String espacoExtra = "";
                if (listaColaboradores.get(j).get(i).length() < maior) {
                    for (int k = 0; k < (maior - listaColaboradores.get(j).get(i).length()); k++) {
                        espacoExtra += " ";
                    }
                }
                if (i == listaColaboradores.get(0).size() - 1 && listaColaboradores.get(j).get(0).equals("")) {
                    arrColaboradores[j] += "\n" + listaColaboradores.get(j).get(i) + "\n";
                } else {
                    arrColaboradores[j] += listaColaboradores.get(j).get(i) + espacoExtra + "\t";
                }
            }
        }
        String resColaboradores = "";
        for (int i = 0; i < arrColaboradores.length; i++) {
            resColaboradores += arrColaboradores[i] + "\n";
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            b.write(resColaboradores.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public byte[] getRelatorioEntregaTxt(int idOf) {
        List<Object> relatorioEntrega = queryRelatorioEntrega(idOf);
        ArrayList<ArrayList<String>> listaColaboradores = new ArrayList<ArrayList<String>>();
        listaColaboradores.add(new ArrayList<String>());
        listaColaboradores.get(0).add("Colaborador");
        listaColaboradores.get(0).add("Tarefa");
        listaColaboradores.get(0).add("Descrição");
        listaColaboradores.get(0).add("Complexidade");
        listaColaboradores.get(0).add("Número da Tarefa");
        listaColaboradores.get(0).add("História");
        listaColaboradores.get(0).add("Sprint");
        listaColaboradores.get(0).add("USTI BB");
        listaColaboradores.get(0).add("ALM");
        listaColaboradores.get(0).add("Artefato\n");
        int index = 1;
        String ultimoUsuario = "";
        Double total = 0.0, totalGeral = 0.0;
        for (Object obj : relatorioEntrega) {
            JSONArray atual = new JSONArray(obj);
            if (index != 1 && !ultimoUsuario.equals(atual.getString(0))) {
                listaColaboradores.add(new ArrayList<String>());
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("");
                listaColaboradores.get(index).add("Total: " + Double.toString(total));
                total = 0.0;
                index++;
                ultimoUsuario = atual.getString(0);
            }
            total += atual.getDouble(7);
            totalGeral += atual.getDouble(7);
            listaColaboradores.add(new ArrayList<String>());
            listaColaboradores.get(index).add(atual.getString(0));
            listaColaboradores.get(index).add(atual.getString(1));
            listaColaboradores.get(index).add(atual.getString(2));
            listaColaboradores.get(index).add(atual.getString(3));
            listaColaboradores.get(index).add(atual.getString(4));
            listaColaboradores.get(index).add(atual.getString(5));
            listaColaboradores.get(index).add(atual.getString(6));
            listaColaboradores.get(index).add(Double.toString(atual.getDouble(7)));
            listaColaboradores.get(index).add(atual.getString(8));
            listaColaboradores.get(index).add(atual.getString(9));
            index++;
        }
        listaColaboradores.add(new ArrayList<String>());
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("Subtotal por Colaborador: " + Double.toString(total));
        index++;
        listaColaboradores.add(new ArrayList<String>());
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("");
        listaColaboradores.get(index).add("\nTotal Geral: " + Double.toString(totalGeral));
        String[] arrColaboradores = new String[listaColaboradores.size()];
        for (int i = 0; i < listaColaboradores.size(); i++) {
            arrColaboradores[i] = "";
        }
        for (int i = 0; i < listaColaboradores.get(0).size(); i++) {
            int maior = 0;
            for (int j = 0; j < listaColaboradores.size(); j++) {
                maior = Math.max(maior, listaColaboradores.get(j).get(i).length());
            }
            for (int j = 0; j < listaColaboradores.size(); j++) {
                String espacoExtra = "";
                if (listaColaboradores.get(j).get(i).length() < maior) {
                    for (int k = 0; k < (maior - listaColaboradores.get(j).get(i).length()); k++) {
                        espacoExtra += " ";
                    }
                }
                if (i == listaColaboradores.get(0).size() - 1 && listaColaboradores.get(j).get(0).equals("")) {
                    arrColaboradores[j] += "\n" + listaColaboradores.get(j).get(i) + "\n";

                } else {
                    arrColaboradores[j] += listaColaboradores.get(j).get(i) + espacoExtra + "\t";
                }
            }
        }
        String resColaboradores = "";
        for (int i = 0; i < arrColaboradores.length; i++) {
            resColaboradores += arrColaboradores[i] + "\n";
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            b.write(resColaboradores.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public List<RelatorioDTO> getRelatorioSiglaReferenciaReduzido() {
        ValorUstibb valorUstibb = this.valorUstibbRepository.findByAtivo(1);
        return this.tarefaOfRepository.getRelatorioSiglaReferenciaReduzido().stream()
                .peek(obj -> obj.setValor((valorUstibb.getValor()) * obj.getValor_ustibb()))
                .collect(Collectors.toList());
    }


    public List<RelatorioDTO> getRelatorioSiglaReferenciaExpandido() {
        ValorUstibb valorUstibb = this.valorUstibbRepository.findByAtivo(1);
        return this.tarefaOfRepository.getRelatorioSiglaReferenciaExpandido().stream()
                .peek(obj -> obj.setValor((valorUstibb.getValor()) * obj.getValor_ustibb()))
                .collect(Collectors.toList());
    }

    public List<RelatorioDTO> getRelatorioSiglaReferencia() {
        ValorUstibb valorUstibb = this.valorUstibbRepository.findByAtivo(1);
        return this.tarefaOfRepository.getRelatorioSiglaReferencia().stream()
                .peek(obj -> obj.setValor((valorUstibb.getValor()) * obj.getValor_ustibb()))
                .collect(Collectors.toList());
    }

    public byte[] getRelatoriosXlsx() {
        List<String> headers = Arrays.asList("OF", "Colaborador", "Status", "Valor USTIBB", "Valor", "Referencia", "Sigla");
        List<RelatorioDTO> relatorioDTOS = getRelatorioSiglaReferencia();
        return DocumentsUtils.exportToExcell(relatorioDTOS, "Relatório com Filtro", headers, "SUB_TOTAL_SIGLA");
    }
}