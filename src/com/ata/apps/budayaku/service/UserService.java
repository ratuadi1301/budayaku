package com.ata.apps.budayaku.service;

import org.slf4j.LoggerFactory;

import com.ata.apps.budayaku.dao.UserDAO;
import com.ata.apps.budayaku.dto.GroupDTO;
import com.ata.apps.budayaku.dto.UserDTO;
import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;
public class UserService extends Service<User, UserDTO> {

    private static UserService instance;

    public static UserService get() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
        super(UserDAO.get(), LoggerFactory.getLogger(UserService.class.getName()));
    }

	@Override
    public Class<UserDTO> getDtoClass() {
        return UserDTO.class;
    }

    @Override
    public boolean isReferred(Long id) {
        return false;
    }
    
    @Override
    protected void copyBeanFieldsToDTO(User bean, UserDTO dto) {
        super.copyBeanFieldsToDTO(bean, dto);
        dto.setUsername(bean.getUsername());
        dto.setDisabled(bean.isDisabled());
        dto.setFullName(bean.getFullName());
        Group group = bean.getGroup();
        if(group != null){
            GroupDTO groupDto = new GroupDTO();
            groupDto.setDisabled(group.isDisabled());
            groupDto.setId(group.getId());
            groupDto.setName(group.getName());
            dto.setGroup(groupDto);
        }
        dto.setId(bean.getId());
        dto.setLastLoginAddress(bean.getLastLoginAddress());
        dto.setLastLoginDate(bean.getLastLoginDate());
        dto.setLocked(bean.isLocked());
        dto.setPasswordExpired(bean.isPasswordExpired());
        dto.setPwdCreateDate(bean.getPwdCreateDate());
        dto.setPwdDigest(bean.getPwdDigest());
        dto.setUserSession(bean.getUserSession());
        dto.setPwdEndDate(bean.getPwdEndDate());
        dto.setWrongPwdCount(bean.getWrongPwdCount());
    }
    
    @Override
    protected void copyDTOFieldsToBean(UserDTO dto, User bean) {
        super.copyDTOFieldsToBean(dto, bean);
        bean.setUsername(dto.getUsername());
        bean.setDisabled(dto.isDisabled());
        bean.setFullName(dto.getFullName());
        GroupDTO groupDto = dto.getGroup();
        if(groupDto != null){
        	Group group = new Group();
        	group.setDisabled(groupDto.isDisabled());
        	group.setId(groupDto.getId());
        	group.setName(groupDto.getName());
        	bean.setGroup(group);
        }
        bean.setId(dto.getId());
        bean.setLastLoginAddress(dto.getLastLoginAddress());
        bean.setLastLoginDate(dto.getLastLoginDate());
        bean.setLocked(dto.isLocked());
        bean.setPasswordExpired(dto.isPasswordExpired());
        bean.setPwdCreateDate(dto.getPwdCreateDate());
        bean.setPwdDigest(dto.getPwdDigest());
        bean.setUserSession(dto.getUserSession());
        bean.setPwdEndDate(dto.getPwdEndDate());
        bean.setWrongPwdCount(dto.getWrongPwdCount());
    }
    
    

}
