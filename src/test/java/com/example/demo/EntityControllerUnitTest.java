
package com.example.demo;

import static org.assertj.core.api.Assertions.setMaxElementsForPrinting;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import com.fasterxml.jackson.core.*;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor("Juan", "Lopez", 40, "jl@gmail.com");
        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor))).andExpect(status().isCreated());
    }



    @Test
    void shouldGetDoctorByIdReturn404NotFoundDoctor() throws Exception{
        long id = 1L;
        when(doctorRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/doctors/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void shouldGetDoctorById() throws Exception{
        Doctor doctor = new Doctor("Juan", "Lopez", 40, "jl@gmail.com");
        long id = 1;
        doctor.setId(id);
        when(doctorRepository.findById(id)).thenReturn(Optional.of((doctor)));
        mockMvc.perform(get("/api/doctors/{id}", id)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(doctor.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctor.getLastName()))
                .andExpect(jsonPath("$.age").value(doctor.getAge()))
                .andExpect(jsonPath("$.email").value(doctor.getEmail()));
    }
    @Test
    void shouldReturnAllDoctors() throws Exception{
        List <Doctor> doctors = new ArrayList<>(
                Arrays.asList(new Doctor("Juan", "Lopez", 40, "jl@gmail.com"),
                        new Doctor("Juan1", "Lopez1", 40, "jl1@gmail.com"),
                        new Doctor("Juan2", "Lopez2", 40, "jl2@gmail.com")));
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(doctors.size()));
    }

    @Test
    void shouldGetAllDoctorsReturn204NoContent() throws Exception{
        List <Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());

    }
    @Test
    void shouldDeleteDoctorById() throws Exception{
        Doctor doctor = new Doctor("Juan", "Lopez", 40, "jl@gmail.com");
        long id = 1;
        doctor.setId(id);
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        doNothing().when(doctorRepository).deleteById(id);
        mockMvc.perform(delete("/api/doctors/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception{
        doNothing().when(doctorRepository).deleteAll();
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }
    @Test
    void shouldDeleteDoctorByIdReturn404NotFound() throws Exception{
        long id = 1L;

        when(doctorRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/doctors/{id}", id)).andExpect(status().isNotFound());

    }




}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient("Juan", "Lopez", 40, "jl@gmail.com");
        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient))).andExpect(status().isCreated());
    }



    @Test
    void shouldGetPatientByIdReturn404NotFoundDoctor() throws Exception{
        long id = 1L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patient/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    void shouldGetPatientById() throws Exception{
        Patient patient = new Patient("Juan", "Lopez", 40, "jl@gmail.com");
        long id = 1;
        patient.setId(id);
        when(patientRepository.findById(id)).thenReturn(Optional.of((patient)));
        mockMvc.perform(get("/api/patients/{id}", id)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(jsonPath("$.age").value(patient.getAge()))
                .andExpect(jsonPath("$.email").value(patient.getEmail()));
    }
    @Test
    void shouldReturnAllPatients() throws Exception{
        List <Patient> patients = new ArrayList<>(
                Arrays.asList(new Patient("Juan", "Lopez", 40, "jl@gmail.com"),
                        new Patient("Juan1", "Lopez1", 40, "jl1@gmail.com"),
                        new Patient("Juan2", "Lopez2", 40, "jl2@gmail.com")));
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(patients.size()));
    }

    @Test
    void shouldGetAllPatientReturn204NoContent() throws Exception{
        List <Patient> patients = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());

    }
    @Test
    void shouldDeletePatientById() throws Exception{
        Patient patient = new Patient("Juan", "Lopez", 40, "jl@gmail.com");
        long id = 1;
        patient.setId(id);
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        doNothing().when(patientRepository).deleteById(id);
        mockMvc.perform(delete("/api/patients/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        doNothing().when(patientRepository).deleteAll();
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }
    @Test
    void shouldDeletePatientByIdReturn404NotFound() throws Exception{
        long id = 1L;

        when(patientRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/patients/{id}", id)).andExpect(status().isNotFound());

    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room("Emergency unit");
        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room))).andExpect(status().isCreated());
    }


    @Test
    void shouldGetRoomByRoomNameReturn404NotFoundDoctor() throws Exception{
        String roomName = "Emergency unit";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/room/{roomName}", roomName)).andExpect(status().isNotFound());
    }

    @Test
    void shouldGetRoomByRoomName() throws Exception{
        Room room = new Room("Emergency unit");
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of((room)));
        mockMvc.perform(get("/api/rooms/{roomName}", room.getRoomName())).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(room.getRoomName()));

    }
    @Test
    void shouldReturnAllRooms() throws Exception{
        List <Room> rooms = new ArrayList<>(
                Arrays.asList(new Room("Emergency unit"),
                        new Room("Critical care unit"),
                        new Room("Delivery room")));
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(rooms.size()));
    }

    @Test
    void shouldGetAllRoomsReturn204NoContent() throws Exception{
        List <Room> rooms = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());

    }
    @Test
    void shouldDeleteRoomByRoomName() throws Exception{
        Room room = new Room("Emergency unit");
        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(Optional.of(room));
        doNothing().when(roomRepository).deleteByRoomName(room.getRoomName());
        mockMvc.perform(delete("/api/rooms/{roomName}", room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception{
        doNothing().when(roomRepository).deleteAll();
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }
    @Test
    void shouldDeleteRoomByRoomNameReturn404NotFound() throws Exception{
       String roomName = "Emergency unit";
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/rooms/{roomName}", roomName)).andExpect(status().isNotFound());

    }

}
