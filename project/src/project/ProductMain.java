package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ProductMain {

	private String productId; // 제품ID
	private String productName; // 제품이름
	private String category; // 카테고리
	private int price; // 가격
	private int quantity; // 수량

	// *** Construtor *** //
	ProductMain() {
	}

	public ProductMain(String productId, String productName, String category, int price, int quantity) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}


	// *** getter & setter *** //
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

	// *** String Method *** //
	public void productString() {
		System.out.printf("%-10s%-10s%-10s%-10d%-10d\n", productName, productId, category, price, quantity);
	}

	// *** ProductMenu Method *** //
	public void ProductMenu() throws IOException {
		// customerService 클래스에서 입력 내용구현
		Scanner sc = new Scanner(System.in);
		Main restart = new Main();
		ProductService productService = new ProductService();
		int stepNo = 0;
		productService.Fileread(); // 파일읽기

		while (true) {
			System.out.println("==========제품 메인화면 입니다.==========");
			System.out.println("1.제품등록");
			System.out.println("2.제품수정");
			System.out.println("3.제품삭제");
			System.out.println("4.단일 제품조회");
			System.out.println("5.제품 전체조회");
			System.out.println("0.메인 메뉴로 돌아가기");
			System.out.println("=====================================");
			// 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
			int menu = sc.nextInt();

			switch (menu) {
			case 1:
				productService.insert(stepNo); // 1. 제품등록
				break;
			case 2: {
				productService.edit(stepNo); // 2. 제품수정
				break;
			}
			case 3:
				productService.delete(stepNo); // 3. 제품삭제
				break;
			case 4:
				productService.SingleView(stepNo); // 4. 단일제품 조회
				break;
			case 5:
				productService.view(stepNo); // 5. 제품 전체조회
				break;
			case 0:
				productService.FileSave(); // 6. 종료
				restart.start(); // 0. 초기화면 돌아가기
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

}