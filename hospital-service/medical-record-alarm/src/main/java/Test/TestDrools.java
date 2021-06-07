package Test;


import com.medisec.hospitalservice.medical_record.MedicalRecord;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestDrools {
    public static final void main(String[] args) {
        MedicalRecord medicalRecord = new MedicalRecord();
        // load up the knowledge base
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession("ksession-rules");

        // go !
        Booking booking = new Booking();
        booking.setDiscount(true);
        booking.setPrice(50);
        System.out.println(kSession);
        System.out.println("Initial price: " + booking.getPrice());
        kSession.insert(booking);
        kSession.fireAllRules();
        System.out.println("New price: " + booking.getPrice());
    }
}
