package designpatterns.builder.normal;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Pizza {
	private Map<String, Integer> toppingAmounts;
	private String customerName;
	private Date orderDate;

	private Pizza(PizzaBuilder pizzaBuilder) {
		this.toppingAmounts = pizzaBuilder.toppingAmounts;
		this.customerName = pizzaBuilder.customerName;
		this.orderDate = pizzaBuilder.orderDate;
	}

	public Map<String, Integer> getToppingAmount() {
		return toppingAmounts;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public static class PizzaBuilder {
		Map<String, Integer> toppingAmounts;
		String customerName;
		Date orderDate;

		public PizzaBuilder() {
			toppingAmounts = new HashMap<>();
			this.customerName = "Customer";
			this.orderDate = Calendar.getInstance().getTime();
		}

		public PizzaBuilder addTopping(String topping, int amount) {
			if (topping != null && amount > 0) {
				this.toppingAmounts.compute(topping, (k, v) -> v == null ? amount : v + amount);
			}
			return this;
		}

		public PizzaBuilder removeTopping(String topping, int amount) {
			if (topping != null && amount > 0) {
				this.toppingAmounts.compute(topping, (k, v) -> v != null && v > amount ? v - amount : null);
			}
			return this;
		}

		public PizzaBuilder setCustomerName(String customerName) {
			if (customerName != null && !customerName.isEmpty()) {
				this.customerName = customerName;
			}
			return this;
		}

		public Pizza builder() {
			return new Pizza(this);
		}
	}
}
