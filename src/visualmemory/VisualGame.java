/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visualmemory;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

/**
 *
 * @author Bykov_SP
 */
public abstract class VisualGame implements MouseListener, MouseMotionListener{
    
    protected final int INITIAL_STATE = 0;
    protected final int SHOW_STATE = 1;
    protected final int GETTIN_RESULT_STATE = 2;
    protected final int SHOWIN_RESULT_STATE = 3;
    
    abstract JSONObject prepareJSON(); 
    
    protected void outputResult(JSONObject resultJSONObject) {
        JSONStreamAware jSONStreamAware;
        
        jSONStreamAware = resultJSONObject;

        try(Writer sout = new FileWriter("d:/VMresult.txt", true)) {
            
            jSONStreamAware.writeJSONString(sout);
            sout.write('\n');
            sout.flush();
            sout.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    void createTaskArr(){};
    
    void setAppState(int state){};
}
