package com.hystrixmicroservice.hystrix.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductServiceDelegate {
    @Autowired
    RestTemplate restTemplate;

    // implement a callStudentService method and enabled it by hystrix. we wanna return student detail with a normal flow
    // this a http exchange

    @HystrixCommand(fallbackMethod = "listeProduits_Fallback")
    public String listeProduits(){

        String response = restTemplate.exchange("http://localhost:8033/getProduits"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
                }).getBody();
        return "NORMAL FLOW !!! - Product Details : "+response+" -";
    }

    @SuppressWarnings("unused")
    private String listeProduits_Fallback(){
        System.out.println("Product Service is down !!! fallback route enable");
        return "CIRCUIT BREAKER ENABLE !!! No response from Product Service at this moment. Service will be back ";
    }

    @HystrixCommand(fallbackMethod = "afficherUnProduit_fallback")
    public String afficherUnProduit(int id) {
        String url = "http://localhost:8033/getProduitById/{id}";
        String response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                },
                id).getBody();
        return "NORMAL FLOW !!! - Id : "+id+" - Product Details : "+response+" -";
    }

    @SuppressWarnings("unused")
    private String afficherUnProduit_fallback(int id){
        System.out.println("Product Service is down !!! fallback route enable");
        return "CIRCUIT BREAKER ENABLE !!! No response from Product Service at this moment. Service will be back ";
    }

    @HystrixCommand(fallbackMethod = "calculerMargeProduit_fallback")
    public String calculerMargeProduit() {
        String response = restTemplate.exchange("http://localhost:8033/AdminProduits"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
                }).getBody();
        return "NORMAL FLOW !!! - Product Details : "+response+" -";
    }

    @SuppressWarnings("unused")
    private String calculerMargeProduit_fallback(){
        System.out.println("Product Service is down !!! fallback route enable");
        return "CIRCUIT BREAKER ENABLE !!! No response from Product Service at this moment. Service will be back ";
    }

    @HystrixCommand(fallbackMethod = "trierProduitsParOrdreAlphabetique_fallback")
    public String trierProduitsParOrdreAlphabetique() {
        String response = restTemplate.exchange("http://localhost:8033/getProduitsTrier"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
                }).getBody();
        return "NORMAL FLOW !!! - Product Details : "+response+" -";
    }

    @SuppressWarnings("unused")
    private String trierProduitsParOrdreAlphabetique_fallback(){
        System.out.println("Product Service is down !!! fallback route enable");
        return "CIRCUIT BREAKER ENABLE !!! No response from Product Service at this moment. Service will be back ";
    }

    // this is a bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
