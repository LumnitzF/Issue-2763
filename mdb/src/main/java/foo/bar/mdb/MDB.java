/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foo.bar.mdb;

import foo.bar.remote.ejb.LogToInfo;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Lumnitzf
 */
@MessageDriven(mappedName = "jms/LogToInfoTopic", name = "LogToInfoMDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "maxSessions", propertyValue = "1"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class MDB implements MessageListener {

    private static final Logger LOG = Logger.getLogger(MDB.class.getCanonicalName());

    @EJB(name = "LogToInfoBean", mappedName = "LogToInfoBean")
    private LogToInfo logToInfo;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                logToInfo.log(textMessage.getText());
            } else {
                LOG.warning("Message was not a TextMessage");
            }
        } catch (JMSException e) {
            LOG.severe(e.toString());
        }
    }

}
