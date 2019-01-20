package visualmemory;

import java.awt.BorderLayout;
import java.awt.Panel;
import org.json.simple.JSONObject;

/**
 *
 * @author bykov_s_p
 */
public class StartScreen extends VisualGame{
    
    public StartScreen(MainFrame frame){
        this.frame = frame;
        drawMenuButtons();
    }
    
    private void drawMenuButtons(){
        
    }

    @Override
    JSONObject prepareJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void clearGame() {
        
    }

    @Override
    void drawContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
