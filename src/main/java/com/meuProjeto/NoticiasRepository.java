package com.meuProjeto;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class NoticiasRepository implements PanacheRepository<Noticias> {

    //Método para buscar todas as notícias cadastradas.
    public List<Noticias> findAllNoticias() {
        return listAll();
    }
    
    @Transactional
    public Noticias salvar(Noticias novaNoticias) {
        persist(novaNoticias);
        return novaNoticias;
    }

}
