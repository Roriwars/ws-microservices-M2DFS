package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class AppTest {

    private ProductController productController = new ProductController();

    @Test
    public void testGetterProduct(){
        System.out.println("Test des getter sur la classe product");
        Product test = new Product(0,"nom",25,20);
        Assert.assertTrue(0 == test.getId());
        Assert.assertTrue("nom".equals(test.getNom()));
        Assert.assertTrue(25==test.getPrix());
        Assert.assertTrue(20==test.getPrixAchat());
    }

    @Test
    public void testGetProduits(){
        List<Product> expected = productController.getProductList();
        Assertions.assertEquals(expected,productController.listeProduits());
    }

    @Test
    public void testAfficherUnProduit(){
        List<Product> expected = productController.getProductList();

        Assertions.assertEquals(expected.get(0),productController.afficherUnProduit(expected.get(0).getId()));
        Assertions.assertNotSame(expected.get(0),productController.afficherUnProduit(expected.get(1).getId()));
    }

    @Test
    public void testAjouterUnProduit(){
        List<Product> expected = productController.getProductList();
        Product newProduit = new Product(7,"nom",20,15);
        expected.add(newProduit);
        productController.ajouterProduit(newProduit);
        List<Product> result = productController.listeProduits();
        Assertions.assertEquals(expected,result);
    }

    @Test
    public  void testUpdateProduit(){
        List<Product> expected = productController.getProductList();
        Product produitUpdate = new Product(1,"nom",20,15);
        expected.set(0,produitUpdate);
        expected.remove(1);
        productController.updateProduit(produitUpdate);
        List<Product> result = productController.listeProduits();
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void testMargeProduit(){
        List<Product> liste = productController.getProductList();
        int margeExpected = liste.get(0).getPrix()-liste.get(0).getPrixAchat();
        HashMap<Product,Integer> result = productController.calculerMargeProduit();
        Assertions.assertEquals(margeExpected,result.get(liste.get(0)));
    }

    @Test
    public void testTriAlphabetique(){
        List<Product> liste = productController.getProductList();
        List<String> noms = new ArrayList<>();
        for(Product p:liste){
            noms.add(p.getNom());
        }
        noms.sort(Comparator.comparing( String::toString ));
        List<Product> result = productController.trierProduitsParOrdreAlphabetique();
        Assertions.assertEquals(noms.get(0),result.get(0).getNom());
    }
}
