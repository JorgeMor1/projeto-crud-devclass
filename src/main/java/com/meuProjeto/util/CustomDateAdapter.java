package com.meuProjeto.util;

import jakarta.json.bind.adapter.JsonbAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


// Esei essa classe,  para implementar o JsonbAdapter para adaptar LocalDate ao formato de string no JSON
public class CustomDateAdapter implements JsonbAdapter<LocalDate, String> {

    @Override
    public String adaptToJson(LocalDate obj) throws Exception {
            if (obj != null) {
                return obj.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
            }
            return null;
        }

        @Override
        public LocalDate adaptFromJson(String obj) throws Exception {
            if (obj == null || obj.isEmpty()) {
                return null; 
            }
        
            // Limpar a string e remove caracteres não numéricos
            String cleanInput = obj.replaceAll("[^0-9]", ""); 
        
            if (cleanInput.length() != 8) {
                throw new IllegalArgumentException("Erro: A data deve ter exatamente 8 caracteres no formato ddMMyyyy. Entrada fornecida: '" + obj + "'");
            }
            if (!cleanInput.matches("\\d{8}")) {
                throw new IllegalArgumentException("Erro: A data fornecida contém caracteres inválidos. Apenas números são permitidos. Entrada fornecida: '" + obj + "'");
            }
        
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                return LocalDate.parse(cleanInput, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Erro: Formato de data inválido. Use ddMMyyyy (exemplo: 08052024). Entrada fornecida: '" + obj + "'", e);
            }
        }
        
}



