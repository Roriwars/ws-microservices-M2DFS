package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private static List<Product> productList = new ArrayList<Product>();

    static {
        // create static bd
        productList = new ArrayList<Product>();
        Product product = new Product(1,"banane",2, 1);
        productList.add(product);
        product = new Product(2,"pomme",3, 2);
        productList.add(product);
        product = new Product(3,"fraise",5, 3);
        productList.add(product);
        product = new Product(4,"cerise",4, 2);
        productList.add(product);
        product = new Product(5,"concombre",4, 1);
        productList.add(product);
        product = new Product(6,"poire",3, 2);
        productList.add(product);
    }


    //Récupérer la liste des produits
    @RequestMapping(value = "/getProduits", method = RequestMethod.GET)
    public List<Product> listeProduits() {
        return productList;
    }

    //Récupérer un produit par son Id
    @GetMapping("/getProduitById/{id}")
    public Product afficherUnProduit(@PathVariable(value = "id") int id) {
        return productList.stream().filter(product -> product.getId() == id).collect(Collectors.toList()).get(0);
    }

    //ajouter un produit
    @PostMapping(value = "/addProduit")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded = product;

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        if(productAdded.getPrix()==0){
            //return new ProduitGratuitException();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        productList.add(productAdded);

        return ResponseEntity.created(location).build();
    }

    // supprimer un produit
    @DeleteMapping("deleteProduit")
    public List<Product> supprimerProduit(@Valid @RequestBody Product product) {
        Product productDelete = productList.stream().filter(res -> res.getId() == product.getId()).collect(Collectors.toList()).get(0);
        productList.remove(productDelete);
        return productList;
    }

    // Mettre à jour un produit
    @PostMapping("updateProduit")
    public void updateProduit(@RequestBody Product product) {
        Product productUpdate = productList.stream().filter(res -> res.getId() == product.getId()).collect(Collectors.toList()).get(0);
        productList.set( productList.indexOf(productUpdate) , product);
        productList.remove(productUpdate);
    }

    @GetMapping("AdminProduits")
    public HashMap<Product,Integer> calculerMargeProduit(){
        HashMap<Product,Integer> listeMarge = new HashMap<Product, Integer>();
        for(Product p : productList){
            listeMarge.put(p,p.getPrix()-p.getPrixAchat());
        }
        return listeMarge;
    }

    @GetMapping("getProduitsTrier")
    public List<Product> trierProduitsParOrdreAlphabetique() {

        List<Product> produitsTrier = productList;
        Collections.sort(produitsTrier,Product.ComparatorNom);
        return produitsTrier;
    }

    //Pour les tests
    /*@GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productList.chercherUnProduitCher(400);
    }*/



}
