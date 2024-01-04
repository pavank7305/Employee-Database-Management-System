package customsorting;

import java.util.Comparator;

import edbms.Employee;

public class SortEmployeeByAge implements Comparator<Employee>{

	@Override
	public int compare(Employee x, Employee y) {
		return x.getAge().compareTo(y.getAge());
	}

}
