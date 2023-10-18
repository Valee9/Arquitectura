package cl.ucn.disc.as.services;

import cl.ucn.disc.as.model.*;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.List;

public interface Sistema {
    /**
     * Agrega un Edificio al sistema
     *
     * @param edificio a agregar
     * @return
     */
    Edificio add(Edificio edificio);

    /**
     * Agrega un Persona al sistema
     *
     * @param persona a agregar
     * @return
     */
    Persona add(Persona persona);

    /**
     * Agrega un Departamento al edificio del sistema
     *
     * @param departamento a agregar
     * @param edificio en donde se agrega el departamento
     * @return
     */
    Departamento addDepartamento(Departamento departamento, Edificio edificio);

    /**
     * Agrega un Departamento al id del edificio del sistema
     *
     * @param departamento a agregar
     * @param idEdificio en donde se agrega el departamento
     * @return
     */
    Departamento addDepartamento(Departamento departamento, Long idEdificio);

    /**
     * Agrega un contrato a una persona de un departamento
     *
     * @param duenio del departamento
     * @param departamento que tiene un contrato
     * @param fechaPago en que se hizo el pago
     * @return
     */
    Contrato realizarContrato(Persona duenio, Departamento departamento, Instant fechaPago);

    /**
     * Agrega un contrato a un idPersona de un idDepartamento
     *
     * @param idDuenio del departamento
     * @param idDepartamento que tiene un contrato
     * @param fechaPago en que se hizo el pago
     * @return
     */
    Contrato realizarContrato(Long idDuenio, Long idDepartamento, Instant fechaPago);

    /**
     * Trae una lista con todos los contratos en el sistema
     * @return
     */
    List<Contrato> getContratos();

    /**
     * Trae una lista con todas las personas en el sistema
     * @return
     */
    List<Persona> getPersonas();

    /**
     * Trae una lista con todos los pagos asociados a un rut
     * @return
     */
    List<Pago> getPagos(String rut);

}
