package com.wow.doge.helper;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.User;
import com.wow.doge.services.CommonService;
import com.wow.doge.services.SelectionHelper;
import com.wow.doge.services.UserService;

/**
 * Helferklasse für Datenbankzugriffe für Userobjekte.
 */
public class UserEvaluationHelper {

	public List<User> getAllUsers() {
		UserService service = new UserService();
		return service.getList();
	}

	/**
	 * Da es über die Criterions in Hibernate KEIN HAVING gibt, muss hier ein wenig getrickst werden. Es wird ein SQL erstellt, welches über HAVING alle Benutzer-IDs raussucht,
	 * die den Mindestbestellwert besitzen. Anschließend werden einfach alle Benutzer mit diesen IDs ausgelesen. Es ist über das erste SQL nicht (so einfach?) möglich trotz HAVING
	 * und GROUP BY alle Benutzerinformationen aus dem SQL auszulesen, da sonst die Gruppierung fehlschlagen würde.
	 * @param minimumOrderValue
	 * @return alle Benutzer mit dem angegebenen Mindestbestellwert
	 */
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
