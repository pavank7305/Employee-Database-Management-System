package edbms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import customexception.EmployeeNotFoundException;
import customexception.InvalidChoiceException;
import customsorting.SortEmployeeByAge;
import customsorting.SortEmployeeById;
import customsorting.SortEmployeeByName;
import customsorting.SortEmployeeBySalary;

public class EmployeeManagementSystemServiceImpl implements EmployeeManagementSystemService {

	Scanner scan = new Scanner(System.in);

	Map<String, Employee> db = new LinkedHashMap<>();

	@Override
	public void addEmployee() {
		System.out.println("Enter Employee Age");
		Integer age = scan.nextInt();
		System.out.println("Enter Employee Name");
		String name = scan.next();
		System.out.println("Enter Employee Salary");
		Double salary = scan.nextDouble();
		Employee employee = new Employee(age, name, salary);
		db.put(employee.getId(), employee);
		System.out.println("Employee Record Inserted Successfully!");
		System.out.println("Your Employee Id is " + employee.getId());
	}

	@Override
	public void displayEmployee() {
		System.out.println("Enter Employee Id:");
		String id = scan.next();
		id = id.toUpperCase();
		if (db.containsKey(id)) {
			System.out.println("Employee Record Found!");
			Employee employee = db.get(id);
			System.out.println("Employee Id: " + employee.getId());
			System.out.println("Employee Age: " + employee.getAge());
			System.out.println("Employee Name: " + employee.getName());
			System.out.println("Employee Salary: " + employee.getSalary());
		} else {
			try {
				String message = "Employee with Id " + id + " is not Found!";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void displayAllEmployees() {
		if (!db.isEmpty()) {
			System.out.println("Employees Records are as follows:");
			System.out.println("--------------------------------");
			Set<String> keys = db.keySet();
			for (String key : keys) {
				System.out.println(db.get(key));
			}
		} else {
			try {
				String message = "No Employees Records to Display!";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void removeEmployee() {
		System.out.println("Enter Employee Id: ");
		String id = scan.next();
		id = id.toUpperCase();
		if (db.containsKey(id)) {
			System.out.println("Employee Record Found!");
			System.out.println(db.get(id));
			db.remove(id);
			System.out.println("Employee Record Deleted Successfully!");
		} else {
			try {
				String message = "Employee with Id: " + id + " not found!";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void removeAllEmployees() {
		if (!db.isEmpty()) {
			System.out.println("No of Employee Records before deleting: " + db.size());
			db.clear();
			System.out.println("No of Employee Records after deleting: " + db.size());
			System.out.println("Employees Records Deleted Successfully");
		} else {
			try {
				String message = "No Employee Records to Delete!";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void updateEmployee() {
		System.out.println("Enter Employee Id: ");
		String id = scan.next();
		id = id.toUpperCase();
		if (db.containsKey(id)) {
			System.out.println("Employee Record Found!");
			Employee employee = db.get(id);
			System.out.println("1:Update Age\n2:Update Name \n3:Update Salary \nEnter Choice");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter Employee Age");
				Integer age = scan.nextInt();
				employee.setAge(age);
				System.out.println("Age Set Successfully!");
				break;
			case 2:
				System.out.println("Enter Employee Name");
				String name = scan.next();
				employee.setName(name);
				System.out.println("Name Set Successfully!");
				break;
			case 3:
				System.out.println("Enter Employee Salary");
				Double salary = scan.nextDouble();
				employee.setSalary(salary);
				System.out.println("Salary Set Successfully!");
				break;
			default:
				try {
					String message = "Invalid Choice, Kindly Enter Valid Choice ";
					throw new InvalidChoiceException(message);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		} else {
			try {
				String message = "Employee with Id: " + id + " is not found";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void countEmployees() {
		System.out.println("No of Employees Records : " + db.size());
	}

	@Override
	public void sortEmployees() {
		if (db.size() >= 2) {
			List<Employee> list = new ArrayList<>();
			Set<String> keys = db.keySet();
			for (String key : keys) {
				Employee employee = db.get(key);
				list.add(employee);
			}
			System.out.println("1:Sort Employee By Id");
			System.out.println("2:Sort Employee By Age");
			System.out.println("3:Sort Employee By Name");
			System.out.println("4:Sort Employee By Salary");
			System.out.println("Enter Choice");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				Collections.sort(list, new SortEmployeeById());
				for (Employee employee : list) {
					System.out.println(employee);
				}
				break;
			case 2:
				Collections.sort(list, new SortEmployeeByAge());
				for (Employee employee : list) {
					System.out.println(employee);
				}
				break;
			case 3:
				Collections.sort(list, new SortEmployeeByName());
				for (Employee employee : list) {
					System.out.println(employee);
				}
				break;
			case 4:
				Collections.sort(list, new SortEmployeeBySalary());
				for (Employee employee : list) {
					System.out.println(employee);
				}
				break;
			default:
				try {
					String message = "Invalid Choice, Kindly Enter Valid Choice";
					throw new InvalidChoiceException(message);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			}
		} else {
			try {
				String message = "No Sufficient Employee Records to Sort";
				throw new EmployeeNotFoundException(message);
			} catch (EmployeeNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void getEmployeeWithHighestSalary() {
		if (db.size() >= 2) {
			Set<String> keys = db.keySet();
			List<Employee> list = new ArrayList<>();
			for (String key : keys) {
				list.add(db.get(key));
			}
			Collections.sort(list, new SortEmployeeBySalary());
			System.out.println("Employee With Highest Salary:" + list.get(list.size() - 1));
		} else {
			try {
				String message = "Employee Records Not Sufficient ";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void getEmployeeWithLowestSalary() {
		if (db.size() >= 2) {
			Set<String> keys = db.keySet();
			List<Employee> list = new ArrayList<>();
			for (String key : keys) {
				list.add(db.get(key));
			}
			Collections.sort(list, new SortEmployeeBySalary());
			System.out.println("Employee With Lowest Salary:" + list.get(0));
		} else {
			try {
				String message = "Employee Records Not Sufficient";
				throw new EmployeeNotFoundException(message);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
