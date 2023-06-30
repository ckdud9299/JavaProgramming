package project;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

//int newID = list.get(list.size()-1).getId()+1;

public class CustomerService {

	ArrayList<CustomerMain> customerList = new ArrayList<>();
	HashMap<String, CustomerMain> customerHash = new HashMap<String, CustomerMain>();

	Scanner sc = new Scanner(System.in);

	// 고객 정보입력
	public void insert() {
		// id입력받기
		System.out.println("id을 입력하세요. ");
		String id = sc.next();

		if (customerHash.containsKey(id)) {
			System.out.println("이미 등록된 id입니다.");
			insert();
			return;
		}
		// pw 입력받기
		System.out.println("비밀번호를 입력하세요. ");
		String pw = sc.next();
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

		// 입력받은 값 저장
		CustomerMain customermain = new CustomerMain(id, pw, name, age, gender, address, phonenumber);

		customerList.add(customermain);
		customerHash.put(id, customermain);
	}

	// 고객정보 수정
	public void edit() {
		System.out.println("수정할 고객의 ID를 입력하세요.");
		String id = sc.next();

		if (customerHash.containsKey(id)) {

			// 고객 메인 = 해쉬맵에서 입력받은 id의 값을 넣어준다 hashmap.get(id) = 주소값 반환
			CustomerMain customermain = customerHash.get(id);
			// 해쉬맵에서 찾은 값을 리스트indexof에 넣어줘서, 리스트의 값이 위치하는 인덱스 번호를 찾는다.
			int index = customerList.indexOf(customermain);

			System.out.println("수정하실 항목을 선택하세요.");
			System.out.println("1.이름 2.나이 3.성별 4.주소 5. 휴대폰번호 6. 비밀번호");
			int n = sc.nextInt();

			switch (n) {
			case 1:
				System.out.println("이름을 새로 입력하세요.");
				String name = sc.next();
				customermain.setName(name);
				customerList.get(index).setName(name);

				break;
			case 2:
				System.out.println("나이를 새로 입력하세요.");
				int age = sc.nextInt();
				customermain.setAge(age);

				customerList.get(index).setAge(age);

				break;
			case 3:
				System.out.println("성별을 새로 입력하세요.");
				String gender = sc.next();
				customermain.setGender(gender);

				customerList.get(index).setGender(gender);

				break;
			case 4:
				System.out.println("주소를 새로 입력하세요.");
				String address = sc.next();
				customermain.setAddress(address);

				customerList.get(index).setAddress(address);

				break;
			case 5:
				System.out.println("휴대폰 번호을 새로 입력하세요.");
				String phonenumber = sc.next();
				customermain.setPhoneNumber(phonenumber);

				customerList.get(index).setPhoneNumber(phonenumber);

				break;

			case 6:
				System.out.println("비밀번호을 새로 입력하세요.");
				String pw = sc.next();
				customermain.setPhoneNumber(pw);
				customerList.get(index).setPhoneNumber(pw);
				break;

			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		} else {
			System.out.println("입력하신 회원님은 없습니다.");
		}

	}

	// 고객 정보 삭제
	public void delete() {
		System.out.println("---회원을 삭제합니다.---");
		System.out.println("삭제할 회원의 아이디를 입력해주세요 : ");
		String id = sc.next();

		if (customerHash.containsKey(id)) {
			CustomerMain customermain = customerHash.get(id);
			int index = customerList.indexOf(customermain);

			customerHash.remove(id);
			customerList.remove(index);
		} else {
			System.out.println("입력하신 회원님은 없습니다.");
		}
	}

	// 개인별 정보 출력
	public void personalView() {
		System.out.println("고객의 ID를 입력하세요.");
		String id = sc.next();
		if (customerHash.containsKey(id)) {
			CustomerMain customermain = customerHash.get(id);
			customermain.customerString();
		} else {
			System.out.println("입력하신 회원님은 없습니다.");
		}

	}

	// 전체 고객 정보 출력
	public void view() {
		if (customerList.size() != 0) {
			for (CustomerMain customer : customerList) {
				customer.customerString();
			}
		} else if (customerList.size() == 0) {
			System.out.println("회원 목록이 없습니다.");
		}

	}

	// csv에 저장
	public void FileSave() {
		String path = "customer.txt";
		File file = new File(path);
		BufferedWriter writer = null;
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
			writer = new BufferedWriter(new FileWriter(path, false));
			for (CustomerMain customer : customerList) {
				String str;
				str = customer.getId() + "," + customer.getPw() + "," + customer.getName() + "," + customer.getAge()
						+ "," + customer.getGender() + "," + customer.getPhoneNumber() + "," + customer.getAddress();
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
	public void Fileread() {
		String path = "customer.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				String id = data[0];
				String pw = data[1];
				String name = data[2];
				int age = Integer.parseInt(data[3]);
				String gender = data[4];
				String address = data[5];
				String phonenumber = data[6];

				CustomerMain customermain = new CustomerMain(id, pw, name, age, gender, address, phonenumber);
				customerList.add(customermain);
				customerHash.put(id, customermain);
			}

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}