package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.util.List;

/**
 * The Persona class.
 *
 * @author Valentina Hormazabal.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Contrato extends BaseModel {
    /**
     * The fechaPago.
     */
    @Getter
    @Setter
    @NotNull
    private Instant fechaPago;

    /**
     * Relación 1 contrato está asociado a 1 persona
     */
    @OneToOne
    @Getter
    @Setter
    private Persona persona;

    /**
     * Relación 1 contrato está asociado a 1 departamento
     */
    @OneToOne
    @Getter
    @Setter
    private Departamento departamento;

    /**
     * Relación 1 contrato está asociado a 1 o más pagos
     */
    @OneToMany(mappedBy = "contrato")
    @Getter
    @Setter
    private List<Pago> pagos;

    /**
     * Constructor de Contrato
     * @param persona
     * @param departamento
     * @param fechaPago
     */
    public Contrato(Persona persona, Departamento departamento, Instant fechaPago) {
        this.persona = persona;
        this.departamento = departamento;
        this.fechaPago = fechaPago;
    }

    /**
     * Custom builder to validate
     */
    public static class ContratoBuilder {
        /**
         * @return the Departamento.
         */
        public Contrato build() {
            // TODO: Add the validations
            return new Contrato(this.persona, this.departamento, this.fechaPago);
        }
    }

    /**
     * Obtiene la persona asociada al contrato.
     * @return persona
     */
    public Persona getPersona() {
        return this.persona;
    }

    /**
     * Obtiene el departamento asociado al contrato.
     * @return departamento
     */
    public Departamento getDepartamento() {
        return this.departamento;
    }

    /**
     * Obtiene una lista con todos los pagos asociados al contrato.
     * @return pagos
     */
    public List<Pago> getPagos() {
        return this.pagos;
    }
}
