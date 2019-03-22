package lr_AgLinks;

// Добавить ограничение не больше 6 агентов для связи, чтобы не закольцевалось
// Поменять Run Configuration на стандартные jade.Boot и т.д

import java.util.ArrayList;

public class Snippet {
    public static void main(String[] args) {
        ArrayList<Link> links = new ArrayList<Link>();
        links.add(new Link("Agent2", 10));
        links.add(new Link("Agent3", 25));
        Setting s = new Setting();
        s.setLinks(links);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s, "Agent1.xml");


        ArrayList<Link> links2 = new ArrayList<Link>();
        links2.add(new Link("Agent1", 10));
        links2.add(new Link("Agent4", 20));
        Setting s2 = new Setting();
        s2.setLinks(links2);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s2, "Agent2.xml");


        ArrayList<Link> links3 = new ArrayList<Link>();
        links3.add(new Link("Agent1", 25));
        links3.add(new Link("Agent7", 5));
        links3.add(new Link("Agent8", 15));
//        links3.add(new Link("Agent8", 15));
        Setting s3 = new Setting();
        s3.setLinks(links3);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s3, "Agent3.xml");

        ArrayList<Link> links4 = new ArrayList<Link>();
        links4.add(new Link("Agent2", 20));
        links4.add(new Link("Agent5", 5));
        links4.add(new Link("Agent7", 10));
        Setting s4 = new Setting();
        s4.setLinks(links4);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s4, "Agent4.xml");

        ArrayList<Link> links5 = new ArrayList<Link>();
        links5.add(new Link("Agent4", 5));
        links5.add(new Link("Agent6", 5));
        Setting s5 = new Setting();
        s5.setLinks(links5);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s5, "Agent5.xml");

        ArrayList<Link> links6 = new ArrayList<Link>();
        links6.add(new Link("Agent5", 5));
        links6.add(new Link("Agent7", 15));
        Setting s6 = new Setting();
        s6.setLinks(links6);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s6, "Agent6.xml");

        ArrayList<Link> links7 = new ArrayList<Link>();
        links7.add(new Link("Agent3", 5));
        links7.add(new Link("Agent4", 10));
        links7.add(new Link("Agent6", 15));
        links7.add(new Link("Agent10", 10));
        Setting s7 = new Setting();
        s7.setLinks(links7);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s7, "Agent7.xml");

        ArrayList<Link> links8 = new ArrayList<Link>();
        links8.add(new Link("Agent3", 15));
        links8.add(new Link("Agent10", 20));
        Setting s8 = new Setting();
        s8.setLinks(links8);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s8, "Agent8.xml");

        ArrayList<Link> links10 = new ArrayList<Link>();
        links10.add(new Link("Agent7", 10));
        links10.add(new Link("Agent8", 20));
        Setting s10 = new Setting();
        s10.setLinks(links10);
        // создание XML
        WorkWithConfigGFiles.marshalAny(Setting.class, s10, "Agent10.xml");
    }
}