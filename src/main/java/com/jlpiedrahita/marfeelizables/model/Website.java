package com.jlpiedrahita.marfeelizables.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents an Internet website that has been visited by the <i>Marfeelables Bot</i>
 */
@Entity
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String URL;

    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss z")
    private Date visitDate;
    private boolean marfeelizable;

    public enum VisitStatus {
        SUCCESS,
        ERROR;
    }

    public Website() {
    }

    public Website(String URL, boolean marfeelizable, Date visitDate, VisitStatus visitStatus) {
        this(null, URL, marfeelizable, visitDate, visitStatus);
    }

    public Website(Long id, String URL, boolean marfeelizable, Date visitDate, VisitStatus visitStatus) {
        this.id = id;
        this.URL = URL;
        this.visitStatus = visitStatus;
        this.visitDate = visitDate;
        this.marfeelizable = marfeelizable;
    }

    /**
     * @return this Website's internal ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Bot visit status
     * <p>
     * Several things can go wrong when the bot is trying to visit each website: sites
     * can be down, hosts can be unknown, timeouts, etc. The status tells whether the
     * bot visit was successful or not.
     *
     * @return the visit status
     * @see VisitStatus
     */
    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    /**
     * Date of the Marfeelizables bot visit to the website
     *
     * @return bot visit date to this website
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * @return The website URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * Whether this website is marfeelizable or not.
     * <p>
     * This can change over time as websites are updated or modified.
     *
     * @return Whether this website is marfeelizable or not
     */
    public boolean isMarfeelizable() {
        return marfeelizable;
    }

    @Override
    public int hashCode() {
        return this.id == null ? super.hashCode() : this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == null ? super.equals(obj) : this.id.equals(obj);
    }

}
