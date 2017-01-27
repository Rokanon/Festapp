package com.musicfestivals.festival;

import com.musicfestivals.artist.Artist;
import com.musicfestivals.query.DataQuery;
import com.musicfestivals.social.SocialNetworks;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "festivalUpload")
@ViewScoped
public class FestivalUpload implements Serializable {

    private static final long serialVersionUID = 1L;
    private final DataQuery query = new DataQuery();
    private Festival festival;
    private Artist artist;
    private SocialNetworks social;
    private boolean test = false;
    
    @PostConstruct
    public void init() {
        festival = new Festival();
        artist = new Artist();
        social = new SocialNetworks();
    }

    public void fileUpload(FileUploadEvent event) throws IOException {
        OutputStream outputStream = null;
        try {
            InputStream is = event.getFile().getInputstream();
            File file = new File("out.tmp");
            outputStream = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            is.close();
            outputStream.close();
            JSONParser parser = new JSONParser();

            Object obj = parser.parse(new FileReader(file));

            JSONObject jo = (JSONObject) obj;
            JSONObject jsonObject = (JSONObject) jo.get("Festival");
            long t = 0;
            int m = 0;
            festival.setTitle((String) jsonObject.get("Name"));
            festival.setBeginDate(convertStringToDate((String) jsonObject.get("StartDate")));
            festival.setEndDate(convertStringToDate((String) jsonObject.get("EndDate")));
            festival.setPlace((String) jsonObject.get("Place"));
            festival.setGenre("Genre");
            festival.setTimesSeen(t);
            festival.setTicketsSold(t);
            festival.setMaxTicketsPerUser(m);
            festival.setMaxTicketsPerUserPerDay(m);
            festival.setRating(t);
            festival.setUsersRated(m);
            festival.setInfo("There is no info for this festival");
            festival.setVerified(false);

            JSONArray tickets = (JSONArray) jsonObject.get("Tickets");
            int dayTicket = Integer.parseInt((String) tickets.get(0));
            int wholeTicket = Integer.parseInt((String) tickets.get(0));

            festival.setPriceOneDay(dayTicket);
            festival.setPriceWholeFestival(wholeTicket);

            query.getEntityManager().persist(getFestival());
            query.getEntityManager().getTransaction().commit();

            JSONArray artists = (JSONArray) jsonObject.get("PerformersList");
            for (int i = 0; i < artists.size(); i++) {
                transactionCheck();
                artist = new Artist();
                JSONObject a = (JSONObject) artists.get(i);
                artist.setArtistName((String) a.get("Performer"));
                artist.setFestivalId(festival.getId());
                artist.setPerformanceDate(convertStringToDate((String) a.get("StartDate")));
                artist.setPerformanceEndDate(convertStringToDate((String) a.get("EndDate")));
                artist.setPerformanceTimeStart(convertStringToTime((String) a.get("StartTime")));
                artist.setPerformanceTimeEnd(convertStringToTime((String) a.get("EndTime")));
                query.getEntityManager().persist(getArtist());
                query.getEntityManager().getTransaction().commit();
            }

            JSONArray socialNetworks = (JSONArray) jsonObject.get("SocialNetworks");
            
            JSONObject a = (JSONObject) socialNetworks.get(0);
            JSONObject b = (JSONObject) socialNetworks.get(1);
            JSONObject c = (JSONObject) socialNetworks.get(2);
            
            transactionCheck();
            
            social.setFestivalId(festival.getId());
            social.setFacebook((String) a.get("Link"));
            social.setTwitter((String) b.get("Link"));
            social.setYoutube((String) c.get("Link"));

            query.getEntityManager().persist(getSocial());
            query.getEntityManager().getTransaction().commit();
            setTest(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transactionCheck() {
        if (!query.getEntityManager().getTransaction().isActive()) {
            query.getEntityManager().getTransaction().begin();
        }
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Date convertStringToDate(String s) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        try {
            startDate = df.parse(s);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    private Time convertStringToTime(String s) {
        Time time = null;
        time = Time.valueOf(s.substring(0, 8));
        if (s.substring(s.length() - 2, s.length()).equals("PM")) {
            time.setHours(time.getHours() + 12);
        }
        return time;
    }

    public SocialNetworks getSocial() {
        return social;
    }

    public void setSocial(SocialNetworks social) {
        this.social = social;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }
}
