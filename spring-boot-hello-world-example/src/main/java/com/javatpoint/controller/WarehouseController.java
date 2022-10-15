package com.javatpoint.controller;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.squareship.model.CheckoutStatus;
import com.squareship.model.Item;
import com.squareship.model.PostalDetails;
import com.squareship.model.Product;
import com.squareship.model.ProductDetails;
import com.squareship.model.ProductInfo;
import com.squareship.model.ShippingCharges;

@RestController
public class WarehouseController {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String ADD_PRODUCTS_ENDPOINT_URL="https://e-commerce-api-recruitment.herokuapp.com/cart/item/";
	ArrayList<Item> itemlist= new ArrayList<Item>();
	ProductInfo productinfo= new ProductInfo();
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value ="/cart/item",method=RequestMethod.POST)
	public ArrayList<Item> addProduct(@RequestBody Item item) {
	
		itemlist.add(item);
		productinfo.setItems(itemlist);
		return itemlist;
	}
	
	  @SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/cart/items", method = RequestMethod.GET)
	   public ProductInfo getProduct() {
		  if(null !=productinfo) {
			  ArrayList<Item> itemlist =productinfo.getItems();
		  if(null !=itemlist && !itemlist.isEmpty()) {
			  productinfo.setStatus("Success");
			  productinfo.setMessage("Item available in the cart");
	       return productinfo;
	  }else {
		  productinfo.setMessage("Cart is empty");
		  return productinfo;
	  }
		  }
		   productinfo.setMessage("productinfo is empty");
		   return productinfo;
		  }
	  
	  @RequestMapping(value = "/cart/checkout-value/{postalCode}", method = RequestMethod.GET)
	   public CheckoutStatus checkoutCarts(@PathVariable("postalCode")String postalCode) {
	     
		  HttpHeaders headers = new HttpHeaders();
	      ShippingCharges shipcharge = new ShippingCharges();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<ProductInfo> entity = new HttpEntity<>(headers);
	      
	      PostalDetails postalDetails= restTemplate.exchange("https://e-commerce-api-recruitment.herokuapp.com/warehouse/distance/?postal_code="+postalCode,HttpMethod.GET,entity,
	    		  	                   PostalDetails.class).getBody();
	    double distanceinkms=  postalDetails.getDistance_in_kilometers();
	    shipcharge.setDistance(distanceinkms);
	      ResponseEntity<ProductInfo> result= restTemplate.exchange(
	 	         "http://localhost:8080/cart/items", HttpMethod.GET, entity, ProductInfo.class);
	      
	      ProductInfo ProductInfo=   result.getBody();
	      ArrayList<Item> itemList=ProductInfo.getItems();
	      int totalweight=0;
	      double total_Price=0;
	      for (Item item : itemList) {
	    	 int productId=item.getProduct_id();
	      ProductDetails productdetails= restTemplate.exchange(
	         "https://e-commerce-api-recruitment.herokuapp.com/product/"+productId, HttpMethod.GET, entity, ProductDetails.class).getBody();
	     Product product= productdetails.getProduct();
	     int prod_weight=product.getWeight_in_grams();
	     totalweight=totalweight + prod_weight;
	     double discount=product.getDiscount_percentage();
	     discount=discount / 100;
	    double productprice= product.getPrice();
	    double disctvalue=(discount*productprice);
	    double discountPrice=productprice-disctvalue;
	    total_Price= total_Price+discountPrice;
	    
	     shipcharge.setTotalWeight(totalweight);
	     shipcharge.setDiscounts(discount);
	     shipcharge.setFinalPrice(total_Price);
	      }
	   double shippingcharge=  calculateShippingcost(shipcharge);
	   NumberFormat us     = NumberFormat.getCurrencyInstance(Locale.US);
	   us.format(total_Price+shippingcharge);
	   CheckoutStatus checkoutStatus= new CheckoutStatus();
	   checkoutStatus.setMessage("Total value of your shopping cart is - $"+us.format(total_Price+shippingcharge));
	   checkoutStatus.setStatus("Success"); 
	   return checkoutStatus;
	      }
	  
	   private double calculateShippingcost(ShippingCharges shipcharge) {
		// TODO Auto-generated method stub
		
		   int totalweight= shipcharge.getTotalWeight();
		   double distance = shipcharge.getDistance();
		   
		   if(totalweight< 2 && distance< 5)
		   return 12;
		   if(totalweight< 2 && ((distance > 5) && (distance <20)))
			   return 15;
		   if(totalweight< 2 && ((distance > 20) && (distance <50)))
			   return 20;
		   if(totalweight< 2 && ((distance > 50) && (distance <500)))
			   return 50;
		   if(totalweight< 2 && ((distance > 500) && (distance <800)))
			   return 100;
		   if(totalweight< 2 && (distance > 800))
			   return 220;
		   
		   if(((totalweight > 2) && (totalweight<5)) && distance< 5)
			   return 14;
		   if(((totalweight > 2) && (totalweight < 5)) && ((distance > 5.0) && (distance < 20.0)))
			   return 18;
		   if(((totalweight> 2) && (totalweight<5)) && ((distance > 20) && (distance <50)))
			   return 24;
		   if(((totalweight> 2) && (totalweight<5)) && ((distance > 50) && (distance <500)))
			   return 55;
		   if(((totalweight> 2) && (totalweight<5)) && ((distance > 500) && (distance <800)))
			   return 110;
		   if(((totalweight> 2) && (totalweight<5)) && distance > 800)
			   return 250;
		
		   
		   
		   if(((totalweight> 5) && (totalweight<20)) && distance< 5)
			   return 16;
		   if(((totalweight> 5) && (totalweight<20)) && ((distance > 5) && (distance <20)))
			   return 25;
		   if(((totalweight> 5) && (totalweight<20)) && ((distance > 20) && (distance <50)))
			   return 30;
		   if(((totalweight> 5) && (totalweight<20)) && ((distance > 50) && (distance <500)))
			   return 80;
		   if(((totalweight> 5) && (totalweight<20)) && ((distance > 500) && (distance <800)))
			   return 130;
		   if(((totalweight> 5) && (totalweight<20)) && distance > 800)
			   return 270;
		   
		   
		   
		   if(totalweight> 20 && distance< 5)
			   return 21;
		   if(totalweight> 20 && ((distance > 5) && (distance <20)))
			   return 35;
		   if(totalweight> 20 && ((distance > 20) && (distance <50)))
			   return 50;
		   if(totalweight> 20 && ((distance > 50) && (distance <500)))
			   return 90;
		   if(totalweight> 20 && ((distance > 500) && (distance <800)))
			   return 150;
		   if(totalweight> 20 && distance > 800)
			   return 300;
		   else {
			   return 0;
		   }
	}


	@RequestMapping(value = "/cart/items", method = RequestMethod.DELETE)
	   public ProductInfo deleteProduct(Item item) {
		   productinfo.setItems(null);
		   productinfo.setStatus("Success");
		   productinfo.setMessage("All items have been removed from the cart !");
	      return productinfo;
	   }
	
}
