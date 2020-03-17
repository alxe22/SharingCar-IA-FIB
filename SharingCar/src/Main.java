
import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {

   public static void main(String[] args) throws Exception{
         
       int a, b, c, d;
       
       Scanner in = new Scanner(System.in);
     
        System.out.println("Introduce NUMERO DE USUARIOS");
        a = in.nextInt();
       
        System.out.println("Introduce NUMERO DE CONDUCTORES ( < Numero de usuarios)");
        b = in.nextInt();
        
        System.out.println("Introduce SEMILLA");
        c = in.nextInt();
        
        System.out.println("Introduce TIPO DE SOLUCION INICIAL  (0 o 1)");
        d = in.nextInt();
        
        System.out.println("Introduce ALGORITMO: 0->HILL CLIMBING, 1->SIMULATED ANNEALING  (0 o 1)");
        int id_alg = in.nextInt();
        
        SharingCarBoard board = new SharingCarBoard(a, b, c, d );
        
        for (int i = 0; i < board.getDists().size(); ++i){
                    int minDist_i = board.getDists().get(i).dist;
                    double distMin = (double) minDist_i;
                    distMin /= 10;
                    System.out.print(distMin+" km");
                    System.out.print(distMin+" km ");
                    System.out.println(board.getDists().get(i).ruta);
         }
        
        Problem problem;
        SearchAgent agent;
        
        long init_time = 0;
        long end_time;
        if (id_alg == 0){
            try{
                problem = new  Problem(board,
                                     new SharingCarSuccessorFunction(),
                                     new SharingCarGoalTest(),
                                     new SharingCarHeuristicFunction1());
                Search search =  new HillClimbingSearch();
                
                init_time = System.currentTimeMillis();
                agent = new SearchAgent(problem,search);
                end_time = System.currentTimeMillis();
                System.out.println();
                
                SharingCarBoard scb_fin = (SharingCarBoard)search.getGoalState();
                
                for (int i = 0; i < scb_fin.getDists().size(); ++i){
                    int minDist_i = scb_fin.getDists().get(i).dist;
                    double distMin = (double) minDist_i;
                    distMin /= 10;
                    System.out.print(distMin+" km");
                    System.out.println(scb_fin.getDists().get(i).ruta);
                }
                System.out.println("elapsed time: "+(double)(end_time-init_time)/1000);
                
                printActions(agent.getActions());
                printInstrumentation(agent.getInstrumentation());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        
        if (id_alg == 1){
            try{
                System.out.println("Introduce MAX NUM ITERACIONES");
                int num_iters = in.nextInt();

                System.out.println("Introduce ITERACIONES/PASO DE TEMPERATURA");
                int iters_x_temp = in.nextInt();

                System.out.println("Introduce K");
                int k = in.nextInt();
                
                Scanner ind = new Scanner(System.in);
                System.out.println("Introduce LAMBDA");
                double lambda = ind.nextDouble();

                problem = new  Problem(board,
                                         new SharingCarSuccessorFunctionSA(),
                                         new SharingCarGoalTest(),
                                         new SharingCarHeuristicFunction1());

                Search search =  new SimulatedAnnealingSearch(num_iters,iters_x_temp,k,0.5);
                                
                init_time = System.currentTimeMillis();

                agent = new SearchAgent(problem,search);
                
                end_time = System.currentTimeMillis();
                
                System.out.println();
                
                SharingCarBoard scb_fin = (SharingCarBoard)search.getGoalState();
                
                for (int i = 0; i < scb_fin.getDists().size(); ++i){
                    int minDist_i = scb_fin.getDists().get(i).dist;
                    double distMin = (double) minDist_i;
                    distMin /= 10;
                    System.out.print(distMin+" km ");
                    System.out.println(scb_fin.getDists().get(i).ruta);
                }
                System.out.println("elapsed time: "+(double)(end_time-init_time)/1000);
                printActions(agent.getActions());
                printInstrumentation(agent.getInstrumentation());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        // You can access also to the goal state using the
	// method getGoalState of class Search

    }
   
   private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
   }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
    
}
    

