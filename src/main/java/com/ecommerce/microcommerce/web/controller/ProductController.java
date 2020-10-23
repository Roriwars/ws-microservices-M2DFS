package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
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
    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    public List<Product> listeProduits() {
        return productList;
    }

    //Récupérer un produit par son Id
    @GetMapping("/getProductById/{id}")
    public Product afficherUnProduit(@PathVariable(value = "id") int id) {
        return productList.stream().filter(product -> product.getId() == id).collect(Collectors.toList()).get(0);
    }

    //ajouter un produit
    @PostMapping(value = "/addProduct")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        productList.add(product);
        Product productAdded = product;

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // supprimer un produit
    @DeleteMapping("deleteProductById")
    public void supprimerProduit(@PathVariable(value = "id") int id) {
        Product produitSupp = productList.stream().filter(product -> product.getId() == id).collect(Collectors.toList()).get(0);
        productList.remove(produitSupp);
    }

    // Mettre à jour un produit
    @PostMapping("updateProduct")
    public void updateProduit(@RequestBody Product product) {
        productList.set( product.getId() , product);
    }

    @GetMapping("AdminProduits")
    public HashMap<Product,Integer> calculerMargeProduit(){
        HashMap<Product,Integer> listeMarge = new HashMap<Product, Integer>();
        for(Product p : productList){
            listeMarge.put(p,p.getPrix()-p.getPrixAchat());
        }
        return listeMarge;
    }


    //Pour les tests
    /*@GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productList.chercherUnProduitCher(400);
    }*/



}
