package com.ou.oubusmanager;
        
import com.ou.pojo.Bus;
import com.ou.pojo.Customer;
import com.ou.pojo.Employee;
import com.ou.pojo.Ticket;
import com.ou.pojo.Trip;
import com.ou.services.BusService;
import com.ou.services.TicketService;
import com.ou.services.TripService;
import com.ou.services.UserService;
import com.ou.utils.DateTimeCalc;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
        
/**
 *
 * @author CÔNG SANG
 */
public class TicketFormController implements Initializable {
    @FXML
    private Button btnPrint;

    @FXML
    private Label lblBus;

    @FXML
    private Label lblCusName;

    @FXML
    private Label lblDatePrint;

    @FXML
    private Label lblDepartureTime;

    @FXML
    private Label lblEmName;

    @FXML
    private Label lblFrom;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblSeat;
    
     @FXML
    private Label lblDepartureDate;

    @FXML
    private Label lblTicketId;

    @FXML
    private Label lblTo;
    private Customer customer;
    private Employee employee;
    private Ticket ticket;
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setTicket(Ticket ticket) throws SQLException {
        this.ticket = ticket;
        setTicketPrint();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setTicketPrint() throws SQLException {
        Trip trip = TripService.getTripById(this.ticket.getTripId());
        Bus bus = BusService.getBusById(trip.getBusId());
        this.customer = (Customer) UserService.getCustomerById(ticket.getCustomerId());
        this.employee = (Employee) UserService.getEmployeeById(ticket.getEmployeeId());
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        
        lblFrom.setText(trip.getFrom());
        lblTo.setText(trip.getTo());
        lblTicketId.setText(String.format("%d", this.ticket.getId()));
        lblDepartureTime.setText(trip.getTime());
        lblDepartureDate.setText(trip.getDate());
        lblSeat.setText(String.format("%d", this.ticket.getSeatId()));
        lblCusName.setText(customer.getName());
        lblPhone.setText(customer.getPhone());
        lblBus.setText("Lên xe: " + bus.getBusSerial());
        lblEmName.setText(employee.getName());
        lblDatePrint.setText(dtf.format(now));
    }
    
}
