/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UAV.ACO;


public class ACO {

    private CityGraph weight_distance;
    private int citynum; //城市数量
    private int p = 1000;//迭代次数
    private double bestLength;
    private String bestTour;
    private int antNum = 100;
    private int bestAnt = Integer.MAX_VALUE;
    private ANT[] ants;
    private double alpha = 1.0;
    private double beta = 5.0;
    private double rho = 0.5;
    private double Q = 1000;
    private long startTime;
    private long endTime;

    //初始化城市信息，假设为非对称TSP问题
    public void Init_Distance(int num, double[][] distance) {
    	this.setCitynum(num);
        weight_distance = new CityGraph(citynum, distance);
    }

    public int getCitynum() {
		return citynum;
	}

	public void setCitynum(int citynum) {
		this.citynum = citynum;
	}

	//参数初始化
    private void Init_paras() {
        bestLength = Double.MAX_VALUE;
        bestTour = "";
    }

    /**
     * P<最大迭代次数
     */
    private void Init_Ants() { //每次循环的每只蚂蚁都是新蚂蚁，没有残留信息
        ants = null;
        ants = new ANT[antNum];
        for (int i = 0; i < antNum; i++) {
            ants[i] = new ANT(citynum, alpha, beta);
        }
    }

    //对每只蚂蚁按概率选择移动到下一个节点，直至遍历全部节点
    //当一只蚂蚁结束遍历时，应该更新全局信息素矩阵
    //计算每只蚂蚁的路径长度，
    private void MovetoNextCity() {
        for (int i = 0; i < antNum; i++) {
            ants[i].chooseNextCity();
//            ants[i].updatePheromone();//信息素更新在一次迭代结束
//            ants[i].calRoad();
        }
    }

    //记录当前最好解
    private void findBestRoad() {

        for (int i = 0; i < antNum; i++) {

            if (bestLength > ants[i].getRoadLength()) {
                bestLength = ants[i].getRoadLength();
                bestTour = ants[i].getRoad();
                bestAnt = i;
            }
        }
//        System.out.println("当前最优解是：" + bestTour);
//        System.out.println("该路径下的最低消耗：" + bestLength);
    }
    //按更新方程修改轨迹长度

    private void updatePheromone() {
        for (int i = 0; i < antNum; i++) {
            ants[i].updatePheromone(Q, (endTime - startTime) / 1000);
        }

    }

    /**
     * 迭代结束
     */
    public void iterator() {
       // Init_Distance(); 初始化距离在外面单独执行
        Init_paras();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {//一次迭代即更新了一次解空间
          //  System.out.println("第" + i + "次迭代：");
            Init_Ants();
            MovetoNextCity();
            findBestRoad();
            endTime = System.currentTimeMillis();
            updatePheromone();
        }
    }
    public String getBestTour() {
		return bestTour;
	}

	public void setBestTour(String bestTour) {
		this.bestTour = bestTour;
	}

	

    public static void main(String[] args) {
        ACO aco = new ACO();
        aco.iterator();
    }
}
