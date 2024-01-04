package edbms;

import java.util.Scanner;

import customexception.InvalidChoiceException;

public class EmployeeManagementSystem {

	public static void main(String[] args) {
		System.out.println("Welcome to Employee Database Management System");
		System.out.println("---------------------------------------------");
		Scanner scan = new Scanner(System.in);
		EmployeeManagementSystemService emss = new EmployeeManagementSystemServiceImpl();
		while (true) {
			System.out.println("1.Add Employee\n2.Display Employee");
			System.out.println("3.Display All Employees\n4.Remove Employee");
			System.out.println("5.Remove All Employees\n6.Update Employee");
			System.out.println("7.Count Employees\n8.Sort Employees");
			System.out.println("9.Find Employee With Highest Salary");
			System.out.println("10.Find Employee With Lowest  Salary");
			System.out.println("11.Exit\nEnter Choice");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				emss.addEmployee();
				break;
			case 2:
				emss.displayEmployee();
				break;
			case 3:
				emss.displayAllEmployees();
				break;
			case 4:
				emss.removeEmployee();
				break;
			case 5:
				emss.removeAllEmployees();
				break;
			case 6:
				emss.updateEmployee();
				break;
			case 7:
				emss.countEmployees();
				break;
			case 8:
				emss.sortEmployees();
				break;
			case 9:
				emss.getEmployeeWithHighestSalary();
				break;
			case 10:
				emss.getEmployeeWithLowestSalary();
				break;
			case 11:
				System.out.println("THANK YOU");
				scan.close();
				System.exit(0);
			default:
				try {
					String message = "Invalid Choice, kindly Enter Valid Choice";
					throw new InvalidChoiceException(message);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			System.out.println("==========================");
		}
	}
}
