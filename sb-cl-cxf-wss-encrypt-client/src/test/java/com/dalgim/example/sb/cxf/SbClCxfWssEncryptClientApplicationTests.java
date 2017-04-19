package com.dalgim.example.sb.cxf;

import com.dalgim.example.sb.cxf.wssencrypt.endpoint.Fruit;
import com.dalgim.example.sb.cxf.wssencrypt.endpoint.FruitCatalog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbClCxfWssEncryptClientApplicationTests {

	@Autowired
	FruitCatalog fruitCatalog;

	@Test
	public void contextLoads() {
		assert true;
		//fruitCatalog.getAllFruit();
		Fruit fruit = new Fruit();
		fruit.setKcal(123.2);
		fruit.setName("asdsa");
		fruit.setFat(32.32);
		fruit.setProtein(12.3);
		fruit.setCarbo(123.3);

		fruitCatalog.addFruit(fruit);
	}

}
