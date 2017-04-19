package com.dalgim.example.sb.cxf.wssencrypt.endpoint;

import com.dalgim.example.sb.cxf.wssencrypt.model.Fruit;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dalgim on 08.04.2017.
 */
@Service
@WebService(endpointInterface = "com.dalgim.example.sb.cxf.wssencrypt.endpoint.FruitCatalog", portName = "FruitCatalogPort",
        serviceName = "FruitCatalogService", targetNamespace = "http://endpoint.fruitcatalog.dalgim.com")
public class FruitCatalogImpl implements FruitCatalog {

    private static List<Fruit> fruitList = new ArrayList<>();
    private static AtomicLong atomicLong = new AtomicLong();

    static {
        Fruit apple = new Fruit();
        apple.setName("Apple");
        apple.setCarbo(20.5);
        apple.setFat(0.5);
        apple.setProtein(0.5);
        apple.setId(atomicLong.getAndIncrement());

        Fruit banana = new Fruit();
        banana.setName("Banana");
        banana.setCarbo(20.5);
        banana.setFat(0.5);
        banana.setProtein(0.5);
        banana.setId(atomicLong.getAndIncrement());

        fruitList.add(apple);
        fruitList.add(banana);
    }

    @Override
    public Collection<Fruit> getAllFruits() {
        System.out.println("Incoming request...");
        return fruitList;
    }

    @Override
    public Fruit addFruit(Fruit fruit) {
        Preconditions.checkNotNull(fruit, "Fruit cannot be null!");
        fruit.setId(atomicLong.getAndIncrement());
        fruitList.add(fruit);
        return fruit;
    }

    @Override
    public Fruit getFruitById(Long fruitId) {
        Preconditions.checkNotNull(fruitId, "Fruit Id cannot be null!");

        return fruitList.stream()
                .filter(fruit -> fruit.getId() == fruitId)
                .findFirst().orElse(null);
    }

    @Override
    public void deleteFruitById(Long fruitId) {
        Preconditions.checkNotNull(fruitId, "Fruit Id cannot be null!");
        Fruit fruit = getFruitById(fruitId);
        fruitList.remove(fruit);
    }

    @Override
    public Fruit updateFruit(Fruit fruit) {
        Preconditions.checkNotNull(fruit, "Fruit cannot be null!");
        Preconditions.checkNotNull(fruit.getId(), "Fruit Id cannot be null!");

        Fruit fruitToUpdate = getFruitById(fruit.getId());
        fruitToUpdate.setProtein(fruit.getProtein());
        fruitToUpdate.setCarbo(fruit.getCarbo());
        fruitToUpdate.setFat(fruit.getFat());
        fruitToUpdate.setName(fruit.getName());
        return fruitToUpdate;
    }
}
