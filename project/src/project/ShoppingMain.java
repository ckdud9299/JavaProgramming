package project;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShoppingMain {

	private int productOrderNo; // 제품 주문번호
	private String userID; // UID
	private String productID; // PID
	private String date; // 날짜
	private int quantity; // 수량
	private int price; // 가격
	private int totapPrice; // 총액

	private String inputId;

	// * 생성자 */

	public ShoppingMain() {
	}

	public ShoppingMain(int productOrderNo, String userID, String productID, String date, int quantity, int price,
			int totapPrice) {
		super();
		this.productOrderNo = productOrderNo;
		this.userID = userID;
		this.productID = productID;
		this.date = date;
		this.quantity = quantity;
		this.price = price;
		this.totapPrice = totapPrice;
	}

	// * getter & setter * //
	public int getProductOrderNo() {
		return productOrderNo;
	}

	public void setProductOrderNo(int productOrderNo) {
		this.productOrderNo = productOrderNo;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTotapPrice() {
		return totapPrice;
	}

	public void setTotapPrice(int totapPrice) {
		this.totapPrice = totapPrice;
	}

	public void shoppingString() {
		System.out.printf("%-10s%-10s%-10s%-20s%-10d%-10d%-10d\n", productOrderNo, userID, productID, date, quantity,
				price, totapPrice);
	}


	public void Login() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("==========쇼핑몰 로그인==========");
		// Customer & Product 객체생성 파일리스트 값 받아오기
		CustomerService customerService = new CustomerService();
		ProductService productService = new ProductService();
		customerService.Fileread();
		productService.Fileread();
		// 사용자 id, pw 입력받고 고객 id 및 비밀번호 확인
		System.out.println("ID 입력하세요 = ");
		inputId = sc.next();
		String findFw = null;

		// 고객정보 클래스의 아이디 정보 확인
		if (customerService.customerHash.containsKey(inputId)) {
			findFw = customerService.customerHash.get(inputId).getPw();
		} else if (!customerService.customerHash.containsKey(inputId)) {
			System.out.println("로그인 실패");
			Login();
			return;
		}
		// 패스워드 확인
		System.out.println("PW 입력하세요 = ");
		String pw = sc.next();
		if (findFw.equals(pw)) {
			System.out.println("로그인 성공!");
			ShoppingMenu();

		} else {
			System.out.println("로그인 실패");
			Login();
			return;
		}
	}

	// ProductMainMenu 시작(Shopping Main클래스에서 처음 호출받는 메소드)
	public void ShoppingMenu() throws IOException {
		// ShoppingMenu Page List
		Scanner sc = new Scanner(System.in);
		Main restart = new Main();
		ShoppingService shoppingService = new ShoppingService();
		shoppingService.Fileread();

		int stepNo = 1;

		while (true) {
			System.out.println("-----------------------");
			System.out.println("1. 제품구매");
			System.out.println("2. 환불");
			System.out.println("3. 고객 구매리스트 조회");
			System.out.println("4. 주문목록 전체조회");
			System.out.println("0. 메인 메뉴로 돌아가기");
			System.out.println("-----------------------");
			// 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
			int menu = 0;
			
			while (true) {
				try {
					menu = sc.nextInt();

					if (menu >= 0 && menu <= 4) {
						break;
					} else {
						System.out.println("[ERROR] 0부터 4까지의 숫자를 입력해주세요.");
					}
				} catch (InputMismatchException e) {
					System.out.println("[ERROR] 숫자를 입력해주세요.");
					sc.nextLine(); // 스트링 버퍼를 비움 -> 잘못된 입력이 남아있는 경우 반복적인 예외 발생 방지
				}
			}

			switch (menu) {
			case 1:
				shoppingService.productBuy(inputId, stepNo); // 1. 구매하기
				break;
			case 2: {
				shoppingService.productRefund(inputId, stepNo); // 2. 환불하기
				break;
			}
			case 3:
				shoppingService.customerBuyList(inputId); // 3. 고객 구매 리스트
				break;
			case 4:
				shoppingService.view(); // 4. 주문목록 전체조회
				break;
			case 0:
				shoppingService.FileSave(); // 6. 종료
				restart.start(); // 0. 초기화면 돌아가기
				return;
			}
		}
	}

}