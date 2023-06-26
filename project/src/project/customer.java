package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class customer{
	
	Scanner sc = new Scanner(System.in);
	
	private String name;
	private int age;
	private String gender;
	private String address;
	private String phoneNumber;
	private String grade;
	
	customer(){}
      
	customer(String name, int age, String gender, String phoneNumber, String address){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.address = address;		
	}
      
	
	public String getName() {return name;}
    public String setName(String name) {return this.name;}
    public int getAge(){ return age;}
    public int setAge(int age){ return this.age;}  
    public String getGender() {return gender;}
    public String setGender(String gender) {return this.gender;}
    public String getAddress() {return address;}
    public String setAddress(String address) {return this.address;}
    public String getPhoneNumber() {return phoneNumber;}
    public String setPhoneNumber(String phoneNumber) {return this.phoneNumber;}
      
	
	public void customerMenu() {
		System.out.println("1.입력");
         System.out.println("2.수정");
         System.out.println("3.삭제");
         System.out.println("0.메인 메뉴로 돌아가기");
         
	     int menu = sc.nextInt();
	      
	     switch(menu) {
	         case 1 : 
	        	 insert();
	         case 2 : {
	            break;
	         }
	         case 3 : 
	        	 break;
	         case 0 :
	        	 Shopping restart = new Shopping();
	        	 restart.start();
	        	 break;
	         default : System.out.println("잘못된 입력입니다.");
	      }
      }
	
	// 1번을 입력받았을 때 실행되는 insert()
	public void insert() {
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
		

		

            
//            //읽을 라인이 없을 경우 br은 null을 리턴한다.
//			while(true) {
//				boolean result = false;
//				System.out.println("번호를 입력하세요. "); // 중복값일때 예외 처리 
//				String phonenumber = sc.next();
//				
//				try(FileReader rw = new FileReader("customerInfo.txt");
//		                BufferedReader br = new BufferedReader( rw );
//		             ){
//		            String readLine = null ;
//		            while( ( readLine =  br.readLine()) != null ){
//		            	String [] arr = readLine.split(",");
//		            	if(phonenumber.equals(arr[3])) {
//		            		System.out.println("중복된 전화번호입니다.");
//		            		continue;          		
//		            	}else if(!phonenumber.equals(arr[3])) {
//		            		result = true;
//		            		break;
//		            	}
//		            	
//
//		            }
//		        }catch ( IOException e ) {
//		            System.out.println(e);
//		        }
//					
//			}
			

		
		sc.nextLine();
		System.out.println("주소를 입력하세요. ");
		String address = sc.nextLine();
		
		customer A = new customer(name, age, gender, phonenumber, address);
		String str = A.getName()+","+A.getAge()+","+A.getGender()+","+A.getPhoneNumber()+","+A.getAddress();
		
		try(    
				FileWriter fw = new FileWriter("customerInfo.txt" ,true);
	            BufferedWriter bw = new BufferedWriter(fw);)
			{
	            bw.write(str); //버퍼에 데이터 입력
	            bw.newLine();
	            bw.flush(); //버퍼의 내용을 파일에 쓰기
	        }catch ( IOException e ) {
	            System.out.println(e);
	        }
		System.out.println("고객정보가 저장되었습니다.");
			
	}
}