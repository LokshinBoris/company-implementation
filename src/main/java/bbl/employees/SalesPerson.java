package bbl.employees;

import org.json.JSONObject;

public class SalesPerson extends WageEmployee 
{
	private float percent;
	private long sales;
	public SalesPerson()
	{
		
	}
	public SalesPerson(long id, int basicSalary, String department, int hours, int wage, float percent, long sales)
	{
		super(id, basicSalary, department, hours, wage);
		this.percent=percent;
		this.sales=sales;
		
	}
	public float getPercent() 
	{
		return percent;
	}
	public void setPercent(float percent)
	{
		this.percent = percent;
	}
	public long getSales()
	{
		return sales;
	}
	public void setSales(long sales)
	{
		this.sales = sales;
	}
	
	public int computeSalary() 
	{
		return Math.round( super.computeSalary()+ (int)(sales*percent/100) );
	}

	@Override
	protected void fillJSONObjct(JSONObject jsonObject) 
	{
		if(!jsonObject.has("className"))
		{
			jsonObject.put("className", getClass().getName());
		}
		super.fillJSONObjct(jsonObject);
		jsonObject.put("percent",percent);
		jsonObject.put("sales",sales);
	}
	@Override
	protected void fillEmployee(JSONObject jsonObject)
	{
		super.fillEmployee(jsonObject);
		percent=jsonObject.getFloat("percent");
		sales=jsonObject.getLong("sales");
	}
	
}
