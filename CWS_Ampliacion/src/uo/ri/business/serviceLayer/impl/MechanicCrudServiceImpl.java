package uo.ri.business.serviceLayer.impl;

import java.util.List;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.serviceLayer.MechanicCrudService;
import uo.ri.business.transactionScripts.administrator.AddMechanic;
import uo.ri.business.transactionScripts.administrator.DeleteMechanic;
import uo.ri.business.transactionScripts.administrator.ListMechanics;
import uo.ri.business.transactionScripts.administrator.UpdateMechanic;
import uo.ri.common.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

	@Override
	public void addMechanic(MechanicDto mechanic) throws BusinessException {
		AddMechanic am=new AddMechanic(mechanic);
		am.execute();

	}

	@Override
	public void deleteMechanic(Long idMechanic) throws BusinessException {		
		DeleteMechanic am=new DeleteMechanic(idMechanic);
		am.execute();

	}

	@Override
	public void updateMechanic(MechanicDto mechanic) throws BusinessException {		
		UpdateMechanic am=new UpdateMechanic(mechanic);
		am.execute();

	}

	@Override
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		ListMechanics lm=new ListMechanics();
		List<MechanicDto>mechanics=null;
		mechanics=lm.execute();
		return mechanics;
	}

	@Override
	public List<MechanicDto> findActiveMechanics() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
