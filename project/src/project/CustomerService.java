package project;

import java.io.*;
import java.util.*;


public class CustomerService {
	// Customer Main에서 필드값 리스트로 만들기
	ArrayList<CustomerMain> list = new ArrayList<>();
	Scanner sc = new Scanner(System.in);

	// 고객 정보입력
	public void insert() {
		// id입력받기
		System.out.println("id을 입력하세요. ");
		String id = sc.next();

		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getId())) {
				System.out.println("이미 등록된 id입니다.");
				insert();
				break;
			}
		}
		// 이름 입력받기
		System.out.println("이름을 입력하세요. ");
		String name = sc.next();
		// 나이 입력받기
		System.out.println("나이를 입력하세요. ");
		int age = sc.nextInt();
		// 성별 입력받기
		System.out.println("성별를 입력하세요.(남/여)");
		String gender = sc.next();
		while (!(gender.equals("남") || gender.equals("여"))) {
			System.out.println("잘못입력하셨습니다. 다시 입력해주세요.");
			gender = sc.next();
		}
		// 주소 입력받기
		System.out.println("주소를 입력하세요. ");
		String address = sc.next();
		// 휴대폰 번호 입력받기
		System.out.println("번호를 입력하세요. ");
		String phonenumber = sc.next();
		// 입력받은 값 리스트에 저장
		list.add(new CustomerMain(id, name, age, gender, address, phonenumber));
	}

	// 고객정보 수정
	public void edit() {
		System.out.println("수정할 고객의 ID를 입력하세요.");
		String id = sc.next();
		int n = -1;
		int index = 0;

		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getId())) {
				System.out.println("수정하실 항목을 선택하세요.");
				System.out.println("1.이름 2.나이 3.성별 4.주소 5.번호");
				index = i;
				n = sc.nextInt();
				break;
			}
		}
		
		switch (n) {
			case -1 :
				System.out.println("없는 ID 입니다.");
				break;
			case 1:
				System.out.println("이름을 새로 입력하세요.");
				String name = sc.next();
				list.get(index).setName(name);
				break;
			case 2:
				System.out.println("나이를 새로 입력하세요.");
				int age = sc.nextInt();
				list.get(index).setAge(age);
				break;
			case 3:
				System.out.println("성별을 새로 입력하세요.");
				String gender = sc.next();
				list.get(index).setGender(gender);
				break;
			case 4:
				System.out.println("주소를 새로 입력하세요.");
				String address = sc.next();
				list.get(index).setAddress(address);
				break;
			case 5:
				System.out.println("번호을 새로 입력하세요.");
				String phonenumber = sc.next();
				list.get(index).setPhoneNumber(phonenumber);
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
		}
	}		
	

	// 고객 정보 삭제
	public void delete() {
		System.out.println("---회원을 삭제합니다.---");
		System.out.println("삭제할 회원의 아이디를 입력해주세요 : ");
		String name = sc.next();
		Iterator<CustomerMain> customerIterator = list.iterator();
		while (customerIterator.hasNext()) {
			CustomerMain customerMain = customerIterator.next();
			if (customerMain.getId().equals(name)) {
				customerIterator.remove();
				System.out.println(name + " " + "회원님 아이디가 삭제되었습니다.");
				return;
			}
		}

		System.out.println("입력하신 회원님은 없습니다.");
	}
	
	// 개인별 정보 출력
	public void personalView() {
		System.out.println("고객의 ID를 입력하세요.");
		String id = sc.next();

		for (int i = 0; i < list.size(); i++) {
			if (id.equals(list.get(i).getId())) {
				String str = list.get(i).getName() + " " + "회원" + " || " + "id = " + list.get(i).getId() + " || " + 
							 "나이 = " + list.get(i).getAge() + " || " + "성별 = " + list.get(i).getGender() + " || " + 
							 "주소 = " + list.get(i).getAddress() + " || " + "핸드폰번호 = " + list.get(i).getPhoneNumber();
				System.out.println(str);				
			}
		}
	}

	// 전체 고객 정보 출력
	public void view() {
		if (list.size() != 0) {
			for (CustomerMain customer : list) {
				customer.customerList();
			}
		} else if (list.size() == 0) {
			System.out.println("회원 목록이 없습니다.");
		}

	}
	
	// csv에 저장
	public void exit() {
		String path = "customer.csv";
		File file = new File(path);
		BufferedWriter writer = null;
	    try {
	    	if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
	    	writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"));
			for(CustomerMain customer : list) {	
				String str;
				str = customer.getId() + "," + customer.getName() + "," + customer.getAge() + "," + customer.getGender() + "," +
						customer.getPhoneNumber() + "," + customer.getAddress();
					writer.append(str);
			}
		    writer.close();			
		} catch (IOException e) {
		  System.out.println("An error occurred.");
		  e.printStackTrace();
		} 
	}
}	