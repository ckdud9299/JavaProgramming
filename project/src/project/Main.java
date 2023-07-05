package project;

import java.io.IOException;
import java.util.*;

public class Main {

	public static void start() throws IOException {

		System.out.println("==========쇼핑몰 프로그램==========");
		System.out.println("1. 쇼핑몰");
		System.out.println("2. 고객 관리");
		System.out.println("3. 제품 관리");
		System.out.println("어떤 메뉴를 선택하시겠습니까? ");
		System.out.println("===============================");
		// 입력받은 조건에 따라 쇼핑몰, 고객관리, 제품관리로 분기
		Scanner sc = new Scanner(System.in);
		int menu = 0;
		while (true) {

			try {
				menu = sc.nextInt();
				if(menu > 3 || menu < 1) {
					System.out.println("[ERROR] 1~3번을 입력하세요.");
				}else {
					break;
				}
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("[ERROR] 숫자를 입력하세요.");
			}
		}

		switch (menu) {
		case 1:
			ShoppingMain shoppingMain = new ShoppingMain();
			shoppingMain.Login();
			break;
		case 2: 
			CustomerMain coustormerMain = new CustomerMain();
			coustormerMain.customerMenu();
			break;
		case 3:
			ProductMain productMain = new ProductMain();
			productMain.ProductMenu();
			break;
		default:
			break;
		}

	}

	public static void main(String[] args) throws IOException {
		// Main class의 start 메소드 호출
		start();

	}
}