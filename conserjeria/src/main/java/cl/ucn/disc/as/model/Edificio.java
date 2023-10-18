/*
 * Copyright (c) 2023. Arquitectura de Sistemas, DISC, UCN.
 */

package cl.ucn.disc.as.model;

import cl.ucn.disc.as.model.exceptions.IllegalDomainException;
import cl.ucn.disc.as.utils.ValidationUtils;
import io.ebean.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
public class Edificio extends BaseModel{
    /**
     * The Nombre.
     */
    @Getter
    @NotNull
    private String nombre;

    /**
     * The Direccion.
     */
    @Getter
    @NotNull
    private String direccion;

    /**
     * The nPisos.
     */
    @Getter
    @NotNull
    private Integer nPisos;

    /**
     * Relación 1 edificio está asociado a 1 o más departamentos
     */
    @OneToMany(mappedBy = "edificio")
    private List<Departamento> departamentos;


    /**
     * Custom builder to validate
     */
    public static class EdificioBuilder {

        /**
         * @return the Edificio.
         */
        public Edificio build() {
            // validate number positive
            if (this.nPisos <= 0) {
                throw new IllegalArgumentException("El número debe ser positivo");
            }
            // TODO: Add the validations

            return new Edificio(this.nombre, this.direccion, this.nPisos, this.departamentos);

        }
    }
    /**
     * Obtiene una persona
     * @return persona
     */
    public List<Departamento> getDepartamento() {
        return this.departamentos;
    }

    /**
     * Agregar un departamento a un edificio
     * @param departamento
     */
    public void add(Departamento departamento) {
        departamento.setEdificio(this);
        this.departamentos.add(departamento);
    }
}
