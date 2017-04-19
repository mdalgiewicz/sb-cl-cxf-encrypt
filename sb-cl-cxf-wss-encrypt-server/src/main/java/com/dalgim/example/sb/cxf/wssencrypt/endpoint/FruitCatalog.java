package com.dalgim.example.sb.cxf.wssencrypt.endpoint;

import com.dalgim.example.sb.cxf.wssencrypt.model.Fruit;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Collection;

/**
 * Created by dalgim on 08.04.2017.
 */
@WebService(name = "FruitCatalog")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface FruitCatalog {

    @WebMethod(action = "get_all_fruits", operationName = "getAllFruit")
    @WebResult(name = "allFruits")
    Collection<Fruit> getAllFruits();

    @WebMethod(action = "add_fruit", operationName = "addFruit")
    @WebResult(name = "createdFruit")
    Fruit addFruit(@WebParam(name = "newFruit") Fruit fruit);

    @WebMethod(action = "get_fruit_by_id", operationName = "getFruitById")
    @WebResult(name = "FruitResponse")
    Fruit getFruitById(@WebParam(name = "fruitId") Long fruitId);

    @WebMethod(action = "delete_fruit_by_id", operationName = "deleteFruitById")
    void deleteFruitById(@WebParam(name = "fruitId") Long fruitId);

    @WebMethod(action = "update_fruit", operationName = "updateFruit")
    @WebResult(name = "updateFruitResponse")
    Fruit updateFruit(@WebParam(name = "fruit") Fruit fruit);
}
