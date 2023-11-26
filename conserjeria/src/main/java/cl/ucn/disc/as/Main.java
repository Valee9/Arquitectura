package cl.ucn.disc.as;

import cl.ucn.disc.as.model.Edificio;
import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import cl.ucn.disc.as.grpc.PersonaGrpcServiceImpl;
import cl.ucn.disc.as.ui.ApiRestServer;
import cl.ucn.disc.as.ui.WebController;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.ebean.DB;
import io.ebean.Database;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Locale;

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
     * @param args to use
     */
    public static void main(String[] args) throws InterruptedException, IOException {

        log.debug("Starting Main with library path: {}", System.getProperty("java.library.path"));

        // Start the API Rest server
        log.debug("Starting ApiRest server ..");
        Javalin app = ApiRestServer.start(7070, new WebController());

        // stop the API REST server.
        // app.stop();

        // Start the gRPC server
        log.debug("Starting the gRPC server ..");
        Server server = ServerBuilder
                .forPort(50123)
                .addService(new PersonaGrpcServiceImpl())
                .build();

        server.start();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        // wait for the stop
        server.awaitTermination();

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

        // the faker
        Locale locale = new Locale("es-CL");
        FakeValuesService fvs = new FakeValuesService(locale, new RandomService());
        Faker faker = new Faker(locale);

        // faker
        for (int i = 0; i < 1000; i++) {
            Persona persona = Persona.builder()
                    .rut(fvs.bothify("########-#"))
                    .nombre(faker.name().firstName())
                    .apellidos(faker.name().lastName())
                    .email(fvs.bothify("????##@gmail.com"))
                    .telefono(fvs.bothify("+569########"))
                    .build();
            db.save(persona);
        }

    }
}