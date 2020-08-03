package lt.vilkaitisvyt.Model;

import java.math.BigDecimal;

public class TotalYearlyTaxes {
	
	private BigDecimal totalTaxes;
	
	public TotalYearlyTaxes() {
		
	}

	public TotalYearlyTaxes(BigDecimal totalTaxes) {
		super();
		this.totalTaxes = totalTaxes;
	}

	public BigDecimal getTotalTaxes() {
		return totalTaxes;
	}

	public void setTotalTaxes(BigDecimal totalTaxes) {
		this.totalTaxes = totalTaxes;
	}
}
