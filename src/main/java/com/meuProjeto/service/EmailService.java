package com.meuProjeto.service;

import java.time.LocalDate;
import java.util.List;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.meuProjeto.model.Noticia;

@ApplicationScoped
public class EmailService {
   
@Inject
Mailer mailer; 

   public void enviarEmailParaCliente(String nomeCliente, String emailCliente, List<Noticia> noticias, LocalDate nascimento) {
    if (noticias != null && !noticias.isEmpty()) {
        boolean isAniversario = (nascimento != null && 
                                 nascimento.getMonth() == LocalDate.now().getMonth() && 
                                 nascimento.getDayOfMonth() == LocalDate.now().getDayOfMonth());
        
        String saudacao = "Olá, bom dia " + nomeCliente + "!";
        String mensagemAniversario = isAniversario ? "Feliz Aniversário! \uD83C\uDF89\uD83C\uDF82\uD83E\uDD73" : "";

        StringBuilder corpoEmail = new StringBuilder();
        corpoEmail.append("<html><head><meta charset=\"UTF-8\"></head><body>");
        corpoEmail.append("<p>").append(saudacao).append("</p>");

        if (!mensagemAniversario.isEmpty()) {
            corpoEmail.append("<p><strong>").append(mensagemAniversario).append("</strong></p>");
        }
        
        corpoEmail.append("<p>Estas são as Notícias do dia:</p>");
        int contador = 1;

        for (Noticia noticia : noticias) {
            corpoEmail.append("<p><strong>\uD83D\uDCE0 Notícia ").append(contador).append(": </strong></p>");
            corpoEmail.append("<p><strong>Título: </strong>");
            
            // Verifica se a URL existe e cria o link no título
            if (noticia.getUrl() != null && !noticia.getUrl().isEmpty()) {
                corpoEmail.append("<a href=\"").append(noticia.getUrl()).append("\">")
                          .append(noticia.getTitulo())
                          .append("</a>");
            } else {
                corpoEmail.append(noticia.getTitulo());
            }
            corpoEmail.append("</p>");

            corpoEmail.append("<p><strong>Descrição: </strong>").append(noticia.getDescricao()).append("</p>");

            if (noticia.getUrl() != null && !noticia.getUrl().isEmpty()) {
                corpoEmail/*.append("<p><strong>URL: </strong><a href=\"").append(noticia.getUrl()).append("\">")
                          /.append(noticia.getUrl())*/
                          .append("</a></p>");
            }

            corpoEmail.append("<br>---------------------------------------");
            contador++;
        }

        corpoEmail.append("</body></html>");
        mailer.send(Mail.withHtml(emailCliente, "Notícias do dia!", corpoEmail.toString()));
    }
   }
}

