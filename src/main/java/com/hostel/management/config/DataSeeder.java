package com.hostel.management.config;

import com.hostel.management.entity.*;
import com.hostel.management.entity.enums.RoleType;
import com.hostel.management.repository.*;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final HostelRepository hostelRepository;
    private final FloorRepository floorRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        UserAccount admin = new UserAccount();
        admin.setFullName("Admin User");
        admin.setEmail("admin@hostel.local");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setRole(RoleType.ADMIN);
        userRepository.save(admin);

        UserAccount studentUser = new UserAccount();
        studentUser.setFullName("Rahul Student");
        studentUser.setEmail("student@hostel.local");
        studentUser.setPassword(passwordEncoder.encode("Student@123"));
        studentUser.setRole(RoleType.STUDENT);
        userRepository.save(studentUser);

        Student student = new Student();
        student.setUser(studentUser);
        student.setPhone("9999999999");
        student.setGuardianName("Mr. Kumar");
        student.setGuardianPhone("8888888888");
        student.setEnrollmentNumber("ENR-1001");
        studentRepository.save(student);

        Hostel hostel = new Hostel();
        hostel.setName("Boys Hostel A");
        hostel.setAddress("North Campus");
        hostelRepository.save(hostel);

        Floor floor = new Floor();
        floor.setHostel(hostel);
        floor.setFloorNumber(1);
        floorRepository.save(floor);

        Room room = new Room();
        room.setFloor(floor);
        room.setRoomNumber("101");
        room.setCapacity(3);
        room.setMonthlyRent(new BigDecimal("3500"));
        roomRepository.save(room);

        for (int i = 1; i <= 3; i++) {
            Bed bed = new Bed();
            bed.setRoom(room);
            bed.setBedNumber("B" + i);
            bed.setOccupied(false);
            bedRepository.save(bed);
        }
    }
}
