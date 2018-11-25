package developGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


public class TimerGame implements ActionListener {
    Timer t = new Timer(3000,this);
    
    public void StartTimer(){
        t.start();
    }
    
    public static void main(String[] args){
        TimerGame T = new TimerGame(); 
//        System.out.println("nguyen duy chien");
        T.StartTimer();
//        System.out.println("nguyen duy chien 2");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
