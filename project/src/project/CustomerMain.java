package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CustomerMain {

	Scanner sc = new Scanner(System.in);
	private String id;
	private String pw;
	private String name;
	private int age;
	private String gender;
	private String address;
	private String phoneNumber;

	/*
	 * ///////////////생성자/////////////
	 */
	CustomerMain() {
	}

	public CustomerMain(String id, String pw, String name, int age, String gender, String address, String phoneNumber) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
////////////////////생성자 완료/////////////////////////////

	/*
	 * //////////////getter & setter 시작//////////////
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw() {
		return id;
	}

	public void setPw(String pw) {
		this.id = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*
	 * //////////////getter & setter 완료//////////////
	 */

	public void customerList() {
		StringBuffer sb = new StringBuffer();
		sb.append(name + " ");
		sb.append("회원" + " || ");
		sb.append("id = ");
		sb.append(id + " || ");
		sb.append("pw = ");
		sb.append(pw + " || ");
		sb.append("나이 = ");
		sb.append(age + " || ");
		sb.append("성별 = ");
		sb.append(gender + " || ");
		sb.append("주소 = ");
		sb.append(address + " || ");
		sb.append("핸드폰번호 = ");
		sb.append(phoneNumber);
		System.out.println(sb);
	}

	// customerMainMenu 시작(Shopping Main클래스에서 처음 호출받는 메소드)
	public void customerMenu() throws IOException {
		// customerService 클래스에서 입력 내용구현
		Main restart = new Main();
		CustomerService customerService = new CustomerService();

		customerService.Fileread(); // 파일을 불러와 list와 hashmap에 저장하는 메소드 불러오기

		while (true) {
			System.out.println("-----------------------");
			System.out.println("1.입력");
			System.out.println("2.수정");
			System.out.println("3.삭제");
			System.out.println("4.조회");
			System.out.println("5.전체조회");
			System.out.println("6.종료");
			System.out.println("0.메인 메뉴로 돌아가기");
			System.out.println("-----------------------");
			// 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
			int menu = sc.nextInt();

			switch (menu) {
			case 1:
				customerService.insert(); // 1. 입력
				break;
			case 2: {
				customerService.edit(); // 2. 수정
				break;
			}
			case 3:
				customerService.delete(); // 3. 삭제
				break;
			case 4:
				customerService.personalView(); // 4. 개인별 조회
				break;
			case 5:
				customerService.view(); // 5. 전체 고객 조회
				break;
			case 6:
				customerService.exit(); // 6. 종료
				break;
			case 0:
				restart.start(); // 0. 초기화면 돌아가기
				break;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

}