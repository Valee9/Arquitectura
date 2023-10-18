/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.Instant;

/**
 * The Persona class.
 *
 * @author Valentina Hormazabal.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Pago extends BaseModel {
    /**
     * The fechaPago
     */
    @Getter
    @NotNull
    private Instant fechaPago;
    /**
     * The monto
     */
    @Getter
    @NotNull
    private Integer monto;

    /**
     * Relación 1 o más pagos están asociados a 1 contrato
     */
    @ManyToOne
    @Setter
    private Contrato contrato;

    /**
     * Custom builder to validate
     */
    public static class PagoBuilder {
        /**
         * @return the Pago.
         */
        public Pago build() {
            // validate number positive
            if (this.monto <= 0) {
                throw new IllegalArgumentException("El monto debe ser positivo");
            }
            // TODO: Add the validations

            return new Pago(this.fechaPago, this.monto, this.contrato);
        }
    }
}
