package com.ecommerce.micrommerce.web.controller.exercice;

import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.micrommerce.web.dao.ProductDao;
import com.ecommerce.micrommerce.web.exceptions.exercice.ProduitGratuitException;
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
    @GetMapping("/AdminProduits")
    public Map<String, Integer> calculerMargeProduit() {
        Map<String, Integer> marges = new HashMap<>();
        listeProduits().stream().forEach(product -> {
            int marge = Math.abs(product.getPrixAchat() - product.getPrix());
            marges.put(product.toString(), marge);
        });
        return marges;
    }

    @GetMapping("produits/asc")
    public List<Product> trierProdutsParOrdreAlphabetiqueCroissant() {
        return productDao.findByOrderByNomAsc();
    }

    @GetMapping("produits/desc")
    public List<Product> trierProdutsParOrdreAlphabetiqueDecroissant() {
        return productDao.findByOrderByNomDesc();
    }

    @Override
    @PostMapping(value = "/Produits")
    public ResponseEntity<Product> ajouterProduit(@RequestBody Product product) {
        if (product.getPrix() == 0) {
            throw new ProduitGratuitException(
                    "Vous ne pouvez ajouter un produit gratuit. Ajouter un prix non nul");
        }
        return super.ajouterProduit(product);
    }

}