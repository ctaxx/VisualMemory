/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package visualmemory;

import java.awt.Color;

/**
 *
 * @author Bykov_SP
 */
public abstract class VisualGame {
    void outputResult(){};
    
    void createTaskArr(){};
    
    void setAppState(int state){};
    
    void setCurrentColour(Color color){};
}
