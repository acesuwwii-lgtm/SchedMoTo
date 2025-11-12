package com.oop.naingue.demo5.repositories;

import com.oop.naingue.demo5.models.Rooms;
import com.oop.naingue.demo5.repositories.RoomsRepository;

public class InsertRoomTest {
    public static void main(String[] args) {
        Rooms room = new Rooms();
        room.setRoomId(101);           // primary key
        room.setRoomNumber(101);       // room number
        room.setRoomType("Deluxe");    // type
        room.setRoomPrice(2500.0);     // price
        room.setStatus("Available");   // status
        room.setRoomCapacity(2);       // capacity

        RoomsRepository repo = new RoomsRepository();
        repo.insert(room);

        System.out.println("Room inserted successfully!");
    }

//    kung ma merge na ni ninyo sa inyo, egamit ra ni tapos e run para maka insert mo
//    e merge sa inyong branches
//    makita na sa inyong database
//    ok na ni, ayaw na prompt balik, kani na gamita, pag update na diya ug merge sa imong branch para magamit
//    3 min break muna ko
}
