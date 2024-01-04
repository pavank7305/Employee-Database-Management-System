package edbms;

public class Employee {

	private String id;
	private Integer age;
	private String name;
	private Double salary;
	private static int count = 101;

	public Employee(Integer age, String name, Double salary) {
		this.id = "EMP" + count;
		count++;
		this.age = age;
		this.name = name;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Id:" + id + ", Age:" + age + ", Name:" + name + ", Salary:" + salary;
	}

}
