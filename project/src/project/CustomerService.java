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
	public void insert(int stepNo) {
		int isStepNo = stepNo;

		String id = null, pw = null, name = null, gender = null, address = null, phonenumber = null;
		int age = 0;

		System.out.println("==========고객 정보를 입력합니다==========");

		/* step 1 = id 입력받기 */
		if (isStepNo == 1) {
			System.out.println("id을 입력하세요 = ");
			id = sc.next();

			if (customerHash.containsKey(id)) {
				System.out.println("이미 등록된 id입니다.");
				insert(isStepNo);
				return;
			}

			isStepNo++;
		}

		/* step 2 = pw 입력받기 */
		if (isStepNo == 2) {
			System.out.println("비밀번호를 입력하세요 = ");
			pw = sc.next();
			isStepNo++;
		}

		/* step 3 = 이름 입력받기 */
		if (isStepNo == 3) {
			System.out.println("이름을 입력하세요 = ");
			name = sc.next();
			isStepNo++;
		}

		/* step 4 = 나이 입력받기 */
		if (isStepNo == 4) {
			while (true) {
				try {
					System.out.println("나이를 입력하세요 = ");
					age = sc.nextInt();
					isStepNo++;
					break;
				} catch (InputMismatchException e) {
					System.out.println("[ERROR] 숫자를 입력해주세요.");
					sc.nextLine(); // 스트링 버퍼를 비움 -> 잘못된 입력이 남아있는 경우 반복적인 예외 발생 방지
				}
			}
		}

		/* step 5 = 성별 입력받기 */
		if (isStepNo == 5) {
			System.out.println("성별를 입력하세요.(남/여)");
			gender = sc.next();
			while (!(gender.equals("남") || gender.equals("여"))) {
				System.out.println("[ERROR] 잘못된 입력입니다. '남' 또는 '여'로 입력하세요 = ");
				gender = sc.next();
			}
			isStepNo++;
		}

		/* step 6 = 주소 입력받기 */
		if (isStepNo == 6) {
			System.out.println("주소를 입력하세요 = ");
			sc.nextLine();
			address = sc.nextLine();
			isStepNo++;
		}

		/* step 7 = 핸드폰 번호 입력받기 */
		if (isStepNo == 7) {
			System.out.println("번호를 입력하세요 = ");
			phonenumber = sc.nextLine();
			isStepNo++;
		}

		/* step 8 = 입력받은 값 저장 */
		if (isStepNo == 8) {
			CustomerMain customermain = new CustomerMain(id, pw, name, age, gender, address, phonenumber);

			customerList.add(customermain);
			customerHash.put(id, customermain);
		}
	}

	// 고객정보 수정
	public void edit(int stepNo) {
		int isStepNo = stepNo;

		String customerId = null;
		int n = 0;

		System.out.println("==========고객 정보를 수정합니다==========");

		/* step 1 = 수정할 고객 ID 입력받기 */
		if (isStepNo == 1) {
			System.out.println("수정할 고객의 ID를 입력하세요 = ");
			customerId = sc.next();
			isStepNo++;
		}

		/* step 2 = 수정할 항목 입력받기(입력받은 Id가 존재할 경우) */
		if (isStepNo == 2) {
			if (customerHash.containsKey(customerId)) {
				while (true) {
					try {
						System.out.println("1.이름 2.나이 3.성별 4.주소 5.휴대폰번호 6.비밀번호");
						System.out.println("수정하실 항목을 선택하세요 = ");
						n = sc.nextInt();

						if (n >= 1 && n <= 6) {
							isStepNo++;
							break;
						} else {
							System.out.println("[ERROR] 1부터 6까지의 숫자를 입력해주세요.");
						}
					} catch (InputMismatchException e) {
						System.out.println("[ERROR] 숫자를 입력해주세요.");
						sc.nextLine(); // 스트링 버퍼를 비움 -> 잘못된 입력이 남아있는 경우 반복적인 예외 발생 방지
					}
				}
			} else {
				System.out.println("[ERROR] 존재하지 않는 회원입니다.");
				edit(1);
				return;				
			}
		}

		/* step 3 = 수정할 정보 입력받기 */
		if (isStepNo == 3) {

			// customermain 변수에 hashmap.get(id)의 주소값을 참조하여 저장 -> hashmap과 arraylist가 동일한 개체를 가리키고 참조하기 때문에 한번에 변경가능
			CustomerMain customermain = customerHash.get(customerId);

			switch (n) {
			case 1:
				System.out.println("이름을 새로 입력하세요 = ");
				String name = sc.next();

				customermain.setName(name);

				break;
			case 2:
				while (true) {
					try {
						System.out.println("나이를 새로 입력하세요 = ");
						int age = sc.nextInt();

						customermain.setAge(age);
						
						break;
					} catch (InputMismatchException e) {
						System.out.println("[ERROR] 숫자를 입력해주세요.");
						sc.nextLine(); // 스트링 버퍼를 비움 -> 잘못된 입력이 남아있는 경우 반복적인 예외 발생 방지
					}
				}

				break;
			case 3: // 남/여 만 받기
				System.out.println("성별을 새로 입력하세요 = ");
				String gender = sc.next();

				while (!(gender.equals("남") || gender.equals("여"))) {
					System.out.println("잘못입력하셨습니다. '남' 또는 '여'로 입력하세요 = ");
					gender = sc.next();
				}

				customermain.setGender(gender);
				break;
			case 4:
				System.out.println("주소를 새로 입력하세요 = ");
				String address = sc.next();

				customermain.setAddress(address);

				break;
			case 5:
				System.out.println("휴대폰 번호을 새로 입력하세요 = ");
				String phonenumber = sc.next();

				customermain.setPhoneNumber(phonenumber);

				break;

			case 6:
				System.out.println("비밀번호을 새로 입력하세요 = ");
				String pw = sc.next();

				customermain.setPhoneNumber(pw);

				break;

			default:
				break;
			}
		}
	}

	// 고객 정보 삭제
	public void delete(int stepNo) {
		int isStepNo = stepNo;

		String id = null;

		System.out.println("==========고객 정보를 삭제합니다==========");

		/* step 1 = 삭제할 고객의 Id 입력 받기 */
		if (isStepNo == 1) {
			System.out.println("삭제할 회원의 아이디를 입력해주세요 = ");
			id = sc.next();
			isStepNo++;
		}
		
		/* step 2 = 입력 받은 회원의 정보 삭제*/
		if (isStepNo == 2) {
			if (customerHash.containsKey(id)) {
				CustomerMain customermain = customerHash.get(id);
				int index = customerList.indexOf(customermain);

				customerHash.remove(id);
				customerList.remove(index);
				System.out.println("삭제가 완료되었습니다. ");
			} else {
				System.out.println("[ERROR] 존재하지 않는 회원입니다.");
			}
		}

	}

	// 개인별 정보 출력
	public void personalView(int stepNo) {
		int isStepNo = stepNo;

		String id = null;
		
		System.out.println("==========개인별 고객 정보를 출력합니다==========");
		
		/* step 1 = 조회할 고객의 Id 입력 받기 */
		if (isStepNo == 1) {
			System.out.println("고객의 ID를 입력하세요.");
			id = sc.next();
			isStepNo++;
		}
		
		/* step 2 = 입력 받은 고객의 정보 조회 */
		if (isStepNo == 2) {
			if (customerHash.containsKey(id)) {
				System.out.printf("%-10s%-10s%-10s%-10s%-10s%-20s%-11s%n", "고객이름", "ID", "PW", "나이", "성별", "주소", "핸드폰번호");
				CustomerMain customermain = customerHash.get(id);
				customermain.customerString();
			} else {
				System.out.println("[ERROR] 존재하지 않는 회원입니다.");
			}
		}
	}

	// 전체 고객 정보 출력
	public void view(int stepNo) {		
		int isStepNo = stepNo;
		
		System.out.println("==========전체 고객 정보를 출력합니다==========");
		
		/* step 1 = 전체 고객의 정보 조회 */
		if (isStepNo == 1) {
			if (customerList.size() != 0) {
				System.out.printf("%-10s%-10s%-10s%-10s%-10s%-20s%-11s%n", "고객이름", "ID", "PW", "나이", "성별", "주소", "핸드폰번호");
				for (CustomerMain customer : customerList) {
					customer.customerString();
				}
			} else if (customerList.size() == 0) {
				System.out.println("회원 목록이 없습니다.");
			}
		}
	}

	// customer.txt에 저장
	public void FileSave() {
		String path = "customer.txt";
		File file = new File(path);
		BufferedWriter writer = null;
		try {
			if (file.createNewFile()) {
				// System.out.println("File created: " + file.getName());
			} else {
				// System.out.println("File already exists.");
			}
			writer = new BufferedWriter(new FileWriter(path, false));
			for (CustomerMain customer : customerList) {
				String str;
				str = customer.getId() + "," + customer.getPw() + "," + customer.getName() + "," + customer.getAge()
						+ "," + customer.getGender() + "," + customer.getAddress() + "," + customer.getPhoneNumber();
				writer.append(str);
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// customer.txt에 저장된 정보 불러오기
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