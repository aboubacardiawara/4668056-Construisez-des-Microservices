package com.ecommerce.micrommerce.web.controller.exercice;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.micrommerce.web.dao.ProductDao;
import com.ecommerce.micrommerce.web.model.Product;

@RestController("ProductController2")
@RequestMapping("/exercice")
public class ProductController extends com.ecommerce.micrommerce.web.controller.ProductController {

    public ProductController(ProductDao productDao) {
        super(productDao);
    }

    /**
     * Calcul les marges sur les produits
     * 
     * @return
     */
    @GetMapping("/produits/marges")
    public List<Integer> calculerMargeProduit() {
        List<Integer> marges = listeProduits()
                .stream()
                .map((product) -> product.getPrixAchat() - product.getPrix())
                .collect(Collectors.toList());

        return marges;
    }

    @GetMapping("produits/asc")
    public List<Product> trierProdutsParOrdreAlphabetiqueCroissant() {
        return trierProdutsParAlphabetiqueCroissant(true);
    }

    @GetMapping("produits/desc")
    public List<Product> trierProdutsParOrdreAlphabetiqueDecroissant() {
        return trierProdutsParAlphabetiqueCroissant(false);
    }

    private List<Product> trierProdutsParAlphabetiqueCroissant(boolean ordreCroissant) {
        List<Product> produits = listeProduits();
        produits.sort(new Comparator<Product>() {

            @Override
            public int compare(Product product1, Product product2) {
                int resultatComparaison = product1.getNom().compareTo(product2.getNom());
                return ordreCroissant ? resultatComparaison : -resultatComparaison;
            }
        });
        return produits;
    }

}