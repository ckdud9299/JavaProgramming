package project;

import java.io.*;
import java.util.*;

public class ProductService {
	// Customer Main에서 필드값 리스트로 만들기
	ArrayList<ProductMain> list = new ArrayList<>();
	Scanner sc = new Scanner(System.in);

	// 제품 정보입력
	public void insert() {
		// id입력받기
		System.out.println("제품의 id를 입력하세요 = ");
		String productId = sc.next();
		
		// 중복된 id값 찾기
		for (int i = 0; i < list.size(); i++) {
			if (productId.equals(list.get(i).getProductId())) {
				System.out.println("이미 등록된 제품입니다.");
				insert();	// 중복시 insert 다시 호출
				break;
			}
		}
		// 제품명 입력받기
		System.out.println("제품명을 입력하세요 = ");
		String productName = sc.next();
		// 카테고리 입력받기
		System.out.println("카테고리를 입력하세요 = ");
		String category = sc.next();
		// 가격 입력받기
		System.out.println("가격을 입력하세요 = ");
		int price = sc.nextInt();
		// 수량 입력받기
		System.out.println("수량을 등록하세요 = ");
		int quantity = sc.nextInt();
		// 입력받은 값 리스트에 저장
		list.add(new ProductMain(productId, productName, category, price, quantity));
	}

	// 제품정보 수정
	public void edit() {
		System.out.println("수정할 제품 id를 입력하세요.");
		String productId = sc.next();
		int n = -1;
		int index = 0;

		for (int i = 0; i < list.size(); i++) {
			if (productId.equals(list.get(i).getProductId())) {
				System.out.println("수정하실 항목을 선택하세요.");
				System.out.println("1.제품명 2.카테고리 3.가격 4.수량");
				index = i;
				n = sc.nextInt();
				break;
			}
		}
		
		switch (n) {
			case -1 :
				System.out.println("없는 제품 id 입니다.");
				break;
			case 1:
				System.out.println("제품명을 새로 입력하세요.");
				String productName = sc.next();
				list.get(index).setProductName(productName);
				break;
			case 2:
				System.out.println("카테고리를 새로 입력하세요.");
				String category = sc.next();
				list.get(index).setCategory(category);
				break;
			case 3:
				System.out.println("가격을 새로 입력하세요.");
				int price = sc.nextInt();
				list.get(index).setPrice(price);
				break;
			case 4:
				System.out.println("수량을 새로 입력하세요.");
				int quantity = sc.nextInt();
				list.get(index).setQuantity(quantity);
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
		}
	}

	// 제품 삭제
	public void delete() {
		System.out.println("---제품을 삭제합니다.---");
		System.out.println("삭제할 제품 id를 입력해주세요 : ");
		String productId = sc.next();
		Iterator<ProductMain> productIterator = list.iterator();
		while (productIterator.hasNext()) {
			ProductMain productMain = productIterator.next();
			if (productMain.getProductId().equals(productId)) {
				productIterator.remove();
				System.out.println(productId + " " + "제품 아이디가 삭제되었습니다.");
				return;
			}
		}

		System.out.println("입력하신 제품은 없습니다.");

	}
	
	 // 단일제품 정보 출력
	   public void SingleView() {
	      System.out.println("제품의 Id를 입력하세요 = ");
	      String productId = sc.next();

	      for (ProductMain productMain : list) {
	         if (productMain.getProductId().equals(productId)) {
	            productMain.ProductList();
	         }
	      }
	   }

	   // 전체 제품정보 출력
	   public void view() {
	      
	      if(list.size() != 0) {
	         for(ProductMain ProductMain : list) {
	            ProductMain.ProductList();
	         }
	      }else if(list.size() == 0) {
	         System.out.println("등록된 제품이 없습니다.");
	         return;
	      }
	   }
	
	
	// 종료
	public void exit() {
		String path = "product.csv";
		File file = new File(path);
		BufferedWriter writer = null;
	    try {
	    	if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
	    	writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"));
			for(ProductMain product : list) {	
				String str;
				str = product.getProductId() + "," + product.getProductName() + "," + product.getCategory() + "," + 
					  product.getPrice() + "," + product.getQuantity();
				writer.append(str);
			}
		    writer.close();			
		} catch (IOException e) {
		  System.out.println("An error occurred.");
		  e.printStackTrace();
		} 
	}
}