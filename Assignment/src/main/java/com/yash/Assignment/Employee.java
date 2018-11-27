package com.yash.Assignment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "excelemployee")
public class Employee {

	@Id
	private double eId;

	@Column
	private String eName;

	@Column
	private String designation;

	@Column
	private double salary;

	@Column
	private String dept;

	public double geteId() {
		return eId;
	}

	public void seteId(double eId) {
		this.eId = eId;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [eId=" + eId + ", eName=" + eName + ", designation=" + designation + ", salary=" + salary
				+ ", dept=" + dept + "]";
	}

	public Employee(double eId, String eName, String designation, double salary, String dept) {
		super();
		this.eId = eId;
		this.eName = eName;
		this.designation = designation;
		this.salary = salary;
		this.dept = dept;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}
}
