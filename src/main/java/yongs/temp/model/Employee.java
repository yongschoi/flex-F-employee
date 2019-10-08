package yongs.temp.model;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Employee {
    @Id
    int id;
    String name;
    long salary;
    Map<String, String> skills;  
	
    public Employee(int id, String name, long salary, Map<String, String> skills) {
    	this.id = id;
    	this.name = name;
    	this.salary = salary;
    	this.skills = skills;
    }
	public Map<String, String> getSkills() {
		return skills;
	}
	public void setSkills(Map<String, String> skills) {
		this.skills = skills;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}

	@Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + "]";
    }
}
