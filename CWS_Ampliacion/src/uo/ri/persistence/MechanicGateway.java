package uo.ri.persistence;

import java.sql.Connection;
import java.util.List;

import uo.ri.business.dto.MechanicDto;

public interface MechanicGateway {
	//tengo los métodos que operan sobre la tabla (añadir, modificar, borrar y listar
	//Lo mismo se hace para los tdg's de la ampliación
	void add(MechanicDto mechanic);
	void delete(Long idMechanic);
	void update(MechanicDto mechanic);
	List<MechanicDto> findAll();
	
	MechanicDto findByDNI(String dni);
	MechanicDto findById(Long idMechanic);

	void setConnection(Connection c);
}
