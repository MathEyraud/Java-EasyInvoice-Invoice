package com.mycompany.invoice.invoice;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

// Annotation qui marque cette classe comme étant une application Spring Boot.
@SpringBootApplication
// Annotation qui indique à Spring de scanner le package spécifié pour trouver les entités JPA.
@EntityScan(basePackages = {"com.mycompany.invoice.core.entity.customer", "com.mycompany.invoice.core.entity.invoice"})
public class InvoiceApplication {

	// Méthode principale qui lance l'application Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}

	// Permet de nullifier les proxy + Récupérer les données transient (sinon ils sont ignorés)
	// Déclaration d'un bean Hibernate5JakartaModule pour configurer Jackson pour Hibernate.
	// Cela permet de gérer les entités Hibernate lors de la sérialisation/désérialisation JSON.	@Bean
	public Hibernate5JakartaModule datatypeHibernateModule(){
		Hibernate5JakartaModule module = new Hibernate5JakartaModule();

		// Désactive l'utilisation de l'annotation @Transient pour ignorer les propriétés non persistées.
		module.disable(Hibernate5JakartaModule.Feature.USE_TRANSIENT_ANNOTATION);

		// Active la sérialisation des identifiants pour les objets Lazy non chargés.
		// Cela permet de sérialiser les objets proxy de manière correcte sans charger les données associées.
		module.enable(Hibernate5JakartaModule.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);

		return module;
	}

	/**
	 * Le RestTemplate est un outil puissant pour effectuer des appels HTTP
	 * dans une application Spring Boot.
	 * En le définissant comme un bean, vous simplifiez son utilisation
	 * dans l'ensemble de votre application,
	 * en permettant de l'injecter facilement là où vous en avez besoin
	 * pour effectuer des appels aux API REST externes.
	 * @return
	 */
	/*@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}*/

	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}

}