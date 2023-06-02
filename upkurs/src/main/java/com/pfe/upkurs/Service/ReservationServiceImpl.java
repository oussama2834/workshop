package com.pfe.upkurs.Service;
import com.pfe.upkurs.Entites.Reservation;
import com.pfe.upkurs.Entites.ValidateSessionRq;
import com.pfe.upkurs.Repository.ReservationRepository;
import com.pfe.upkurs.Repository.SessionCoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    SessionCourService sessionCoursService;
    @Autowired
    EtudiantService etudiantService;

    @Override
    public Reservation ajouterReservation(Reservation reservation) {

        reservation.setEtat("En attente");

        return reservationRepository.save(reservation);


    }

    @Override
    public Reservation modifierReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void supprimerReservation(Long id) {
        reservationRepository.deleteById(id);
    }


    @Override
    public ResponseEntity<?>  annulerReservation(Long id) {
        Optional<Reservation> r = reservationRepository.findById(id);
        if(r.isPresent()){
            Reservation res = r.get();
            res.setEtat("Réfusée");
            return new ResponseEntity(  reservationRepository.save(res) ,HttpStatus.OK);
        }
        return new ResponseEntity("not found" ,HttpStatus.NOT_FOUND);
    }
    @Override
    public ResponseEntity<?>  validerReservation(Long id) {
        Optional<Reservation> r = reservationRepository.findById(id);
        if(r.isPresent()){
            Reservation res = r.get();
            res.setEtat("Validée");
            return new ResponseEntity(  reservationRepository.save(res) ,HttpStatus.OK);
        }
        return new ResponseEntity("not found" ,HttpStatus.NOT_FOUND);
    }


    @Override
    public List<Reservation> listeReservation() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> listeSessionCoursByEtudient(Long id) {
        return null;
    }

    @Override
    public List<Reservation> listeSessionCoursByEnseignat(Long id) {
        return null;
    }


    @Transactional
    @Override
    public ResponseEntity<?> validateSession(ValidateSessionRq validateSessionRq) {
        Optional<Reservation> optionalReservation=reservationRepository.findById(validateSessionRq.getIdSession());
        if(optionalReservation.isPresent())
        {
            optionalReservation.get().setEtat("Validée");
            return new ResponseEntity<Void>(HttpStatus.OK) ;
        }else
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST) ;
    }


}
