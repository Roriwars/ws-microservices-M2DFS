package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api
public class ProductController {

    public static List<Product> getProductList() {
        return productList;
    }

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

    @ApiResponses(value = {
            @ApiResponse(code=200, message="Succès"),
            @ApiResponse(code=401, message="Authorisation refusée"),
            @ApiResponse(code=403, message="Accès refusée"),
            @ApiResponse(code=404, message="Non trouvé")
    })

    @ApiOperation(
            value= "Get list of products",
            response = Iterable.class,
            tags = "listeProduits"
    )
    //Récupérer la liste des produits
    @RequestMapping(value = "/getProduits", method = RequestMethod.GET)
    public List<Product> listeProduits() {
        return productList;
    }

    @ApiOperation(
            value= "Get a product by his id",
            response = Iterable.class,
            tags = "afficherUnProduit"
    )
    //Récupérer un produit par son Id
    @GetMapping("/getProduitById/{id}")
    public Product afficherUnProduit(@PathVariable(value = "id") int id) {
        return productList.stream().filter(product -> product.getId() == id).collect(Collectors.toList()).get(0);
    }

    @ApiOperation(
            value= "Add product",
            tags = "ajouterProduit"
    )
    //ajouter un produit
    @PostMapping(value = "/addProduit")
    public void ajouterProduit(@Valid @RequestBody Product product) {
        if(product.getPrix()==0){
            throw new ProduitGratuitException("ERROR 403 : Prix du produit égal à zéro");
        }

        Product productAdded = product;

        if (productAdded != null)
            productList.add(productAdded);
    }

    @ApiOperation(
            value= "Delete product",
            tags = "supprimerProduit"
    )
    // supprimer un produit
    @DeleteMapping("deleteProduit")
    public List<Product> supprimerProduit(@Valid @RequestBody Product product) {
        Product productDelete = productList.stream().filter(res -> res.getId() == product.getId()).collect(Collectors.toList()).get(0);
        productList.remove(productDelete);
        return productList;
    }

    @ApiOperation(
            value= "Update product",
            tags = "updateProduit"
    )
    // Mettre à jour un produit
    @PostMapping("updateProduit")
    public void updateProduit(@RequestBody Product product) {
        if(product.getPrix()==0){
            throw new ProduitGratuitException("ERROR 403 : Prix du produit égal à zéro");
        }
        Product productUpdate = productList.stream().filter(res -> res.getId() == product.getId()).collect(Collectors.toList()).get(0);
        Product productNew = new Product(product.getId(),product.getNom(),product.getPrix(),product.getPrixAchat());
        productList.set( productList.indexOf(productUpdate) , productNew);
        productList.remove(productUpdate);
    }

    @ApiOperation(
            value= "Get list of product with his gain ",
            response = Iterable.class,
            tags = "calculerMargeProduit"
    )
    @GetMapping("AdminProduits")
    public HashMap<Product,Integer> calculerMargeProduit(){
        HashMap<Product,Integer> listeMarge = new HashMap<Product, Integer>();
        for(Product p : productList){
            listeMarge.put(p,p.getPrix()-p.getPrixAchat());
        }
        return listeMarge;
    }

    @ApiOperation(
            value= "Get list product ordered by name",
            response = Iterable.class,
            tags = "trierProduitsParOrdreAlphabetique"
    )
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
