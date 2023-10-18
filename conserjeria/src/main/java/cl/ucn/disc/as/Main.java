package cl.ucn.disc.as;

import cl.ucn.disc.as.dao.PersonaFinder;
import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.model.Pago;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.ebean.Database;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Optional;

/**
 * The Main
 *
 * @author Valentina Hormazabal
 */
@Slf4j
public final class Main {
    /**
     * The Main
     *
     * @param args
     */
    public static void main(String[] args) {
        log.debug("Starting Main ...");

        //get the database
        Database db = DB.getDefault();

        //the sistema
        Sistema sistema = new SistemaImpl(db);

        Edificio edificio = Edificio.builder()
                .nombre("Y1")
                .direccion("Angamos #0610 (al aldo de la virgencita)")
                .nPisos(10)
                .build();
        log.debug("Edificio after db: {}", edificio);

        edificio = sistema.add(edificio);
        log.debug("Edificio before db: {}",edificio);

        // build the persona
        Persona persona = Persona.builder()
                .rut("20784533-7")
                .nombre("Valentina")
                .apellidos("Hormazabal Bahamondes")
                .email("vale@gmail.com")
                .telefono("+5698913290")
                .build();

        //save into db
        db.save(persona);
        log.debug("The persona: ${} ", persona);

        //finder de personas
        PersonaFinder pf = new PersonaFinder();
        Optional<Persona> oPersona = pf.byRut("20784533-7");
        oPersona.ifPresent(p -> log.debug("Persona from db: {}", p));
        log.debug("Listo. ");
    }
}