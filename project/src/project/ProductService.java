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
		System.out.println("제품id를 입력하세요 = ");
		String id = sc.next();
		
		// 중복된 id값 찾기
		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getProductNumber())) {
				System.out.println("이미 등록된 제품입니다.");
				insert();	// 중복시 insert 다시 호출
				break;
			}
		}
		// 제품번호 입력받기
		System.out.println("제품번호를 입력하세요 = ");
		String productNumber = sc.next();
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
		list.add(new ProductMain(productNumber, productName, category, price, quantity));
	}

	// 제품정보 수정
	public void edit() {

	}

	// 제품 삭제
	public void delete() {

	}
	
	// 단일제품 정보 출력
	public void SingleView() {
		

	}

	// 전체 제품정보 출력
	public void view() {

	}
	
	
	// 종료
	public void exit() {

	}
}