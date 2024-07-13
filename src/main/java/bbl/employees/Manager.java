package bbl.employees;

import org.json.JSONObject;

public class Manager extends Employee
{
	float factor;
	public Manager()
	{
		
	}
	public Manager(long id, int basicSalary, String department, float factor)
	{
		super(id, basicSalary, department);
		this.factor=factor;
	}

	public float getFactor() 
	{
		return factor;
	}

	public void setFactor(float factor) 
	{
		this.factor = factor;
	}
	
	public int computeSalary()
	{
		return (int)(super.computeSalary()*factor);
	}
	@Override
	protected void fillJSONObjct(JSONObject jsonObject) 
	{
		if(!jsonObject.has("className"))
		{
			jsonObject.put("className", getClass().getName());
		}
		super.fillJSONObjct(jsonObject);
		jsonObject.put("factor",factor);
	}
	@Override
	protected void fillEmployee(JSONObject jsonObject)
	{
		super.fillEmployee(jsonObject);
		factor=jsonObject.getFloat("factor");
	}
}
