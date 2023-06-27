package project;

import java.io.*;
import java.util.*;

class CustomerService {
	
	ArrayList<CustomerMain> list = new ArrayList<>();
	
	Scanner sc = new Scanner(System.in);
	
	public void insert() {
		System.out.println("id을 입력하세요. ");
		String id = sc.next();		
		
		System.out.println("이름을 입력하세요. ");
		String name = sc.next();
		
		System.out.println("나이를 입력하세요. ");
		int age = sc.nextInt();
		
		System.out.println("성별를 입력하세요.(남/여)"); 
		String gender = sc.next();		
		while(!(gender.equals("남") || gender.equals("여"))) {
			System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
			gender = sc.next();
		}
		
		System.out.println("주소를 입력하세요. ");
		String address = sc.nextLine();
		
		sc.nextLine();
		
		System.out.println("번호를 입력하세요. ");
		String phonenumber = sc.next();        		
		
		list.add(new CustomerMain(id,name,age,gender,address,phonenumber));
		
		System.out.println("--test--");
		
		for(int i = 0; i < list.size(); i++) {
			CustomerMain member = list.get(i);
			System.out.println("id : "+id+" "+", name : "+name+", age : "+age);
		}
	}
	
	
	
}

