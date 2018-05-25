/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foo.bar.webapp.rest;

import javax.ejb.EJB;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import foo.bar.webapp.JMSSender;

/**
 *
 * @author Lumnitzf
 */
@Path("/jms")
public class JMSWebResource {

    @EJB
    private JMSSender sender;

    @GET
	public void get(@QueryParam("amount") @DefaultValue("10") int amount) {
		sender.sendMessage(amount);
    }
}
