package customsorting;

import java.util.Comparator;

import edbms.Employee;

public class SortEmployeeBySalary implements Comparator<Employee> {

	@Override
	public int compare(Employee x, Employee y) {
		return x.getSalary().compareTo(y.getSalary());
	}

}
