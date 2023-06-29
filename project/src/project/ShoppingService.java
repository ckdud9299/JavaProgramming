package project;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ShoppingService {

	ArrayList<ShoppingMain> list = new ArrayList<>();
	HashMap<Integer, ShoppingMain> shoppingHash = new HashMap<Integer, ShoppingMain>();

	Scanner sc = new Scanner(System.in);
	
	ProductService productService = new ProductService();	

	public void productBuy(String id) throws IOException {
		
		productService.Fileread();
		
		// 물품 목록 보여주기
		System.out.println("---제품 목록---");
		System.out.println("제품ID   제품명   가격   수량");

		if (!productService.list.isEmpty()) {
			for (ProductMain productMain : productService.list) {
				System.out.println(productMain.getProductId() + " " + productMain.getProductName() + " "
						+ productMain.getPrice() + " " + productMain.getQuantity());
			}
			// 리스트가 비어있으면 view 메소드 종료
		} else if (productService.list.isEmpty()) {
			System.out.println("등록된 제품이 없습니다.");
			return;
		}

		System.out.println();

		// 주문번호 생성하기
		int orderNo = list.get(list.size() - 1).getProductOrderNo() + 1;

		// 주문자 Id 가져오기
		String uid = id;

		// 구매할 제품 ID 입력받기
		System.out.println("구매하실 제품 ID를 입력해주세요");
		String pid = sc.next();

		// 구매할 날짜 생성하기
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String date = now.format(formatter);

		// 구매할 수량 입력받기
		System.out.println("수량을 입력해주세요");
		int inputQuantity = sc.nextInt();

		// 가격 productlist에서 가져오기
		int price = productService.productHash.get(pid).getPrice();

		// 총액 계산하기
		int total = inputQuantity * price;

		//shopping 리스트와 hash에 저장
		ShoppingMain shoppingmain = new ShoppingMain(orderNo, uid, pid, date, inputQuantity, price, total);

		list.add(shoppingmain);
		shoppingHash.put(orderNo, shoppingmain);
		
		// product의 arraylist와 hashmap 수량 변경
		int productQuantity = productService.productHash.get(pid).getQuantity(); // productHash에 저장된 pid에 해당하는 수량 가져오기
		int resultProductQuanity = productQuantity - inputQuantity;
		
		ProductMain productmain = productService.productHash.get(pid);
		int index = productService.list.indexOf(productmain);
		
		productService.productHash.get(pid).setQuantity(resultProductQuanity); 
		productService.list.get(index).setQuantity(resultProductQuanity);
		productService.FileSave();
		
		
	}

	public void productRefund(String inputId) throws IOException {
		productService.Fileread();
		
		String userid = inputId;
		
		for (ShoppingMain shopping : list) {
			if (inputId.equals(shopping.getUserID())){
				shopping.shoppingString();		
			}
		}
		
		System.out.println();
		
		// 환불할 주문번호를 입력받기
		System.out.println("환불하실 주문번호를 입력해주세요");
		int orderNo = sc.nextInt();
		
		String pid = shoppingHash.get(orderNo).getProductID();
		
		// 환불할 수량 입력받기
		System.out.println("환불하실 수량을 입력해주세요");
		int inputQuantity = sc.nextInt();
		
		// product의 arraylist와 hashmap 재고 변경
		int productQuantity = productService.productHash.get(pid).getQuantity();
		int resultProductQuanity = productQuantity + inputQuantity;
		
		ProductMain productmain = productService.productHash.get(pid);
		int index1 = productService.list.indexOf(productmain);
		
		productService.productHash.get(pid).setQuantity(resultProductQuanity);
		productService.list.get(index1).setQuantity(resultProductQuanity);
		productService.FileSave();
		
		//shopping의 arraylist와 hashmap 주문수량 변경
		int orderQuantity = shoppingHash.get(orderNo).getQuantity();
		int resultOrderQuantity = orderQuantity - inputQuantity;
		
		ShoppingMain shoppingmain = shoppingHash.get(orderNo);
		int index2 = list.indexOf(shoppingmain);
		
		shoppingHash.get(orderNo).setQuantity(resultOrderQuantity);
		list.get(index1).setQuantity(resultOrderQuantity);					
	}

	// 임시로 확인하기 위한 메소드
	public void view() {
		if (list.size() != 0) {
			for (ShoppingMain shopping : list) {
				shopping.shoppingString();
			}
		} else if (list.size() == 0) {
			System.out.println("주문 목록이 없습니다.");
		}

	}

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
			for (ShoppingMain shopping : list) {
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
				list.add(shoppingmain);
				shoppingHash.put(orderNo, shoppingmain);
			}

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
