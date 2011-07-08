package jbamboo.functions;

import java.util.TreeMap;
import java.util.HashSet;

import jbamboo.basetypes.Point;

public class Polynomial extends RealFunction {

	private TreeMap<Integer,Double> coefficients;
	
	public Polynomial() {
		coefficients = new TreeMap<Integer,Double>();
	}
	
	/**
	 * This is meant to read well!<br/>
	 * The call <code>new Polynomial(5,0,4,3,0)</code> corresponds to the
	 * polynomial 5x^4 + 0x^3 + 4x^2 + 3x + 0
	 * @param coefficientValues
	 */
	public Polynomial(Double ...coefficientValues) {
		this();
		int order = coefficientValues.length - 1;
		for (int i = 0; i < coefficientValues.length; i++, order--) {
			setCoefficient(order, coefficientValues[i]);
		}		
	}
		
	public Polynomial plus(Polynomial that) {
		Polynomial sum = new Polynomial();
		
		HashSet<Integer> keys = new HashSet<Integer>();
		keys.addAll(coefficients.keySet());
		keys.addAll(that.coefficients.keySet());
		for (Integer key : keys) {
			double coefficient = this.getCoefficient(key) + that.getCoefficient(key);
			sum.setCoefficient(key, coefficient);
		}
		return sum;
	}
	
	public Polynomial times(Polynomial that) {
		Polynomial product = new Polynomial();
		
		for (Integer outerKey : coefficients.keySet()) {
			for (Integer innerKey : that.coefficients.keySet()) {
				double coefficient = this.getCoefficient(innerKey) * that.getCoefficient(outerKey);
				product.plusEqualsCoefficient(innerKey + outerKey, coefficient);
			}
		}
		
		return product;
	}
	
	public void plusEqualsCoefficient(int power, double coefficient) {
		setCoefficient(power, getCoefficient(power) + coefficient);
	}
	
	public Double getCoefficient(int power) {
		Double coefficient = coefficients.get(power);
		return (coefficient == null) ? 0.0 : coefficient;
	}
	
	public void setCoefficient(int power, double coefficient) {
		coefficients.put(power, coefficient);
	}

	@Override
	public Double valueForPoint(Point p) {
		// TODO Auto-generated method stub
		double sum = 0.0;
		for (Integer key : coefficients.keySet()) {
			sum += coefficients.get(key) * Math.pow(p.x(), key);
		}
		return sum;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Integer exponent : coefficients.keySet()) {
			sb.append(String.format("%fx^%d + ",coefficients.get(exponent), exponent));
		}
		return sb.toString();
	}

	@Override
	public RealFunction getDerivative() {
		Polynomial derivative = new Polynomial();
		for (Integer exponent : coefficients.keySet()) {
			derivative.setCoefficient(exponent - 1, coefficients.get(exponent) * exponent);
		}
		return derivative;
	}

}
