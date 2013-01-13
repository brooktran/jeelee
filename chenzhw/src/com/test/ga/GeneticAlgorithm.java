package com.test.ga;

import java.util.ArrayList;
import java.util.List;


public class GeneticAlgorithm {
	private int generation;// 进化代数

	private int population;// 种群数量

	private double crossoverPossibility;// 繁殖概率

	private double mutationPossibility;// 变异概率

	private FitnessCalculate calculator = new FunctionFitness();// 适应函数计算

	private List<Chromosome> clist = new ArrayList<Chromosome>();

	private Randomizer random1;// 随机数生成器1,用于生成变异位和交配位

	private Randomizer random2 = new Randomizer(0, 1);// 随机数生成器2，用于生成0-1之间的概率

	private GenerationDetail detail = new GenerationDetail();

	public GeneticAlgorithm(int population, double sp, double cp, double mp,
			int length) {
		this.population = population;
		this.crossoverPossibility = cp;
		this.mutationPossibility = mp;
		random1 = new Randomizer(0, length - 1);
		generatePopulation(0, 255, length);// 用24位表示一组x,y,z的值
	}

	/**
	 * 生成初始种群
	 * 
	 * @param lower
	 * @param upper
	 * @param length
	 */
	private void generatePopulation(int lower, int upper, int length) {
		// 随机生成染色体
		for (int i = 0; i < population; i++) {
			clist.add(new Chromosome(lower, upper, length));

		}
		// 计算染色体的适应值
		evaluate();
	}

	/**
	 * 计算群体的适应值
	 */
	private void evaluate() {
		double sum = 0.0;
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (Chromosome c : clist) {
			double fitness = calculator.calculate(c.getChromo());
			if (fitness > max) {
				max = fitness;
			}
			if (fitness < min) {
				min = fitness;
			}
			c.setFitness(fitness);
			sum += fitness;
		}
		detail.setMaxFitness(max);
		detail.setMinFitness(min);
		detail.setAverageFitness(sum / population);
		for (Chromosome c : clist) {
			c.setSelect((c.getFitness()) / sum);// 设置选择概率
		}
	}

	/**
	 * 在后代中选择新种群
	 */
	private void selectPopulation() {
		List<Chromosome> tempList = new ArrayList<Chromosome>();
		for (Chromosome c : clist) {		
			long expectation = Math.round(c.getSelect() * population);
			for (int i = 0; i < expectation; i++) {
				tempList.add(c.clone());
			}
		}
		// 如果选择种群数量大于种群规定数量，则淘汰适应值最小的染色体
		while (tempList.size() > population) {
			int location = 0;
			double min = tempList.get(0).getFitness();
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).getFitness() < min) {
					location = i;
				}
			}
			tempList.remove(location);
		}
		// 如果选择种群数量小于种群规定数量，则加入适应值最大的染色体。 相同的染色体经过变异和交换同样会产生新解
		while (tempList.size() < population) {
			int location = 0;
			double max = tempList.get(0).getFitness();
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).getFitness() > max) {
					location = i;
				}
			}
			tempList.add(tempList.get(location).clone());
		}
		clist = tempList;
	}

	/**
	 * 交换两个染色体
	 * 
	 * @param c1
	 * @param c2
	 * @param location
	 */
	private void crossover(Chromosome c1, Chromosome c2) {
		if (c1.getChromo().length != c2.getChromo().length) {
			throw new IllegalStateException("染色体长度不同!");
		}
		// 交换染色体上的基因
		// 随机确定交配位
		int location = random1.nextInteger();
		for (int i = location; i < c1.getChromo().length; i++) {
			char temp;
			temp = c1.getChromo()[i];
			c1.getChromo()[i] = c2.getChromo()[i];
			c2.getChromo()[i] = temp;
		}
	}

	
	/**
	 * 选择--交换--变异
	 * 交配整个种群,完成一次进化
	 * 
	 */
	public void crossoverPopulation() {
		
		//比较所有染色体,若在交配(变异)概率之内则交换(变异)
		for (int j = 0; j < population; j++) {
			for (int i = 0; i < population - 1; i++) {
				double temp = random2.nextDouble();
				if (temp < crossoverPossibility) {// 在交配概率之内
					crossover(clist.get(i), clist.get(i + 1));
				}
			}
			double mutation = random2.nextDouble();
			if (mutation < mutationPossibility) {// 在变异概率之内
				mutation(clist.get(j));
			}
		}
		// 重新计算群体适应值
		evaluate();
		// 重新选择种群
		selectPopulation();
		generation++;		//群体计算器
	}

	// 随机变异一位
	private void mutation(Chromosome ch) {
		int location = random1.nextInteger();
		char c = ch.getChromo()[location];
		if (c == '1') {
			ch.getChromo()[location] = '0';
		} else {
			ch.getChromo()[location] = '1';
		}
	}

	public void printDetail() {
		System.out.println("/*****************************************");
		System.out.println("*           -----遗传算法报告-----                 ");
		System.out.println("*  当前是第" + generation + "代");
		System.out.println("*  当前种群平均适应值为: " + detail.getAverageFitness());
		System.out.println("*  其中最大适应值为:" + detail.getMaxFitness());
		System.out.println("*  其中最小适应值为:" + detail.getMinFitness());
		System.out.println("*******************************************/");
	}

	public static void main(String[] args) {
		// 种群数量：30
		// 选择概率：0.0
		// 交配概率：0.9
		// 变异概率: 0.01
		GeneticAlgorithm ga = new GeneticAlgorithm(30, 0.0, 0.9, 0.01, 24);
		for (int i = 0; i < 5; i++) {
			ga.crossoverPopulation();//在后代中选择新种群
		}
		ga.printDetail();
		for (int j = 0; j < 25; j++) {
			ga.crossoverPopulation();
		}
		ga.printDetail();
		for (int k = 0; k < 1000; k++) {
			ga.crossoverPopulation();
		}
		ga.printDetail();
	}

}
