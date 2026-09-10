package com.casamento.wedding.rsvp;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "rsvp_entry")
public class RsvpEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String guests;
    private String answer;

    @Column(length = 2000)
    private String message;

    private Instant createdAt = Instant.now();

    public RsvpEntry() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGuests() { return guests; }
    public void setGuests(String guests) { this.guests = guests; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
