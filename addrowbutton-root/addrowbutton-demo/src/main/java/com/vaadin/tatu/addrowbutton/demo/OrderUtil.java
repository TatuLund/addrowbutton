package com.vaadin.tatu.addrowbutton.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.tatu.addrowbutton.demo.Order.Priority;

public class OrderUtil {
    public static BeanItemContainer<Order> createOrderContainer() {
        BeanItemContainer<Order> container = new BeanItemContainer<Order>(
                Order.class);

        Random random = new Random(4837291937l);
        for (int id = 0; id < 1000; id++) {
            Order order = generateOrder(random);
            container.addBean(order);
        }

        return container;
    }

    // http://online-generator.com/name-generator/product-name-generator.php
    private static String[] productNames = new String[] { "Tamplam",
            "Tres Phase", "Santindex", "Biging", "Freshflex", "Statdex" };

    // http://online-generator.com/name-generator/business-name-generator.php
    private static String[] customerNames = new String[] { "Apcane", "Nimlex",
            "Rancone", "Geolab", "Aceelectrics", "Sandrill", "Haydox",
            "Vaialax", "Blackphase", "Stan-city", "o-fax", "O-lane", "matfan",
            "Haycorporation", "Dingsuntax", "goldenfase", "kay-lane", "Duolam",
            "Howis", "tresron", "Runhex", "Tanlane", "Basecane",
            "toughelectrics", "Overtech", "Trueunafan", "Trisquote",
            "Whitetechnology", "E-hatfax", "Ganjamedia", "Carecane",
            "Lattaxon", "Stattamtom", "Tranfax", "Zimdomtech", "zunron",
            "Rankplex", "Latacode", "Sunkix", "Ontodom", "Voltanex", "Latdex",
            "U-sunace", "Labvolplus", "vilaware", "Fincare", "Zun-zone",
            "Runtamtam", "Siltouch", "Damtinlab", "Carephase", "Alphadrill",
            "Tranmedia", "Sanelectronics", "X-ron", "Biodom", "Stimdexon",
            "Siliconcom", "Pluslane", "blacktax", "Daltdrill", "Keygreen",
            "Jayace", "dondax", "Newdax", "Tonice", "salt-dom", "Plextriptax",
            "Stimhow", "Indigobam", "Alphalane", "Yearlane", "Nimdexon",
            "Singletech", "Uniplanet", "Icecantom", "Zimity", "Stimzap",
            "Quoteline", "zim-techno", "temptech", "Opetrans", "Kaystrip",
            "Siliconice", "Zathphase", "Med-tam", "Vila-ing", "Bluemedia",
            "Trancity", "Iceline", "Graveit", "Basejob", "Warezatkix",
            "Apmedtex", "Donghotlam", "Sonzamcane", "Randining", "Acetam",
            "Zoombase", "Zamtech", "Namis", "Whitedom", "Sailbiola", "Dontax",
            "Basecom", "Indigola", "quadhex", "Solotaxon", "Hotcane", "Damdox", };

    private static String pickRandom(String[] values, Random random) {
        return values[random.nextInt(values.length)];
    }

    private static Order generateOrder(Random random) {
        Order order = new Order();

        order.setCustomer(pickRandom(customerNames, random));
        order.setCustomized(random.nextDouble() > 0.75);
        order.setOrderAmount(100 + random.nextInt(2000 - 100 / 5) * 5);
        order.setOrderTime(getRandomDate(random));
        order.setPriority(getRandomPriority(random));
        order.setProduct(pickRandom(productNames, random));
        order.setReservedAmount(Math.round(order.getOrderAmount()
                * random.nextFloat()));

        return order;
    }

    private static Priority getRandomPriority(Random random) {
        double value = random.nextDouble();
        if (value < 0.1) {
            return Priority.LOW;
        } else if (value < 0.8) {
            return Priority.NORMAL;
        } else {
            return Priority.HIGH;
        }
    }

    private static Date getRandomDate(Random random) {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -random.nextInt(45));
        cal.set(Calendar.HOUR_OF_DAY, 8 + random.nextInt(8));

        for (int field : new int[] { Calendar.MINUTE, Calendar.SECOND,
                Calendar.MILLISECOND }) {
            cal.set(field, random.nextInt(cal.getMaximum(field)));
        }

        return cal.getTime();
    }

}
