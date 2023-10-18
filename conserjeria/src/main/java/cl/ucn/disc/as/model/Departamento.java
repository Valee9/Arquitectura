/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import io.ebean.annotation.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The Persona class.
 *
 * @author Valentina Hormazabal.
 */
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@Entity
public class Departamento extends BaseModel{
    /**
     * The Numero.
     */
    @Getter
    @NotNull
    private Integer numero;

    /**
     * The nPiso.
     */
    @Getter
    @NotNull
    private Integer nPiso;

    /**
     * Relación 1 o más departamentos están asociados a 1 edificio
     */
    @ManyToOne
    @Getter
    @Setter
    private Edificio edificio;

    /**
     * Relación 1 departamento está asociado a 1 contrato
     */
    @OneToOne
    @Getter
    @Setter
    private Contrato contrato;

    /**
     * Custom builder to validate
     */
    public static class DepartamentoBuilder {
        /**
         * @return the Departamento.
         */
        public Departamento build() {
            // validate number positive
            if (this.numero <= 0 || this.nPiso <= 0) {
                throw new IllegalArgumentException("El número debe ser positivo");
            }
            // TODO: Add the validations
            return new Departamento(this.numero, this.nPiso, this.edificio, this.contrato);
        }
    }
}