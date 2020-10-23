package com.hystrixmicroservice.hystrix.controller;

import com.hystrixmicroservice.hystrix.delegate.ProductServiceDelegate;
import com.hystrixmicroservice.hystrix.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    ProductServiceDelegate productServiceDelegate;

    //Récupérer la liste des produits
    @GetMapping("getProduits")
    public String listeProduits() {
        return productServiceDelegate.listeProduits();
    }

    //Récupérer un produit par son Id
    @GetMapping("/getProduitById/{id}")
    public String afficherUnProduit(@PathVariable(value = "id") int id) {
        return productServiceDelegate.afficherUnProduit(id);
    }

    @GetMapping("AdminProduits")
    public String calculerMargeProduit(){
        return productServiceDelegate.calculerMargeProduit();
    }

    @GetMapping("getProduitsTrier")
    public String trierProduitsParOrdreAlphabetique() {
        return productServiceDelegate.calculerMargeProduit();
    }
}
