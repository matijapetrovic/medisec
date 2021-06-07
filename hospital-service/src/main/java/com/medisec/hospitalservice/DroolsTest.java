package com.medisec.hospitalservice;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsTest {

    public static final void main(String[] args) {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
            Booking booking = new Booking();
            booking.setDiscount(true);
            booking.setPrice(50);
            System.out.println(kSession);
            System.out.println("Initial pricme: " + booking.getPrice());
            kSession.insert(booking);
            kSession.fireAllRules();
            System.out.println("New price: " + booking.getPrice());
    }

    public static class Booking {

        boolean discount;
        int price;

        public boolean applyDiscount() {
            return discount;
        }

        public void setDiscount(boolean discount) {
            this.discount = discount;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
    }
}
