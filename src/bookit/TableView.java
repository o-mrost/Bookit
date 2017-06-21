package bookit;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.showcase.domain.Car;
import org.primefaces.showcase.service.CarService;
 
@ManagedBean(name="tvb")
@ViewScoped
public class TableView implements Serializable {
     
    private List<Termine> termine;
     
    @ManagedProperty("#{service}")
    private Service service;
 
    @PostConstruct
    public void init() {
        setTermine(service.createCars(10));
    }
     
    public List<Car> getCars() {
        return cars;
    }
 
    public void setService(Service service) {
        this.service = service;
    }

	
}