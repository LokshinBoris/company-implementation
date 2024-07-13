package bbl.employees;

import org.junit.jupiter.api.BeforeEach;

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
