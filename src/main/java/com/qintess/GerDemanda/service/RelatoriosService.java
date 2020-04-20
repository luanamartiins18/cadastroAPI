package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.PersistenceHelper;
import com.qintess.GerDemanda.repositories.RelatorioRepository;
import com.qintess.GerDemanda.service.dto.OrdensConcluidasDTO;
import com.qintess.GerDemanda.service.dto.RelatorioDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RelatoriosService {

    @Autowired
    RelatorioRepository relatorioRepository;

    public List<RelatorioDTO> getRelatorioNovo() {
        return this.relatorioRepository.getRelatorioNovo()
                .stream().map(obj -> new RelatorioDTO(obj)).collect(Collectors.toList());
    }

    // Setar o ID da OF na QUERY

    private List<Object> queryRelatorioOrcamento(int idOf){
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = " select " +
                "usu.nome," +
                "    tg.tarefa," +
                "    cg.descricao as complexidade,    " +
                "    tg.descricao as desc_trf,    " +
                "    count(ig.id) as qtd_artefatos," +
                "    sum(ig.valor) as vlr_ustibb" +
                "    " +
                "    from" +
                "        tarefa_of t  " +
                "    inner join" +
                "        usuario_x_of u   " +
                "            on t.fk_of_usuario = u.id  " +
                "inner join " +
                "usuario usu " +
                "on usu.id = u.fk_usuario " +
                "inner join " +
                "ordem_forn orf " +
                "on orf.id = u.fk_ordem_forn " +
                "    inner join" +
                "        item_guia ig   " +
                "            on ig.id = t.fk_item_guia  " +
                "    inner join" +
                "        complex_guia cg   " +
                "            on cg.id = ig.fk_complex_guia  " +
                "    inner join" +
                "        tarefa_guia tg   " +
                "            on tg.id = ig.fk_tarefa_guia  " +
                "where" +
                "        u.fk_ordem_forn = :idOf " +
                "    group by " +
                "usu.nome," +
                "tg.tarefa," +
                "tg.descricao," +
                "cg.descricao " +
                "order by " +
                "usu.nome, " +
                "tg.fk_disciplina, " +
                "substring_index(substring_index(tg.tarefa, '.', 2), '.', -1)," +
                "substring_index(tg.tarefa, '.', -1), " +
                "cg.id";


        Query query = em.createNativeQuery(sql);
        query.setParameter("idOf", idOf);
        List<Object> res = query.getResultList();
        em.close();
        return res;
    }

    private List<Object> queryRelatorioEntrega(int idOf){
        EntityManagerFactory entityManagerFactory = PersistenceHelper.getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        String sql = "select " +
                "usu.nome," +
                "tg.tarefa," +
                "tg.descricao as desc_trf," +
                "cg.descricao as complexidade," +
                "t.num_tarefa," +
                "t.historia," +
                "t.sprint," +
                "ig.valor," +
                "concat(numero_OF_genti, '/', t.num_tarefa, '/', t.sprint, '/', t.artefato) as alm," +
                "t.artefato  " +
                "from " +
                "   tarefa_of t   " +
                "       inner join " +
                "           usuario_x_of u    " +
                "               on t.fk_of_usuario = u.id   " +
                "       inner join  " +
                "           usuario usu  " +
                "               on usu.id = u.fk_usuario  " +
                "       inner join  " +
                "           ordem_forn orf  " +
                "               on orf.id = u.fk_ordem_forn  " +
                "       inner join " +
                "           item_guia ig    " +
                "               on ig.id = t.fk_item_guia   " +
                "       inner join " +
                "           complex_guia cg    " +
                "               on cg.id = ig.fk_complex_guia   " +
                "       inner join " +
                "           tarefa_guia tg    " +
                "               on tg.id = ig.fk_tarefa_guia   " +
                "where " +
                "   u.fk_ordem_forn = :idOf " +
                "order by  " +
                "   usu.nome,  " +
                "   tg.fk_disciplina,  " +
                "   substring_index(substring_index(tg.tarefa, '.', 2), '.', -1), " +
                "   substring_index(tg.tarefa, '.', -1),  " +
                "   cg.id;";



        Query query = em.createNativeQuery(sql);
        query.setParameter("idOf", idOf);
        List<Object> res = query.getResultList();
        em.close();
        return res;
    }

    public HashMap<String, Object> getRelatorioOrcamento(int idOf){

        List<Object> res = queryRelatorioOrcamento(idOf);

        HashMap<String, Object> relatorioOrcamento = new HashMap<String, Object>();
        relatorioOrcamento.put("listaUsuOf", new ArrayList<Object>());

        HashMap<String, Integer> tarefasInseridas = new HashMap<String, Integer>();
        String ultimoUsuInserido = "";
        Integer indexTarefa = 0;

        for(Object i: res){
            JSONArray atual = new JSONArray(i);

            String nome          = atual.getString(0);
            String tarefa        = atual.getString(1);
            String complexidade  = atual.getString(2);
            String descricaoTrf  = atual.getString(3);
            Integer qtdArtefatos = atual.getInt(4);
            Double valorUstiBB  = atual.getDouble(5);
            ArrayList<Object> listaUsuOf = (ArrayList<Object>) relatorioOrcamento.get("listaUsuOf");

            if(!ultimoUsuInserido.equals(nome)) {
                HashMap<String, Object> novoUsuario = new HashMap<String, Object>();
                novoUsuario.put("nome", nome);
                novoUsuario.put("listaTarefas", new ArrayList<Object>());
                listaUsuOf.add(novoUsuario);

                ultimoUsuInserido = nome;
                tarefasInseridas.clear();
                indexTarefa = 0;
            }

            if(tarefasInseridas.containsKey(tarefa)){
                HashMap<String, Object> item = new HashMap<String, Object>();

                item.put("complexidade", complexidade);
                item.put("descricaoTarefa", descricaoTrf);
                item.put("qtdArtefatos", qtdArtefatos);
                item.put("vlrUstiBB", valorUstiBB);

                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size()-1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>)usuario.get("listaTarefas");
                HashMap<String, Object> tarefaObj = (HashMap<String, Object>)listaTarefas.get(tarefasInseridas.get(tarefa));
                ArrayList<Object> listaItens = (ArrayList<Object>) tarefaObj.get("listaItens");
                listaItens.add(item);

            }else{
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

                ArrayList<Object> listaItens = (ArrayList<Object>)tarefaObj.get("listaItens");
                listaItens.add(item);

                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size()-1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>)usuario.get("listaTarefas");

                listaTarefas.add(tarefaObj);
            }
        }
        return relatorioOrcamento;
    }

    public HashMap<String, Object> getRelatorioEntrega(int idOf){

        List<Object> res = queryRelatorioEntrega(idOf);
        HashMap<String, Object> relatorioEntrega = new HashMap<String, Object>();
        relatorioEntrega.put("listaUsuOf", new ArrayList<Object>());

        HashMap<String, Integer> tarefasInseridas = new HashMap<String, Integer>();
        String ultimoUsuInserido = "";
        Integer indexTarefa = 0;

        for(Object i: res){
            JSONArray atual = new JSONArray(i);

            String nome          = atual.getString(0);
            String tarefa        = atual.getString(1);
            String descricaoTrf  = atual.getString(2);
            String complexidade  = atual.getString(3);
            String numTarefa     = atual.getString(4);
            String historia      = atual.getString(5);
            String sprint        = atual.getString(6);
            Double valorUstiBB   = atual.getDouble(7);
            String alm           = atual.getString(8);
            String artefato      = atual.getString(9);

            ArrayList<Object> listaUsuOf = (ArrayList<Object>) relatorioEntrega.get("listaUsuOf");

            if(!ultimoUsuInserido.equals(nome)) {
                HashMap<String, Object> novoUsuario = new HashMap<String, Object>();
                novoUsuario.put("nome", nome);
                novoUsuario.put("listaTarefas", new ArrayList<Object>());
                listaUsuOf.add(novoUsuario);

                ultimoUsuInserido = nome;
                tarefasInseridas.clear();
                indexTarefa = 0;
            }

            if(tarefasInseridas.containsKey(tarefa)){
                HashMap<String, Object> item = new HashMap<String, Object>();

                item.put("complexidade", complexidade);
                item.put("numTarefa", numTarefa);
                item.put("historia", historia);
                item.put("sprint", sprint);
                item.put("alm", alm);
                item.put("artefato", artefato);
                item.put("descricaoTarefa", descricaoTrf);
                item.put("vlrUstiBB", valorUstiBB);

                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size()-1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>)usuario.get("listaTarefas");
                HashMap<String, Object> tarefaObj = (HashMap<String, Object>)listaTarefas.get(tarefasInseridas.get(tarefa));
                ArrayList<Object> listaItens = (ArrayList<Object>) tarefaObj.get("listaItens");
                listaItens.add(item);

            }else{
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

                ArrayList<Object> listaItens = (ArrayList<Object>)tarefaObj.get("listaItens");
                listaItens.add(item);

                HashMap<String, Object> usuario = (HashMap<String, Object>) listaUsuOf.get(listaUsuOf.size()-1);
                ArrayList<Object> listaTarefas = (ArrayList<Object>)usuario.get("listaTarefas");

                listaTarefas.add(tarefaObj);
            }
        }
        return relatorioEntrega;
    }

    public byte[] getRelatorioOrcamentoXlsx(int idOf){

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
        header.setHeight((short)500);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for(int i=0; i<6; i++){
            sheet.getRow(0).getCell(i).setCellStyle(headerStyle);
        }

        int numRow = 1;
        String ultimoInserido = "";
        Double valorTotal = 0.0;

        for(Object obj: relatorioOrcamento){

            JSONArray atual = new JSONArray(obj);
            String nome          = atual.getString(0);

            if(!ultimoInserido.equals(nome) && numRow != 1){
                Row totalRow = sheet.createRow(numRow++);
                totalRow.createCell(5).setCellValue("Total: " + Double.toString(valorTotal));
                valorTotal = 0.0;
            }
            ultimoInserido = nome;

            String tarefa        = atual.getString(1);
            String complexidade  = atual.getString(2);
            String descricaoTrf  = atual.getString(3);
            Integer qtdArtefatos = atual.getInt(4);
            Double valorUstiBB  = atual.getDouble(5);
            valorTotal += valorUstiBB;



            Row row = sheet.createRow(numRow++);

            row.createCell(0).setCellValue(nome);
            row.createCell(1).setCellValue(tarefa);
            row.createCell(2).setCellValue(complexidade);
            row.createCell(3).setCellValue(descricaoTrf);
            row.createCell(4).setCellValue(qtdArtefatos);
            row.createCell(5).setCellValue(valorUstiBB);
            row.setHeight((short)400);
        }

        Row totalRow = sheet.createRow(numRow);
        totalRow.createCell(5).setCellValue("Total: " + Double.toString(valorTotal));

        for(int i=0; i<6; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(3, 8000);  //Descrição da tarefa pode ser muito grande
        }
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            workbook.write(b);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public byte[] getRelatorioEntregaXlsx(int idOf){

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
        header.setHeight((short)500);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for(int i=0; i<10; i++){
            sheet.getRow(0).getCell(i).setCellStyle(headerStyle);
        }

        int numRow = 1;
        Double valorTotal = 0.0;
        String ultimoNome = "";

        for(Object obj: relatorioEntrega){

            JSONArray atual = new JSONArray(obj);
            String nome          = atual.getString(0);
            String tarefa        = atual.getString(1);
            String descricaoTrf  = atual.getString(2);
            String complexidade  = atual.getString(3);
            String numTarefa     = atual.getString(4);
            String historia      = atual.getString(5);
            String sprint        = atual.getString(6);
            Double valor         = atual.getDouble(7);
            String alm           = atual.getString(8);
            String artefato      = atual.getString(9);



            if(!ultimoNome.equals(nome) && numRow != 1){
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

            row.setHeight((short)400);
        }

        Row totalRow = sheet.createRow(numRow++);
        totalRow.createCell(9).setCellValue("Total: " + Double.toString(valorTotal));


        for(int i=0; i<10; i++){
            sheet.autoSizeColumn(i);
        }
        sheet.setColumnWidth(2, 8000);  //Descrição da tarefa pode ser muito grande

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            workbook.write(b);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public byte[] getRelatorioOrcamentoTxt(int idOf){

        List<Object> relatorioOrcamento = queryRelatorioOrcamento(idOf);

        ArrayList<ArrayList<String>>listaColaboradores = new ArrayList<ArrayList<String>>();
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

        for(Object obj: relatorioOrcamento) {

            JSONArray atual = new JSONArray(obj);

            if(index != 1 && !ultimoUsuario.equals(atual.getString(0))){
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
        for(int i=0; i<listaColaboradores.size(); i++){
            arrColaboradores[i] = "";
        }

        for(int i=0; i<listaColaboradores.get(0).size(); i++){
            int maior = 0;
            for(int j=0; j<listaColaboradores.size(); j++){
                maior = Math.max(maior, listaColaboradores.get(j).get(i).length());
            }

            for(int j=0; j<listaColaboradores.size(); j++){
                String espacoExtra = "";

                if(listaColaboradores.get(j).get(i).length() < maior){
                    for(int k=0; k < (maior - listaColaboradores.get(j).get(i).length()); k++){
                        espacoExtra += " ";
                    }
                }
                if(i == listaColaboradores.get(0).size() - 1 && listaColaboradores.get(j).get(0).equals("")){
                    arrColaboradores[j] += "\n" + listaColaboradores.get(j).get(i) + "\n";

                }else{
                    arrColaboradores[j] += listaColaboradores.get(j).get(i) + espacoExtra + "\t";
                }

            }

        }

        String resColaboradores = "";
        for(int i=0; i<arrColaboradores.length; i++){
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

    public byte[] getRelatorioEntregaTxt(int idOf){

        List<Object> relatorioEntrega = queryRelatorioEntrega(idOf);

        ArrayList<ArrayList<String>>listaColaboradores = new ArrayList<ArrayList<String>>();
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

        for(Object obj: relatorioEntrega) {

            JSONArray atual = new JSONArray(obj);

            if(index != 1 && !ultimoUsuario.equals(atual.getString(0))){
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
        for(int i=0; i<listaColaboradores.size(); i++){
            arrColaboradores[i] = "";
        }

        for(int i=0; i<listaColaboradores.get(0).size(); i++){
            int maior = 0;
            for(int j=0; j<listaColaboradores.size(); j++){
                maior = Math.max(maior, listaColaboradores.get(j).get(i).length());
            }

            for(int j=0; j<listaColaboradores.size(); j++){
                String espacoExtra = "";

                if(listaColaboradores.get(j).get(i).length() < maior){
                    for(int k=0; k < (maior - listaColaboradores.get(j).get(i).length()); k++){
                        espacoExtra += " ";
                    }
                }
                if(i == listaColaboradores.get(0).size() - 1 && listaColaboradores.get(j).get(0).equals("")){
                    arrColaboradores[j] += "\n" + listaColaboradores.get(j).get(i) + "\n";

                }else{
                    arrColaboradores[j] += listaColaboradores.get(j).get(i) + espacoExtra + "\t";
                }

            }

        }

        String resColaboradores = "";
        for(int i=0; i<arrColaboradores.length; i++){
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

    public byte[] getRelatorioXlsx(ArrayList<ArrayList<String>> relatorio, String nomeRelatorio){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(nomeRelatorio);

        for(int i=0; i<relatorio.size(); i++){
            Row row = sheet.createRow(i);

            for(int j=0; j<relatorio.get(i).size(); j++){
                row.createCell(j).setCellValue(relatorio.get(i).get(j));
            }
        }

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            workbook.write(b);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }


        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

    public byte[] getRelatorioTxt(ArrayList<ArrayList<String>> relatorio){

        String[] arrRelatorio = new String[relatorio.size()];
        for(int i=0; i<relatorio.size(); i++){
            arrRelatorio[i] = "";
        }

        for(int i=0; i<relatorio.get(0).size(); i++){
            int maior = 0;
            for(int j=0; j<relatorio.size(); j++){
                maior = Math.max(maior, relatorio.get(j).get(i).length());
            }

            for(int j=0; j<relatorio.size(); j++){
                String espacoExtra = "";

                if(relatorio.get(j).get(i).length() < maior){
                    for(int k=0; k < (maior - relatorio.get(j).get(i).length()); k++){
                        espacoExtra += " ";
                    }
                }
                if(i == relatorio.get(0).size() - 1 && relatorio.get(j).get(0).equals("")){
                    arrRelatorio[j] += "\n" + relatorio.get(j).get(i) + "\n";

                }else{
                    arrRelatorio[j] += relatorio.get(j).get(i) + espacoExtra + "\t";
                }
            }
        }

        String resRelatorio = "";
        for(int i=0; i<arrRelatorio.length; i++){
            resRelatorio += arrRelatorio[i] + "\n";
        }

        ByteArrayOutputStream b = new ByteArrayOutputStream();

        try {
            b.write(resRelatorio.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = b.toByteArray();
        Base64.encodeBase64String(bytes);
        return bytes;
    }

}
