package project;

import java.io.IOException;
import java.util.*;

public class ShoppingMain {


	private String productId; // 제품ID
	private String productName; // 제품이름
	private String category; // 카테고리
	private int price; // 가격
	private int quantity; // 수량

	/*
	 * ///////////////생성자/////////////
	 */
	ShoppingMain() {
	}

	public ShoppingMain(String productId, String productName, String category, int price, int quantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}
////////////////////생성자 완료/////////////////////////////
	
	/*
	* //////////////getter & setter 시작//////////////
	*/	

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/*
	 * //////////////getter & setter 완료//////////////
	 */
	
	public void shoppingList() {
		StringBuffer sb = new StringBuffer();	
		sb.append("제품ID = ");
		sb.append(productId + " || ");
		sb.append("제품명 = ");
		sb.append(productName + " || ");
		sb.append("카테고리 = ");
		sb.append(category + " || ");
		sb.append("가격 = ");
		sb.append(price + " || ");
		sb.append("수량 = ");
		sb.append(quantity + " || ");

		System.out.println(sb);
	}
	
	public void login() throws IOException{
		CustomerService customerService = new CustomerService();
		ProductService productService = new ProductService();
		
		customerService.Fileread();		
		productService.Fileread();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-------로그인---------");
		System.out.println("ID를 입력하세요.");
		String id = sc.next();
		System.out.println("비밀번호를 입력하세요.");
		String pw = sc.next();
		
		if (customerService.customerHash.containsKey(id)) {
			// List<String> info = customerService.customerHash.get();
		}
		
		
		
	}
		
	
	public void ShoppingMain() throws IOException {
		Scanner sc = new Scanner(System.in);
		// ShoppingService 클래스에서 입력 내용구현
		//Main restart = new Main();
		//CustomerService customerService = new CustomerService();

		//customerService.Fileread(); // 파일을 불러와 list와 hashmap에 저장하는 메소드 불러오기
		
		while (true) {
			System.out.println("-----------------------");
			System.out.println("1.입력");
			System.out.println("2.수정");
			System.out.println("3.삭제");
			System.out.println("4.조회");
			System.out.println("5.전체조회");
			System.out.println("6.종료");
			System.out.println("0.메인 메뉴로 돌아가기");
			System.out.println("-----------------------");
			// 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
			int menu = sc.nextInt();

			switch (menu) {
			case 1:
				//customerService.insert(); // 1. 입력
				break;
			case 2: {
				//customerService.edit(); // 2. 수정
				break;
			}
			case 3:
				//customerService.delete(); // 3. 삭제
				break;
			case 4:
				//customerService.personalView(); // 4. 개인별 조회
				break;
			case 5:
				//customerService.view(); // 5. 전체 고객 조회
				break;
			case 6:
				//customerService.exit(); // 6. 종료
				break;
			case 0:
				//restart.start(); // 0. 초기화면 돌아가기
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

}
	

