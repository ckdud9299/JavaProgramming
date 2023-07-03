package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
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

	//* 생성자 *//
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

	//*getter & setter  *//
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
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

	public void customerString() {
		
		System.out.printf("%-10s%-10s%-10s%-10d%-10s%-20s%-11s%n", name, id, pw, age, gender, address, phoneNumber);
	}

	// customerMainMenu 시작(Shopping Main클래스에서 처음 호출받는 메소드)
	public void customerMenu() throws IOException {
		// customerService 클래스에서 입력 내용구현
		Main restart = new Main();
		CustomerService customerService = new CustomerService();

		customerService.Fileread(); // 파일읽기
		
		int stepNo = 1;

		while (true) {
			System.out.println("==========고객관리 메인화면입니다==========");
			System.out.println("1.입력");
			System.out.println("2.수정");
			System.out.println("3.삭제");
			System.out.println("4.조회");
			System.out.println("5.전체조회");
			System.out.println("0.메인 메뉴로 돌아가기");
			System.out.println("====================================");
			// 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
			
			int menu = 0;
			
			while (true) {
				try {
					menu = sc.nextInt();

					if (menu >= 0 && menu <= 5) {
						break;
					} else {
						System.out.println("[ERROR] 0부터 6까지의 숫자를 입력해주세요.");
					}
				} catch (InputMismatchException e) {
					System.out.println("[ERROR] 숫자를 입력해주세요.");
					sc.nextLine(); // 스트링 버퍼를 비움 -> 잘못된 입력이 남아있는 경우 반복적인 예외 발생 방지
				}
			}

			switch (menu) {
			case 1:
				customerService.insert(stepNo); // 1. 입력
				break;
			case 2: {
				customerService.edit(stepNo); // 2. 수정
				break;
			}
			case 3:
				customerService.delete(stepNo); // 3. 삭제
				break;
			case 4:
				customerService.personalView(stepNo); // 4. 개인별 조회
				break;
			case 5:
				customerService.view(stepNo); // 5. 전체 고객 조회
				break;
			case 0:
				customerService.FileSave(); // 6. 종료
				restart.start(); // 0. 초기화면 돌아가기
				return;
			default:
				return;
			}
		}
	}

}