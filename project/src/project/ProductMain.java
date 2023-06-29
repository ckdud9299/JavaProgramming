package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ProductMain{
	
	private String productId;	//제품ID
	private String productName;		//제품이름
	private String category;		//카테고리
	private int price;				//가격
	private int quantity;			//수량
	
	/*
	 * 	///////////////생성자/////////////
	 */
	ProductMain(){}
	
	public ProductMain(String productId, String productName, String category, int price, int quantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}

////////////////////생성자 완료/////////////////////////////
      
	
	/*
	 *  //////////////getter & setter 시작//////////////
	 */
	public String getProductId() {return productId;}
	public void setProductId(String productId) {this.productId = productId;}
	public String getProductName() {return productName;}
	public void setProductName(String productName) {this.productName = productName;}
	public String getCategory() {return category;}
	public void setCategory(String category) {this.category = category;}
	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}
	public int getQuantity() {return quantity;}
	public void setQuantity(int quantity) {this.quantity = quantity;}	
	
	/*
	 *  //////////////getter & setter 완료//////////////
	 */
	
	public void ProductList() {
		StringBuffer sb = new StringBuffer();
		sb.append(productName + " ");
		sb.append("제품" + " || ");
		sb.append("제품ID = ");
		sb.append(productId + " || ");
		sb.append("카테고리 = ");
		sb.append(category + " || ");
		sb.append("가격 = ");
		sb.append(price + " || ");
		sb.append("수량 = ");
		sb.append(quantity);
		System.out.println(sb);
	}

	// ProductMainMenu 시작(Shopping Main클래스에서 처음 호출받는 메소드)
	public void ProductMenu() throws IOException {
		// customerService 클래스에서 입력 내용구현
		Scanner sc = new Scanner(System.in);
		Main restart = new Main();
		ProductService productService = new ProductService();
		
		productService.Fileread();	// 파일읽기
		
		while (true) {
			System.out.println("-----------------------");
			System.out.println("1.제품등록");
			System.out.println("2.제품수정");
			System.out.println("3.제품삭제");
			System.out.println("4.단일 제품조회");
			System.out.println("5.제품 전체조회");
			System.out.println("6.종료");
			System.out.println("0.메인 메뉴로 돌아가기");
			System.out.println("-----------------------");
			// 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
			int menu = sc.nextInt();

			switch (menu) {
			case 1:
				productService.insert(); // 1. 제품등록
				break;
			case 2: {
				productService.edit(); // 2. 제품수정
				break;
			}
			case 3:
				productService.delete(); // 3. 제품삭제
				break;
			case 4:
				productService.SingleView(); // 4. 단일제품 조회
				break;
			case 5:
				productService.view(); // 5. 제품 전체조회
				break;
			case 6:
				productService.exit(); // 6. 종료
				break;
			case 0:
				restart.start(); // 0. 초기화면 돌아가기
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

}