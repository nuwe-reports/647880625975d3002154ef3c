package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.example.demo.repositories.*;
import org.junit.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;


    @BeforeEach
    void setUp() {
        d1 = new Doctor("Jordi", "Coso", 40, "jordi.coso@gmail.com");
        p1 = new Patient("Marta", "Porcar", 25, "marta.porcar@gmail.com");
        r1 = new Room("Room_1");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt = LocalDateTime.parse("19:30 24/04/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a2 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a3 = new Appointment(p1, d1, r1, finishesAt.plusHours(2), finishesAt.plusHours(2));
    }

    @Test
    void shouldSaveDoctor() {
        Doctor doctor = entityManager.persistAndFlush(d1);

        assertThat(doctor.getId()).isNotNull();
        assertThat(doctor.getFirstName()).isEqualTo("Jordi");
        assertThat(doctor.getLastName()).isEqualTo("Coso");
        assertThat(doctor.getAge()).isEqualTo(40);
        assertThat(doctor.getEmail()).isEqualTo("jordi.coso@gmail.com");
    }

    @Test
    void shouldSavePatient() {
        Patient patient = entityManager.persistAndFlush(p1);

        assertThat(patient.getId()).isNotNull();
        assertThat(patient.getFirstName()).isEqualTo("Marta");
        assertThat(patient.getLastName()).isEqualTo("Porcar");
        assertThat(patient.getAge()).isEqualTo(25);
        assertThat(patient.getEmail()).isEqualTo("marta.porcar@gmail.com");

    }

    @Test
    void shouldSaveRoom() {
        Room room = entityManager.persistAndFlush(r1);
        assertThat(room).isNotNull();
        assertThat(room.getRoomName()).isEqualTo("Room_1");
    }

    @Test
    void shouldSaveAppointment() {
        Appointment appointment = this.entityManager.persistAndFlush(a1);

        assertThat(appointment.getId()).isNotNull();

    }

    @Test
    void testOverlappingAppointments() {
        Appointment appointment1 = this.entityManager.persistAndFlush(a1);
        Appointment appointment2 = this.entityManager.persistAndFlush(a2);


        boolean overlaps = appointment1.overlaps(appointment2);
        assertThat(overlaps).isTrue();
    }

    @Test
    void testNonOverlappingAppointments() {
        Appointment appointment1 = this.entityManager.persistAndFlush(a1);
        Appointment appointment3 = this.entityManager.persistAndFlush(a3);

        boolean overlaps = appointment1.overlaps(appointment3);
        assertThat(overlaps).isFalse();
    }


    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */
}
