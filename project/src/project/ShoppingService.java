package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ShoppingService {

	ArrayList<ShoppingMain> shoppingList = new ArrayList<>();
	HashMap<Integer, ShoppingMain> shoppingHash = new HashMap<Integer, ShoppingMain>();

	Scanner sc = new Scanner(System.in);

	// 구매하기
	public void productBuy(String inputId) throws IOException {

		ProductService productService = new ProductService();
		productService.Fileread();

		// 물품 목록 보여주기
		System.out.println("---제품 목록---");
		System.out.println("제품ID   제품명   가격   수량");

		if (!productService.productList.isEmpty()) {
			for (ProductMain productMain : productService.productList) {
				System.out.println(productMain.getProductId() + " " + productMain.getProductName() + " "
						+ productMain.getPrice() + " " + productMain.getQuantity());
			}
			// 리스트가 비어있으면 view 메소드 종료
		} else if (productService.productList.isEmpty()) {
			System.out.println("등록된 제품이 없습니다.");
			return;
		}

		System.out.println();

		// 주문번호 생성하기
		int orderNo = shoppingList.get(shoppingList.size() - 1).getProductOrderNo() + 1;

		// 주문자 Id 가져오기
		String uid = inputId;

		// 구매할 제품 ID 입력받기
		System.out.println("구매하실 제품 ID를 입력해주세요");
		String pid = sc.next();

		// 입력받은 제품 ID가 목록에 없을때 예외처리
		if (!(productService.productHash.containsKey(pid))) {
			System.out.println("잘못된 입력입니다.");
			productBuy(inputId);
		}
		
		// 입력받은 제품 ID의 재고가 0일때
		if(productService.productHash.get(pid).getQuantity() == 0) {
			System.out.println("해당 제품의 재고가 없습니다.");
			return;
		}

		// 구매할 날짜 생성하기
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = now.format(formatter);

		// 구매할 수량 입력받기
		System.out.println("수량을 입력해주세요");
		int inputQuantity = sc.nextInt();

		// 입력받은 수량이 0일때
		if (inputQuantity == 0) {
			System.out.println("수량이 없습니다.");
			productBuy(inputId);
		}

		// 가격 productlist에서 가져오기
		int price = productService.productHash.get(pid).getPrice();

		// 총액 계산하기
		int total = inputQuantity * price;

		// shopping 리스트와 hash에 저장
		ShoppingMain shoppingmain = new ShoppingMain(orderNo, uid, pid, date, inputQuantity, price, total);

		shoppingList.add(shoppingmain);
		shoppingHash.put(orderNo, shoppingmain);

		// product의 arraylist와 hashmap 수량 변경
		int productQuantity = productService.productHash.get(pid).getQuantity(); // productHash에 저장된 pid에 해당하는 재고 가져오기
		int resultProductQuanity = productQuantity - inputQuantity; // 변경될 재고 계산

		ProductMain productmain = productService.productHash.get(pid);
		int index = productService.productList.indexOf(productmain);

		productService.productHash.get(pid).setQuantity(resultProductQuanity); // productHash 재고 변경
		productService.productList.get(index).setQuantity(resultProductQuanity); // productList 재고 변경
		productService.FileSave(); // product.txt 저장
	}

	public void productRefund(String inputId) throws IOException {

		// *** 고객 정보, 제품정보 파일로 받아와서 ShoppingList에 넣어주기 ***//
		String userId = inputId;
		ProductService productService = new ProductService();
		productService.Fileread();

		int listCount = 0;
		// *** 고객이 제품을 가지고 있는지 판단하고 고객의 구매리스트 출력 ***//
		for (ShoppingMain e : shoppingList) {
			if (userId.equals(e.getUserID())) {
				listCount++;
				if (listCount != 0)
					e.shoppingString();
			}
		}
		// *** 고객의 구매리스트가 없을 때 ***//
		if (listCount == 0) {
			System.out.println("해당 고객은 제품이 없습니다.");
			return;
		}
		System.out.println("------------------------------");
		System.out.println("환불할 제품의 주문번호를 입력하세요 = ");
		int num = sc.nextInt(); 
		// *** 환불할 항목의 값이 구매자의 범위내에 없을 때 or 해쉬맵에 저자되어 있지 않을때 ***//
		if (!shoppingHash.containsKey(num) || !userId.equals(shoppingHash.get(num).getUserID())) {
			System.out.println("입력값이 초과되었습니다.");
			System.out.println("재입력 해주세요.");
			System.out.println("------------------------------");
			productRefund(userId);
			return;
		} else {
			// *** 환불할 값이 선택되면 환불 갯수를 입력받는다. ***//
			System.out.println("------------------------------");
			System.out.println("환불 갯수를 입력해주세요.");
			int refundCount = sc.nextInt();
			// *** 입력된 수량이 내가 가진 수량을 초과하면 안됨 ***//
			if (refundCount > shoppingHash.get(num).getQuantity()) {
				System.out.println("입력값이 수량을 초과했습니다.");
				System.out.println("재입력 해주세요.");
				System.out.println("------------------------------");
				productRefund(userId);
				return;
			} else {
				// *** 정상적으로 수량을 입력했으면 수량 빼주기 *** //
				int editQuantity = shoppingHash.get(num).getQuantity() - refundCount;
				ShoppingMain shoppingMain = shoppingHash.get(num);
				shoppingMain.setQuantity(editQuantity);

				// *** 수량이 빠진만큼 가격빼주기 *** //
				int editPrice = shoppingHash.get(num).getTotapPrice()
						- (refundCount * shoppingHash.get(num).getPrice());
				shoppingMain.setQuantity(editQuantity);
				shoppingMain.setTotapPrice(editPrice);

				// *** 수량이 '0'이면 해당 객체 삭제 *** //
				if (editPrice == 0) {
					shoppingMain = shoppingHash.get(num);
					int index = shoppingList.indexOf(shoppingMain);
					shoppingHash.remove(num);
					shoppingList.remove(index);
				}

				// *** 남은 고객 리스트 출력 *** //
				int count2 = 0;
				for (ShoppingMain e : shoppingList) {
					if (userId.equals(e.getUserID())) {
						count2++;
						if (count2 != 0)
							e.shoppingString();
					}
				}
				// *** 환불 후 제품이 안남았을 때 *** //
				if (count2 == 0) {
					System.out.println("------------------------------");
					System.out.println("환불 후 고객님의 남은 제품이 없습니다.");
					return;
				}
				// *** Product Class의 HashMap, ArrayList 값 수정 *** //
				ProductMain productmain = productService.productHash.get(userId);
				int index = productService.productList.indexOf(productmain);

				productService.productHash.get(userId).setQuantity(editQuantity); // 해쉬맵 값 바꾸기
				productService.productList.get(index).setQuantity(editQuantity); // 리스트 값 바꾸기
				productService.FileSave();
				System.out.println("------------------------------");
				System.out.println("환불이 완료되었습니다.");
			}

		}

	}
	
	public void customerBuyList(String inputId) throws IOException {

		String userId = inputId;
		int listCount = 0;
		ProductService productService = new ProductService();
		productService.Fileread();

		// *** 고객이 제품을 가지고 있는지 판단하고 고객의 구매리스트 출력 ***//
		for (ShoppingMain e : shoppingList) {
			if (userId.equals(e.getUserID())) {
				listCount++;
				if (listCount != 0)
					e.shoppingString();
			}
		}
		// *** 고객의 구매리스트가 없을 때 *** //
		if (listCount == 0) {
			System.out.println("해당 고객은 제품이 없습니다.");
			return;
		}

	}

	public void view() {
		if (shoppingList.size() != 0) {
			for (ShoppingMain shopping : shoppingList) {
				shopping.shoppingString();
			}
		} else if (shoppingList.size() == 0) {
			System.out.println("주문 목록이 없습니다.");
		}

	}

	// txt파일 저장
	public void FileSave() {
		String path = "shopping.txt";
		File file = new File(path);
		BufferedWriter writer = null;
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
			writer = new BufferedWriter(new FileWriter(path, false));
			for (ShoppingMain shopping : shoppingList) {
				String str;
				str = shopping.getProductOrderNo() + "," + shopping.getUserID() + "," + shopping.getProductID() + ","
						+ shopping.getDate() + "," + shopping.getQuantity() + "," + shopping.getPrice() + ","
						+ shopping.getTotapPrice();
				writer.append(str);
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// txt파일 가져오기
	public void Fileread() throws IOException {
		String path = "shopping.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				int orderNo = Integer.parseInt(data[0]);
				String uid = data[1];
				String pid = data[2];
				String date = data[3];
				int quantity = Integer.parseInt(data[4]);
				int price = Integer.parseInt(data[5]);
				int total = Integer.parseInt(data[6]);

				ShoppingMain shoppingmain = new ShoppingMain(orderNo, uid, pid, date, quantity, price, total);
				shoppingList.add(shoppingmain);
				shoppingHash.put(orderNo, shoppingmain);
			}

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}