package bbl.employees;

import org.junit.jupiter.api.BeforeEach;

import telran.employees.Company;
import telran.employees.CompanyMapsImpl;

class CompanyMapsImplTest extends CompanyTest {

	@Override
	@BeforeEach
	void setCompany() {
		company = getEmptyCompany();
		super.setCompany();
	}
	protected Company getEmptyCompany()
	{
		return new CompanyMapsImpl();
	}
}
