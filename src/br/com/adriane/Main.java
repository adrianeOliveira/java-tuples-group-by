package br.com.adriane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javatuples.Pair;

public class Main {

    public static void main(String[] args) {
        Product product1 = new Product(1, "123", BigDecimal.valueOf(2.5));
        Product product2 = new Product(2, "123", BigDecimal.valueOf(3.0));
        Product product3 = new Product(3, "1234", BigDecimal.valueOf(3.0));
        Bag bag = new Bag(1);
        bag.addProduct(product1, product2, product3);

        //agrupar por codigo
        Map<String, BigDecimal> groupByCode = new HashMap<>();
        bag.products.forEach(product -> groupByCode.merge(product.code, product.value, BigDecimal::add));
        System.out.printf("Agrupamento por código: %s\n", groupByCode);

        //agrupar por pair
        Map<Pair<String, Integer>, BigDecimal> groupByCodeAndId = new HashMap<>();
        bag.products.forEach(product -> groupByCodeAndId.merge(Pair.with(product.code, product.id), product.value, BigDecimal::add));
        System.out.printf("Agrupamento por código e ID: %s\n", groupByCodeAndId);
    }
}

class Product {
    Integer id;
    String code;
    BigDecimal value;

    public Product(Integer id, String codigo, BigDecimal value) {
        this.id = id;
        this.code = codigo;
        this.value = value;
    }
}

class Bag {
    Integer id;
    List<Product> products;

    public Bag(Integer id) {
        this.id = id;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product... products) {
        this.products.addAll(Arrays.asList(products));
    }
}
