package com.test.ga;

/***
 * 染色体类
 * @author Administrator
 *计算函数 f(x,y,z) = xyz*sin(xyz)的最大值
 */
public class Chromosome {
	
	private double fitness=-1;//代表未计算适应函数；
	private double select=-1;//选择概率
	private char[] chromo;//染色体串
	private Randomizer random;
	private int lower;
	private int upper;
	
	public Chromosome(int lower,int upper,int length){
		this.lower=lower;
		this.upper=upper;
		random=new Randomizer(lower,upper);
		chromo=random.nextBitArray(length);
	}
	
	/**
	 * 克隆一个染色体
	 */
	public Chromosome clone()
	{
		Chromosome c=new Chromosome(lower,upper,chromo.length);
		char[] temp=new char[c.chromo.length];
		System.arraycopy(chromo,0,temp,0,chromo.length);
		c.setChromo(temp);
		return c;
		
	}

	public char[] getChromo() {
		return chromo;
	}

	public void setChromo(char[] chromo) {
		this.chromo = chromo;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getLower() {
		return lower;
	}

	public void setLower(int lower) {
		this.lower = lower;
	}

	public Randomizer getRandom() {
		return random;
	}

	public void setRandom(Randomizer random) {
		this.random = random;
	}

	public double getSelect() {
		return select;
	}

	public void setSelect(double select) {
		this.select = select;
	}

	public int getUpper() {
		return upper;
	}

	public void setUpper(int upper) {
		this.upper = upper;
	}

}
