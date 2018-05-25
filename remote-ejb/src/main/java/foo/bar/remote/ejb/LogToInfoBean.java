/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foo.bar.remote.ejb;

import java.util.logging.Logger;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author Lumnitzf
 */
@Stateless(name = "LogToInfoBean", mappedName = "LogToInfoBean")
@Remote(LogToInfo.class)
public class LogToInfoBean implements LogToInfo {
    
    @Override
    public void log(String string) {
        Logger.getLogger(getClass().getCanonicalName()).info(string);
    }
}
