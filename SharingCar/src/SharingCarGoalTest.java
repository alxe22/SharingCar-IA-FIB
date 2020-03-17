import aima.search.framework.GoalTest;

public class SharingCarGoalTest implements GoalTest {

    public boolean isGoalState(Object state){

        return((SharingCarBoard) state).is_goal();
    }
    
    
     /* Goal test */
    
}