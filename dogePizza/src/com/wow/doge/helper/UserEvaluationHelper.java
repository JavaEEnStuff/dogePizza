package com.wow.doge.helper;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.User;
import com.wow.doge.services.CommonService;
import com.wow.doge.services.SelectionHelper;
import com.wow.doge.services.UserService;

public class UserEvaluationHelper {

	public List<User> getAllUsers() {
		UserService service = new UserService();
		return service.getList();
	}

	public List<User> getAllUsersWithMinimumOrderValue(Double minimumOrderValue) {
		if (minimumOrderValue == null) {
			return getAllUsers();
		} else {
			CommonService userService = new CommonService();
			String sql = new StringBuilder("select u.id FROM dogePizzaUser u ").append("JOIN u.orders AS o ").append("JOIN o.positions AS op ")
					.append("where u.id IS NOT NULL ").append("GROUP BY u.id ").append("HAVING sum(op.price) > ").append(minimumOrderValue).toString();
			List<Integer> list = userService.getIds(sql);
			if (list != null && list.size() > 0) {
				UserService service = new UserService();
				SelectionHelper<User> helper = new SelectionHelper<>();
				helper.addCriterion(Restrictions.in("id", list));
				return service.getList(helper);
			} else {
				return new LinkedList<>();
			}
		}
	}
}
