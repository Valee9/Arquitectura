package cl.ucn.disc.as.ui;

import cl.ucn.disc.as.model.Persona;
import cl.ucn.disc.as.services.Sistema;
import cl.ucn.disc.as.services.SistemaImpl;
import io.ebean.DB;
import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

public class WebController implements RoutesConfigurator{

    /**
     * The sistema.
     */
    private final Sistema sistema;

    /**
     * The web controller.
     */
    public WebController() {
        this.sistema = new SistemaImpl(DB.getDefault());
        // FIXME: only populate in case of new database.
        this.sistema.populate();
    }

    /**
     * Configure the routes.
     *
     * @param app to use.
     */
    @Override
    public void configure(final Javalin app) {

        app.get("/", ctx -> {

            String rut = ctx.pathParam("rut");
            Optional<Persona> oPersona = this.sistema.getPersona(rut);
            ctx.json(oPersona.orElseThrow(() -> new NotFoundResponse("Can't fins Persona with rut: " + rut)));
        });
    }
}