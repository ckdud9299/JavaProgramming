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

	// 환불하기
	public void productRefund(String inputId) throws IOException {

		ProductService productService = new ProductService();
		productService.Fileread();

		String userid = inputId;

		// 환불내역이 있는지 없는지 확인 -> 있으면 true, 없으면 true
		boolean result = false;
		for (ShoppingMain shopping : shoppingList) {
			if (userid.equals(shopping.getUserID())) {
				result = true;
			}
		}
		
		// 환불 내역이 없을때
		if (!result) {
			System.out.println("주문 내역이 없습니다.");
			return;
		}
		// 환불 내역이 있을때
		else {
			// 환불 내역 보여주기
			for (ShoppingMain shopping : shoppingList) {
				if (inputId.equals(shopping.getUserID())) {
					shopping.shoppingString();
				}
			}

			System.out.println();

			// 환불할 주문번호를 입력받기
			System.out.println("환불하실 주문번호를 입력해주세요");
			int orderNo = sc.nextInt();

			// 환불할 주문번호가 오류일때
			if (shoppingHash.containsKey(orderNo)) {
				if (!(userid.equals(shoppingHash.get(orderNo).getUserID()))) {
					System.out.println("잘못된 입력입니다.");
					productRefund(inputId);
				}
			} else {
				System.out.println("잘못된 입력입니다.");
				productRefund(inputId);
			}

			// 환불할 주문번호가 오류가 아닐때
			String pid = shoppingHash.get(orderNo).getProductID();

			// 환불할 수량 입력받기
			System.out.println("환불하실 수량을 입력해주세요");
			int refundCount = sc.nextInt();

			ShoppingMain shoppingmain = shoppingHash.get(orderNo);
			int sIndex = shoppingList.indexOf(shoppingmain);

			// 환불 원하는 수량>주문된 수량일때 -> 처음으로 돌아가기
			if (refundCount > shoppingHash.get(orderNo).getQuantity()) {
				System.out.println("주문된 수량보다 많습니다.");
				productRefund(userid);
			}

			// 환불 원하는 수량<주문된 수량일때 -> shopping.txt 주문수량&총액 - , product.txt 재고 +
			// 환불 원하는 수량=주문된 수량일때 shopping.txt에서 해당 전체 정보 삭제, product.txt 재고 +
			else {
				if (refundCount == shoppingHash.get(orderNo).getQuantity()) {
					// 해당 전체 정보 삭제
					shoppingHash.remove(orderNo);
					shoppingList.remove(sIndex);
				} else {
					// shopping의 arraylist와 hashmap 주문수량 변경
					int editOrderQuantity = shoppingHash.get(orderNo).getQuantity() - refundCount;

					shoppingHash.get(orderNo).setQuantity(editOrderQuantity);
					shoppingList.get(sIndex).setQuantity(editOrderQuantity);

					// shopping의 arraylist와 hashmap 총액 변경
					int editTotal = editOrderQuantity * shoppingHash.get(orderNo).getPrice();

					shoppingHash.get(orderNo).setTotapPrice(editTotal);
					shoppingList.get(sIndex).setTotapPrice(editTotal);
				}

				// product의 arraylist와 hashmap 재고 변경
				int editProductQuanity = productService.productHash.get(pid).getQuantity() + refundCount;

				ProductMain productmain = productService.productHash.get(pid);
				int pIndex = productService.productList.indexOf(productmain);

				productService.productHash.get(pid).setQuantity(editProductQuanity);
				productService.productList.get(pIndex).setQuantity(editProductQuanity);
				productService.FileSave();
			}
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