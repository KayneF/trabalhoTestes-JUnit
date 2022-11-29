package com.junittest.work;

import java.time.Instant;

import com.junittest.work.entities.Category;
import com.junittest.work.entities.Order;
import com.junittest.work.entities.OrderItem;
import com.junittest.work.entities.Payment;
import com.junittest.work.entities.Product;
import com.junittest.work.entities.User;
import com.junittest.work.entities.enums.OrderStatus;

public class TestUtil {

	public static Category newCategory() {
		return new Category (
				null,
				"Smartphones");
	}
	
	public static Product newProduct() {
		return new Product(
				null,
				"MacBook Pro",
				"Cras fringilla convallis sem vel faucibus.",
				7000.0,
				"Computers");
	}
	
	public static User newUser() {
		return new User(
				null, 
				"Scott Summers", 
				"scottsummers@email.com", 
				"+5511987654455", 
				"asd321");
	}
	
	public static Order newOrder() {
		return new Order(
				null, 
				Instant.parse("2019-07-22T15:21:22Z"), 
				OrderStatus.WAITING_PAYMENT, 
				newUser());
	}
	
	public static OrderItem newOrderItem() {
		return new OrderItem(
				newOrder(), 
				newProduct(), 
				2, 
				newProduct().getPrice());
	}
	
	public static Payment newPayment() {
		return new Payment(
				null, 
				Instant.parse("2019-06-20T21:53:07Z"), 
				newOrder());
	}
	
}
