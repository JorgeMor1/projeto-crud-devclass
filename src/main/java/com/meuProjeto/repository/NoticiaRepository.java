package com.meuProjeto.repository;

import java.util.List;

import com.meuProjeto.model.Noticia;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class NoticiaRepository implements PanacheRepository<Noticia> {

    @PersistenceContext
    EntityManager entityManager;

    public List<Noticia> findAllNoticias() {
        return listAll();
    }
    
    @Transactional
    public Noticia salvar(Noticia novaNoticia) {
        persist(novaNoticia);
        return novaNoticia;
    }

    
    public List<Noticia> getNoticiasNaoProcessadas(int page, int size, String sort) {
        String jpql = "SELECT n FROM Noticia n WHERE n.processada = false ORDER BY n." + sort;
        return entityManager.createQuery(jpql, Noticia.class)
        .setFirstResult(page * size)  
                        .setMaxResults(size)          
                        .getResultList();
                    
    }
    
}
