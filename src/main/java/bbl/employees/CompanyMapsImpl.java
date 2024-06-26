package bbl.employees;

import java.util.*;
import java.util.Map.Entry;
//So far we do consider optimization
public class CompanyMapsImpl implements Company
{
	TreeMap<Long, Employee> employees = new TreeMap<>();
	HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	TreeMap<Float, List<Manager>> factorManagers = new TreeMap<>();
	
	@Override
	public Iterator<Employee> iterator() 
	{
		return employees.values().iterator();		
	}

	@Override
	public void addEmployee(Employee empl)
	{
		if(employees.putIfAbsent(empl.getId(), empl)!=null) throw new IllegalStateException();
			
			employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new LinkedList<Employee>()).add(empl);
			if(  empl instanceof Manager )
			{
				Manager man=(Manager) empl;
				factorManagers.computeIfAbsent(man.getFactor(), k -> new LinkedList<Manager>()).add(man);
			} 
	}

	@Override
	public Employee getEmployee(long id)
	{
		return employees.get(id);
	}

	@Override
	public Employee removeEmployee(long id)
	{
		Employee res=null;
		res=employees.remove(id);
		if(res!=null)
		{
			List<Employee> listDep=employeesDepartment.get(res.getDepartment());
			listDep.remove(res);
			if(  res instanceof Manager )
			{
				Manager man=(Manager) res;
				List<Manager> listMan=factorManagers.get(man.factor);
				listMan.remove(man);
			}		
		}
		else throw new NoSuchElementException();
		return res;
	}

	@Override
	public int getDepartmentBudget(String department) 
	{
		int res=0;
		List<Employee> emps= employeesDepartment.get(department);
		if(emps!=null)
		{
			Iterator <Employee> it=emps.iterator();
			while(it.hasNext())
			{
				res=res+it.next().computeSalary();
			}
		}
		return res;
	}

	@Override
	public String[] getDepartments()
	{
		Set<String> set=employeesDepartment.keySet();
		String[]  ret=set.stream().sorted().toArray(String[]::new);
		return ret;
	}

	@Override
	public Manager[] getManagersWithMostFactor()
	{
		Entry<Float, List<Manager>> entry= factorManagers.lastEntry();
		Manager[] res=entry.getValue().stream().toArray(Manager[]::new);
		return res;
	}

}