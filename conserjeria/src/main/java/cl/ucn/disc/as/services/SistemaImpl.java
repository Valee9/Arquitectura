package cl.ucn.disc.as.services;

import cl.ucn.disc.as.model.*;
import io.ebean.Database;
import org.jetbrains.annotations.NotNull;

import javax.persistence.PersistenceException;

import java.time.Instant;
import java.util.List;

import static io.avaje.classpath.scanner.internal.ScanLog.log;

public class SistemaImpl implements Sistema{
    /**
     * The database
     */
    private final Database database;

    public SistemaImpl(Database database) {
        this.database = database;
    }

    /**
     * {@inheritDoc}
     *
     * @param edificio a agregar
     * @return edificio
     */
    @Override
    public Edificio add(@NotNull Edificio edificio){
        try {
            this.database.save(edificio);
        } catch (PersistenceException ex) {
            throw new SecurityException("Error al agregar un edificio", ex);
        }
        // WARN: need to retrieve the id?
        return edificio;
    }

    /**
     * {@inheritDoc}
     *
     * @param persona a agregar
     * @return persona
     */
    @Override
    public Persona add(@NotNull Persona persona){
        try {
            this.database.save(persona);
        } catch (PersistenceException ex){
            throw new SecurityException("Error al agregar un edificio",ex);
        }
        // WARN: need to retrieve the id?
        return persona;
    }

    /**
     * {@inheritDoc}
     *
     * @param departamento a agregar
     * @param edificio en donde se agrega el departamento
     * @return departamento
     */
    @Override
    public Departamento addDepartamento(@NotNull Departamento departamento, Edificio edificio) {
        try {
            departamento.setEdificio(edificio);
            this.database.save(departamento);
        } catch (PersistenceException ex) {
            throw new SecurityException("Error al agregar un departamento a un edificio", ex);
        }
        return departamento;
    }

    /**
     *{@inheritDoc}
     *
     * @param departamento a agregar
     * @param idEdificio en donde se agrega el departamento
     * @return departamento
     */
    @Override
    public Departamento addDepartamento(@NotNull Departamento departamento, Long idEdificio){
        Edificio edificio = this.database.find(Edificio.class).setId(idEdificio).findOne();
        if (edificio == null) {
            return null;
        }
        departamento.setEdificio(edificio);
        try {
            this.database.save(departamento);
        } catch (PersistenceException ex){
            throw new SecurityException("Error al agregar un edificio",ex);
        }
        // WARN: need to retrieve the id?
        return addDepartamento(departamento, edificio);
    }

    /**
     * {@inheritDoc}
     *
     * @param duenio
     * @param departamento
     * @param fechaPago
     * @return
     */
    @Override
    public Contrato realizarContrato(Persona duenio, Departamento departamento, Instant fechaPago) {
        Contrato contrato = new Contrato(duenio, departamento, fechaPago);
        try {
            this.database.save(contrato);
        } catch (PersistenceException ex) {
            throw new SecurityException("Error al realizar el contrato", ex);
        }
        return contrato;
    }

    /**
     * {@inheritDoc}
     *
     * @param idDuenio del departamento
     * @param idDepartamento que tiene un contrato
     * @param fechaPago en que se hizo el pago
     * @return
     */
    @Override
    public Contrato realizarContrato(Long idDuenio, Long idDepartamento, Instant fechaPago) {
        try {
            Persona duenio = this.database.find(Persona.class).setId(idDuenio).findOne();
            Departamento departamento = this.database.find(Departamento.class).setId(idDepartamento).findOne();
            if (duenio == null || departamento == null) {
               return null;
            }
            return realizarContrato(duenio, departamento, fechaPago);
        } catch (Exception ex) {
            throw new SecurityException("Error al realizar un contrato", ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<Contrato> getContratos() {
        return this.database.find(Contrato.class).findList();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public List<Persona> getPersonas() {
        return this.database.find(Persona.class).findList();
    }

    /**
     * {@inheritDoc}
     *
     * @param rut
     * @return
     */
    @Override
    public List<Pago> getPagos(String rut) {
        return this.database.find(Pago.class).where().eq("persona.rut", rut).findList();
    }
}
