package Serializables;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProductObject")
public class ProductObject implements Serializable {
	
	@XmlElement(name = "product_id")
	private int product_id;
	@XmlElement(name = "category_id")
	private int category_id;
	@XmlElement(name = "title")
    private String title; 
	@XmlElement(name = "summary")
    private String summary;
	@XmlElement(name = "description")
    private String description;
	@XmlElement(name = "price")
    private int price;
	@XmlElement(name = "price_type")
    private int price_type;
	@XmlElement(name = "imgLink")
    private String imgLink;
	@XmlElement(name = "quantity")
    private int quantity;
    
    
    public ProductObject(){}
    
    
    
	public ProductObject(int product_id, int category_id, String title,
			String summary, String description, int price,
			int price_type, String imgLink, int quantity ) {
		this.product_id = product_id;
		this.category_id = category_id;
		this.title = title;
		this.summary = summary;
		this.description = description;
		this.price = price;
		this.price_type = price_type;
		this.imgLink = imgLink;
		this.quantity  = quantity;
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
	

	public int getPrice_type() {
		return price_type;
	}
	
	public String getImgLink(){
		return imgLink;
	}
	public int getQuantity(){
		return quantity;
	}
}
