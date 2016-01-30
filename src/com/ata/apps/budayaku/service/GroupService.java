package com.ata.apps.budayaku.service;

import org.slf4j.LoggerFactory;

import com.ata.apps.budayaku.dao.DAO;
import com.ata.apps.budayaku.dao.GroupDAO;
import com.ata.apps.budayaku.dto.GroupDTO;
import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;

public class GroupService extends Service<Group, GroupDTO> {

	private static GroupService instance;

	public static GroupService get() {
		if (instance == null) {
			instance = new GroupService();
		}
		return instance;
	}


	private GroupService() {
		super(GroupDAO.get(), LoggerFactory.getLogger(GroupService.class.getName()));
	}

	@Override
    public Class<GroupDTO> getDtoClass() {
        return GroupDTO.class;
    }

    @Override
    public boolean isReferred(Long id) {
        return (DAO.isRefered(User.class, User.GROUP, id));
    }
	
	@Override
	public GroupDTO getDataTransferObject(Group bean) {
		GroupDTO dto = new GroupDTO();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		dto.setCreatedTime(bean.getCreatedTime());
		return dto;
	}

	@Override
	public Group getServerBean(GroupDTO dto) {
		Group bean = new Group();
		bean.setId(dto.getId());
		bean.setName(dto.getName());
		bean.setCreatedTime(dto.getCreatedTime());
		return bean;
	}
    
    
    
    @Override
    protected void copyBeanFieldsToDTO(Group bean, GroupDTO dto) {
        super.copyBeanFieldsToDTO(bean, dto);
    }
    
    @Override
    protected void copyDTOFieldsToBean(GroupDTO dto, Group bean) {
        super.copyDTOFieldsToBean(dto, bean);
    }


}
