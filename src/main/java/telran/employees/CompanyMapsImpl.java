package telran.employees;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

import telran.io.Persistable;
//So far we do consider optimization
public class CompanyMapsImpl implements Company,  Persistable
{
	TreeMap<Long, Employee> employees = new TreeMap<>();
	HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	TreeMap<Float, List<Manager>> factorManagers = new TreeMap<>();
	
	private class CompanyIterator implements Iterator<Employee>{
		Iterator<Employee> iterator = employees.values().iterator();
		Employee prev;
		@Override
		public boolean hasNext() {
			
			return iterator.hasNext();
		}

		@Override
		public Employee next() {
			prev = iterator.next();
			return prev;
		}
		@Override
		public void remove() {
			iterator.remove();
			removeFromIndexMaps(prev);
		}
		
	}
	@Override
	public Iterator<Employee> iterator() {
		
		return new CompanyIterator();
	}
	
	public int getSize()
	{
		return employees.size();
		
	}
	public Employee removeEmployee(long id) {
		Employee empl = employees.remove(id);
		if(empl == null) {
			throw new NoSuchElementException();
		}
		removeFromIndexMaps(empl);
		return empl;
	}

	private void removeFromIndexMaps(Employee empl) {
		removeFromIndexMap(employeesDepartment, empl.getDepartment(), empl);
		if(empl instanceof Manager) {
			Manager manager = (Manager)empl;
			removeFromIndexMap(factorManagers, manager.factor, manager);
		}
	}
	private <K, V extends Employee> void removeFromIndexMap(Map<K, List<V>> map, K key, V empl) {
		map.computeIfPresent(key, (k, v) -> {
			v.remove(empl);
			return v.isEmpty() ? null : v;
		});
		
	}
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

	@Override
	public void save(String filePathStr)
	{
		try(PrintWriter output = new PrintWriter(new FileWriter(filePathStr));)
		{
			String jsonStr = new String();
			for(Employee empl:this)
			{
				jsonStr=empl.getJSON();
				output.println(jsonStr);
			}
		}
		catch (FileNotFoundException e)
		{	
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void restore(String filePathStr) 
	{
		
		try(BufferedReader input = new BufferedReader(new FileReader(filePathStr));)
		{
			input.lines().forEach(      (s)->
										{
											Employee empl=new Employee();
											empl=(Employee)empl.setObject(s);
											addEmployee(empl);
										}
								 );
			
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}