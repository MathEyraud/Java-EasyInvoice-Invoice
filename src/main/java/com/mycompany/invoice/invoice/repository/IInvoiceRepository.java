package com.mycompany.invoice.invoice.repository;

import com.mycompany.invoice.core.entity.invoice.Invoice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IInvoiceRepository extends CrudRepository<Invoice, String> {

    /**
     * AVEC L'UTILISATION DE SPRING DATA JDBC
     * IL N'EST PLUS NECESSAIRE D'AVOIR UN REPOSITORY
     * C'EST L'INTERFACE QUI VA ETENDRE UN CRUD REPOSITORY
     */
    /*
    Invoice create(Invoice invoice);
    List<Invoice> getListInvoice();
    public Invoice getInvoiceByNumber(String number);
     */

    /**
     * Pour éviter les problèmes liée au LazyLoading
     * On peut redéfinir les méthodes
     * EntityGraph permet de spécifier quelles associations doivent être chargées,
     * en utilisant soit des annotations sur les entités, soit
     * dynamiquement dans vos requêtes.
     */
    /*@EntityGraph(value = "invoice.customer", type = EntityGraph.EntityGraphType.FETCH)
    Iterable<Invoice> findAll();

    @EntityGraph(value = "invoice.customer", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Invoice> findById(String number);*/

    /**
     * @EntityGraph N'est plus necessaire car on passe par des microservice.
     * */

}