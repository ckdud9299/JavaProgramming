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
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShoppingService {

	ArrayList<ShoppingMain> shoppingList = new ArrayList<>();
	HashMap<Integer, ShoppingMain> shoppingHash = new HashMap<Integer, ShoppingMain>();

	Scanner sc = new Scanner(System.in);

	// 구매하기
	public void productBuy(String inputId, int stepNo) throws IOException {

		// 제품 정보 파일 읽어오기
		ProductService productService = new ProductService();
		productService.Fileread();

		int isStepNo = stepNo;

		int orderNo = 0, inputQuantity = 0, price = 0, total = 0;
		String uid = null, pid = null, date = null;

		/* step 1 = 제품 목록 출력하기 */
		if (isStepNo == 1) {
			System.out.println("==========제품 목록==========");
			System.out.printf("%-10s%-10s%-10s%-10s%-10s\n", "제품이름", "제품ID", "카테고리", "가격", "수량");

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
			isStepNo++;
		}

		/* step 2= 주문번호 생성하기 */
		if (isStepNo == 2) {
			orderNo = shoppingList.get(shoppingList.size() - 1).getProductOrderNo() + 1;
			isStepNo++;
		}

		/* step 3 = 주문자 Id 가져오기 */
		if (isStepNo == 3) {
			uid = inputId;
			isStepNo++;
		}

		/* step 4 = 구매할 제품 ID 입력받기 */
		if (isStepNo == 4) {
			System.out.println("구매하실 제품 ID를 입력해주세요 = ");
			pid = sc.next();

			// 입력받은 제품 ID가 목록에 없을때 예외처리
			if (!(productService.productHash.containsKey(pid))) {
				System.out.println("[ERROR] 존재하지 않는 제품 ID 입니다.");
				productBuy(inputId, 1);
			}

			// 입력받은 제품 ID의 재고가 0일때
			if (productService.productHash.get(pid).getQuantity() == 0) {
				System.out.println("해당 제품의 재고가 없습니다.");
				return;
			}

			isStepNo++;
		}

		/* step 5 = 구매할 날짜 생성하기 */
		if (isStepNo == 5) {
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			date = now.format(formatter);
			isStepNo++;
		}
		/* step 6 = 구매할 수량 입력받기 */
		if (isStepNo == 6) {
			while (true) {
				try {
					System.out.println("수량을 입력해주세요 = ");
					inputQuantity = sc.nextInt();

					// 입력받은 수량이 0일때
					if (inputQuantity == 0) {
						System.out.println("[ERROR] 0을 입력하셨습니다.");
						continue;
					} else if (inputQuantity > productService.productHash.get(pid).getQuantity()) {
						System.out.println("재고보다 많이 입력하셨습니다.");
						continue;
					}
					isStepNo++;
					break;
				} catch (InputMismatchException e) {
					System.out.println("[ERROR] 숫자를 입력해주세요.");
					sc.nextLine();
				}
			}
		}

		/* step 7 = 가격 productlist에서 가져오기 */
		if (isStepNo == 7) {
			price = productService.productHash.get(pid).getPrice();
			isStepNo++;
		}

		/* step 8 = 총액 계산하기 */
		if (isStepNo == 8) {
			total = inputQuantity * price;
			isStepNo++;
		}

		/* step 9 = shopping 리스트와 hash에 저장 */
		if (isStepNo == 9) {
			ShoppingMain shoppingmain = new ShoppingMain(orderNo, uid, pid, date, inputQuantity, price, total);

			shoppingList.add(shoppingmain);
			shoppingHash.put(orderNo, shoppingmain);
			isStepNo++;
		}

		/* step 10 = product의 arraylist와 hashmap 수량 변경 */
		if (isStepNo == 10) {
			int productQuantity = productService.productHash.get(pid).getQuantity(); // productHash에 저장된 pid에 해당하는 재고
																						// 가져오기
			int resultProductQuanity = productQuantity - inputQuantity; // 변경될 재고 계산

			ProductMain productmain = productService.productHash.get(pid);
			int index = productService.productList.indexOf(productmain);

			productService.productHash.get(pid).setQuantity(resultProductQuanity); // productHash 재고 변경
			productService.productList.get(index).setQuantity(resultProductQuanity); // productList 재고 변경
			productService.FileSave(); // product.txt 저장
		}

	}

	public void productRefund(String inputId, int stepNo) throws IOException {
		int rfStepNo = stepNo;
		String userId = inputId;
		// *** 고객 정보, 제품정보 파일로 받아와서 ShoppingList에 넣기 ***//
		ProductService productService = new ProductService();
		productService.Fileread();
		boolean result = true;
		boolean result2 = true;
		int num = 0;
		int refundCount = 0;
		int editQuantity = 0;
		int editPrice = 0;
		int index = 0;
		String pid = null;

		// *** step1 고객이 제품을 가지고 있는지 확인 후 고객 구매리스트 출력 *** //
		if (rfStepNo == 1) {
			for (ShoppingMain e : shoppingList) {
				if (userId.equals(e.getUserID())) {
					result = false;
					if (result)
						e.shoppingString();
				}
			}
			// *** 고객의 구매리스트가 없을 때 ***//
			if (result) {
				System.out.println("[ERROR]" + rfStepNo + "해당 고객은 제품이 없습니다.");
				return;
			}
			rfStepNo++;
		}
		// *** step2 환불할 제품의 주문번호 입력받기 *** //
		if (rfStepNo == 2) {
			while (true) {
				try {
					System.out.println("------------------------------");
					System.out.println("환불할 제품의 주문번호를 입력하세요 = ");
					num = sc.nextInt();
					rfStepNo++;
					break;

				} catch (Exception e) {
					System.out.println("[ERROR]" + rfStepNo + "숫자를 입력해주세요");
				}
			}
		}
		// *** step3 환불할 항목의 값이 구매자의 범위내에 없을 때 or 해쉬맵에 저자되어 있지 않을때 ***//
		if (rfStepNo == 3) {
			if (!shoppingHash.containsKey(num) || !userId.equals(shoppingHash.get(num).getUserID())) {
				System.out.println("[ERROR]" + rfStepNo + "입력값이 초과되었습니다.");
				System.out.println("------------------------------");
				productRefund(userId, 2);
				return;
			} else {
				pid = shoppingHash.get(num).getProductID(); // shopping해쉬맵의 키값의 제품 키값 가져오기
				rfStepNo++;
			}
		}

		// *** step4 환불할 값이 선택되면 환불 갯수를 입력받는다. ***//
		if (rfStepNo == 4) {
			while (true) {
				try {
					System.out.println("------------------------------");
					System.out.println("환불 갯수를 입력해주세요.");
					refundCount = sc.nextInt();
					rfStepNo++;
					break;
				} catch (Exception e) {
					System.out.println("[ERROR]" + rfStepNo + "숫자를 입력해주세요");
				}
			}
		}
		// *** step5 환불할 값이 내가가진 수량을 초과했을 때. ***//
		if (rfStepNo == 5) {
			if (refundCount > shoppingHash.get(num).getQuantity()) {
				System.out.println("[ERROR]" + rfStepNo + "입력값이 수량을 초과했습니다.");
				System.out.println("------------------------------");
				productRefund(userId, 4);
				return;
			} else {
				rfStepNo++;
			}

		}
		// *** step6 정상적으로 수량을 입력했으면 수량 및 가격 처리 ***//
		if (rfStepNo == 6) {
			// *** 입력된 수량 빼주기 *** //
			editQuantity = shoppingHash.get(num).getQuantity() - refundCount;
			ShoppingMain shoppingMain = shoppingHash.get(num);
			System.out.println(shoppingMain);
			System.out.println();
			shoppingMain.setQuantity(editQuantity); // 해쉬맵 수량 변경

			// int sIndex = shoppingList.indexOf(shoppingMain);
			// shoppingList.get(sIndex).setQuantity(editQuantity); //리스트 수량변경

			// *** 수량이 빠진만큼 가격빼주기 *** //
			editPrice = shoppingHash.get(num).getTotapPrice() - (refundCount * shoppingHash.get(num).getPrice());
			shoppingMain.setTotapPrice(editPrice); // 해쉬맵 수량변경
			// shoppingList.get(sIndex).setTotapPrice(editPrice);

			// *** 수량이 '0'이면 해당 객체 삭제 *** //
			if (editPrice == 0) {
				shoppingMain = shoppingHash.get(num);
				index = shoppingList.indexOf(shoppingMain);
				shoppingHash.remove(num);
				shoppingList.remove(index);
			}
			rfStepNo++;
		}

		// *** step7 고객의 남은 물품 리스트 출력 *** //
		if (rfStepNo == 7) {
			for (ShoppingMain e : shoppingList) {
				if (userId.equals(e.getUserID())) {
					result2 = false;
					if (result2)
						e.shoppingString();
				}
			}
			rfStepNo++;
		}

		// *** step8 환불 후 제품이 안남았을 때 *** //
		if (rfStepNo == 8) {
			if (result2) {
				System.out.println("------------------------------");
				System.out.println("환불 후 고객님의 남은 제품이 없습니다.");
				return;
			}
			rfStepNo++;
		}

		// *** step9 Product Class의 HashMap, ArrayList 값 수정 *** //
		if (rfStepNo == 9) {
			try {
				ProductMain productmain = productService.productHash.get(pid);
				index = productService.productList.indexOf(productmain);
				productService.productHash.get(pid).setQuantity(editQuantity); // 해쉬맵 값 바꾸기
				productService.productList.get(index).setQuantity(editQuantity); // 리스트 값 바꾸기
				productService.FileSave();
				System.out.println("------------------------------");
				System.out.println("환불이 완료되었습니다.");

			} catch (Exception e) {
				System.out.println("환불은 완료되었고, 해당 제품은 품절입니다.");
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