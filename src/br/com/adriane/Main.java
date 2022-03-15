package br.com.adriane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.util.Pair;

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
        /*Map<CompoundKey, BigDecimal> groupByCodeAndId = new HashMap<>();
        bag.products.forEach(product -> groupByCodeAndId.merge(new CompoundKey(product.id, bag.code), product.value, BigDecimal::add));*/

        Map<Pair<Integer, String>, Double> groupByCodeAndId = bag.products.stream()
                .collect(Collectors.groupingBy(p -> Pair.of(p.id, bag.code), Collectors.summingDouble(p -> p.value.doubleValue())));
        System.out.printf("Agrupamento por Pair: %s\n", groupByCodeAndId);

        //acessar as chaves
        ArrayList<Pair<Integer, String>> compoundKeyList = new ArrayList<>(groupByCodeAndId.keySet());
        compoundKeyList.forEach(compoundKey -> System.out.printf("Pair: \t(%d, %s)\n", compoundKey.getFirst(), compoundKey.getSecond()));
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
