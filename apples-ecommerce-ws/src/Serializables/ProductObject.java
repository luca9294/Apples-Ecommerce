package Serializables;

import java.io.Serializable;

public class ProductObject implements Serializable {
	
	private int product_id;
	private int category_id;
    private String title; 
    private String summary;
    private String description;
    private int price;
    private int price_type;
    
    
	public ProductObject(int product_id, int category_id, String title,
			String summary, String description, int price,
			int price_type) {
		this.product_id = product_id;
		this.category_id = category_id;
		this.title = title;
		this.summary = summary;
		this.description = description;
		this.price = price;
		this.price_type = price_type;
	}
	
	
	public int getProduct_id() {
		return product_id;
	}


	public int getCategory_id() {
		return category_id;
	}


	public String getTitle() {
		return title;
	}
	

	public String getSummary() {
		return summary;
	}


	public String getDescription() {
		return description;
	}
	

	public int getPrice() {
		return price;
	}
	

	public int price_type() {
		return price_type;
	}

    
	
	
	
	
	
}
