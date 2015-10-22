package com.dubby.security.model.repo;

import com.dubby.base.model.repo.ARepo;
import com.dubby.security.model.entity.Menu;
import org.springframework.stereotype.Service;

@Service
public class MenuRepoImpl extends ARepo<Menu> implements MenuRepo {

//	@Override
//	@Transactional(readOnly=true)
//	public List<Menu> retrieveMenu(String joinedRights) {
//		if(!StringUtils.isBlank(joinedRights)){
//			String[] splitedRights = joinedRights.split(";");
//			DetachedCriteria rightsCriteria=DetachedCriteria.forClass(getEntityClass()).add(Restrictions.in("rights", Arrays.asList(splitedRights)));
//            List<Menu> list = find(rightsCriteria);
//           for(Menu f: list){
//               Hibernate.initialize(f.getParent());
//           }
//           return list;
//        }
//		return new ArrayList<>();
//	}

	/**
	 * ----------------
	 * ROOT MENU
	 * 02 | will find child menu if type is 'FOLDER'
	 *
	 * SUB MENU
	 * 02-01 | User
	 * 02-02 | foo
	 * 03-04 | bar
	 *
	/** Notes : Right pattern for RR-RR-RR is used for form feature
	 Example
	 * 02-01-01 | Create/Modify, user can create or modify, its mean the button (Create/Edit) is enable
	 * 02-01-02 | Activate/Deactivate
	 * 02-01-03 | Reset Password
	 * 02-01-04 | Unlock
	 */

//	@Override
//	@Transactional(readOnly = true)
//	public List<Menu> findByRights(String rights) {
//		Map<String,ArrayList<String>> parentChilds= new HashMap<>();
//
//		for (String s : rights.split(";")) {
//			if(s.length()==2){level1.add(s);}
//			if(s.length()==5){level1.add(s);}
//		}
//		for (Menu menu : allMenu) {
//			boolean isRoot=!menu.getType().equalsIgnoreCase("form") && menu.getParent() == null;
//			if(isRoot&& isAuthorized(menu,splitedUserRight)){
//				menu.getChild().addAll(fetchChild(menu.getId(), allMenu,splitedUserRight));
//				rootMenus.add(menu);
//			}
//		}
//		return rootMenus;
//		return null;
//	}
//	protected List<Menu> generateTreeFormMenu(List<Menu> allMenu, String joinedUserRights) {
//
//	}
//
//
//	protected List<Menu> fetchChild(Long parentId,List<Menu> allMenus,String[]userRights) {
//		List<Menu> menus= new ArrayList<>();
//		for (Menu menu : allMenus) {
//			boolean isMyChild=menu.getParent() != null &&  parentId.equals(menu.getParent().getId());
//			if (isMyChild && isAuthorized(menu,userRights)) {
//				if (menu.getType().equalsIgnoreCase("folder")) {
//					menu.getChild().addAll(fetchChild(menu.getId(), allMenus,userRights));
//				}
//				menus.add(menu);
//			}
//		}
//		return menus;
//	}
//	private boolean isAuthorized(Menu menu,String[]userRights) {
//		return ArrayUtils.contains(userRights, menu.getRights());
//	}

	@Override
	protected Class<Menu> getEntityClass() {
		return Menu.class;
	}

}
//
