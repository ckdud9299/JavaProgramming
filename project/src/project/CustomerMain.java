package project;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class CustomerMain{
	
	Scanner sc = new Scanner(System.in);
	private String id;
	private String name;
	private int age;
	private String gender;
	private String address;
	private String phoneNumber;
	
	/*
	 * 	///////////////생성자/////////////
	 */
	CustomerMain(){}
      
	CustomerMain(String id, String name, int age, String gender, String address, String phoneNumber){
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;		
		this.phoneNumber = phoneNumber;
	}
////////////////////생성자 완료/////////////////////////////
      
	
	/*
	 *  //////////////getter & setter 시작//////////////
	 */
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public String getGender() {return gender;}
	public void setGender(String gender) {this.gender = gender;}
	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}
	public String getPhoneNumber() {return phoneNumber;}
	public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
	
	/*
	 *  //////////////getter & setter 완료//////////////
	 */
	
	// customerMainMenu 시작(Shopping Main클래스에서 처음 호출받는 메소드)
	public void customerMenu() {
		System.out.println("1.입력");
        System.out.println("2.수정");
        System.out.println("3.삭제");
        System.out.println("0.메인 메뉴로 돌아가기");
         // 메뉴 번호 입력받고 번호에 따라 CustomerService 메소드 호출
	     int menu = sc.nextInt();
	     CustomerService customerService = new CustomerService();
	     
	     switch(menu) {
	         case 1 : 
	        	 customerService.insert();	//1. 입력
	        	 break;
	         case 2 : {
	        	 customerService.edit();	//2. 수정
	            break;
	         }
	         case 3 : 
	        	 customerService.delete();	//3. 삭제
	        	 break;
	         case 0 :
	        	 Shopping restart = new Shopping();	//0. 돌아가기
	        	 restart.start();
	        	 break;
	         default : System.out.println("잘못된 입력입니다.");
	      }
      }
	
		
}