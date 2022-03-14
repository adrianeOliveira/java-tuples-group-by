package br.com.adriane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Product product1 = new Product(1, BigDecimal.valueOf(2.5));
        Product product2 = new Product(2, BigDecimal.valueOf(3.0));
        Product product3 = new Product(3, BigDecimal.valueOf(3.0));
        Bag bag = new Bag(1, "123");
        bag.addProduct(product1, product2, product3);

        //agrupar por codigo
        Map<Integer, BigDecimal> groupByCode = new HashMap<>();
        bag.products.forEach(product -> groupByCode.merge(product.id, product.value, BigDecimal::add));
        System.out.printf("Agrupamento por código: %s\n", groupByCode);

        //agrupar por pair
        /*Map<Pair<String, Integer>, BigDecimal> groupByCodeAndId = new HashMap<>();
        bag.products.forEach(product -> groupByCodeAndId.merge(Pair.with(bag.code, product.id), product.value, BigDecimal::add));
        System.out.printf("Agrupamento por código e ID: %s\n", groupByCodeAndId);*/

        //agrupar por chave composta
        Map<CompoundKey, BigDecimal> groupByCodeAndId = new HashMap<>();
        bag.products.forEach(product -> groupByCodeAndId.merge(new CompoundKey(product.id, bag.code), product.value, BigDecimal::add));

        //acessar as chaves
        ArrayList<CompoundKey> compoundKeyList = new ArrayList<>(groupByCodeAndId.keySet());
//        listOfPair.forEach(System.out::println);
        compoundKeyList.forEach(compoundKey -> System.out.printf("Pair: \t(%s, %d)\n", compoundKey.code, compoundKey.productId));
    }
}

class Product {
    Integer id;

    BigDecimal value;

    public Product(Integer id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }
}

class Bag {
    Integer id;
    String code;
    List<Product> products;

    public Bag(Integer id, String code) {
        this.id = id;
        this.code = code;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product... products) {
        this.products.addAll(Arrays.asList(products));
    }
}

class CompoundKey {
    Integer productId;
    String code;

    public CompoundKey(Integer productId, String code) {
        this.productId = productId;
        this.code = code;
    }
}
