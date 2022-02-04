/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.naming.OperationNotSupportedException;

/**
 * This factory returns an instance of the Appointment interface and we use it to separate layers.
 * @author Ilia Consuegra
 */
public class AppointmentFactory {
    /**
     * 
     * @return AppointmentInterface
     * @throws OperationNotSupportedException 
     */
    public static AppointmentInterface createAppointmentInterface() throws OperationNotSupportedException {
        //AppointmentInterface is initialized
        AppointmentInterface appointmentInterface = null;
        //appoinmentInterface is implemented as AppointmentManager
        appointmentInterface = new AppointmentManager();
        return appointmentInterface;
        
    }
}
