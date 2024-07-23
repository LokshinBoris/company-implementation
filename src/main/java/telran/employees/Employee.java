package telran.employees;

import java.util.Objects;

import org.json.JSONObject;

import telran.io.JSONable;

public class Employee implements Comparable<Employee>, JSONable
{
	private long id;
	private int basicSalary;
	private String department;
	
	public Employee()
	{		
	}
	public Employee(long id, int basicSalary, String department)
	{
		this.id = id;
		this.basicSalary = basicSalary;
		this.department = department;
	}
	@Override
	public int compareTo(Employee o)
	{
		//comparing according to id
		return (int)(this.id - o.id)%Integer.MAX_VALUE;
	}
	public long getId()
	{
		return id;
	}
	public int getBasicSalary()
	{
		return basicSalary;
	}
	public String getDepartment() 
	{
		return department;
	}
	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return id == other.id;
	}
	
	public int computeSalary() 
	{
		return basicSalary;
	}
	@Override
	public String getJSON()
	{
		JSONObject jsonObject= new JSONObject();
		fillJSONObjct(jsonObject);
		return jsonObject.toString();
	}
	
	protected void fillJSONObjct(JSONObject jsonObject) 
	{
		if(!jsonObject.has("className"))
		{
			jsonObject.put("className", getClass().getName());
		}
		jsonObject.put("id",id);
		jsonObject.put("department",department);
		jsonObject.put("basicSalary",basicSalary);
	}
	
	@Override
	public JSONable setObject(String json) 
	{
		JSONObject jsonObject = new JSONObject(json);
		String className=jsonObject.getString("className");
		try
		{
			Employee empl = (Employee) Class.forName(className).getConstructor().newInstance();
			empl.fillEmployee(jsonObject);		
			return empl; 
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected void fillEmployee(JSONObject jsonObject)
	{
		id=jsonObject.getLong("id");
		department=jsonObject.getString("department");
		basicSalary=jsonObject.getInt("basicSalary");	
	}
	
}