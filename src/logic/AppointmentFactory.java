/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Ilia Consuegra
 */
public class AppointmentFactory {
    public static AppointmentInterface createAppointmentInterface() throws OperationNotSupportedException {
        AppointmentInterface appointmentInterface = null;
        appointmentInterface = new AppointmentManager();
        return appointmentInterface;
        
    }
}
