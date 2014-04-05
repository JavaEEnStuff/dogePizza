package com.wow.doge.startup;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.Address;
import com.wow.doge.domain.User;
import com.wow.doge.services.AddressService;
import com.wow.doge.services.UserService;

public class UserStartupCreator implements StartupCreator {
	private static final String ADMIN_EMAIL_ADDRESS = "doge";
	private static final String USER_EMAIL_ADDRESS = "usah";
	private static final Logger logger = Logger.getLogger(UserStartupCreator.class);

	@Override
	public void create() {
		UserService userService = new UserService();
		List<User> doges = userService.getList(Restrictions.eq("emailAddress", ADMIN_EMAIL_ADDRESS));
		if (doges.size() == 0) {
			logger.info("Lege neuen Admin-User mit Mail " + ADMIN_EMAIL_ADDRESS + " an...");
			User admin = new User();
			admin.setAdmin(true);
			admin.setPassword("15admin15");
			admin.setEmailAddress(ADMIN_EMAIL_ADDRESS);
			admin.setFirstName(ADMIN_EMAIL_ADDRESS);
			admin.setLastName("pizza");

			Address defaultAddress = new Address();
			defaultAddress.setCity("DogeTown");
			defaultAddress.setNumber(12);
			defaultAddress.setStreetName("Doge Alley");
			defaultAddress.setZipCode(12369);
			AddressService addressService = new AddressService();
			addressService.saveOrUpdate(defaultAddress);
			admin.setDefaultAddress(defaultAddress);
			userService.saveOrUpdate(admin);
		} else {
			logger.info("Admin-User mit Mail " + ADMIN_EMAIL_ADDRESS + " existiert bereits");
		}
		
		List<User> usas = userService.getList(Restrictions.eq("emailAddress", USER_EMAIL_ADDRESS));
		if (usas.size() == 0) {
			logger.info("Lege neuen Admin-User mit Mail " + USER_EMAIL_ADDRESS + " an...");
			User usah = new User();
			usah.setAdmin(false);
			usah.setPassword("15user15");
			usah.setEmailAddress(USER_EMAIL_ADDRESS);
			usah.setFirstName(USER_EMAIL_ADDRESS);
			usah.setLastName("bester");

			Address defaultAddress = new Address();
			defaultAddress.setCity("UsahTown");
			defaultAddress.setNumber(12);
			defaultAddress.setStreetName("Usah Alley");
			defaultAddress.setZipCode(12369);
			AddressService addressService = new AddressService();
			addressService.saveOrUpdate(defaultAddress);
			usah.setDefaultAddress(defaultAddress);
			userService.saveOrUpdate(usah);
		} else {
			logger.info("Admin-User mit Mail " + USER_EMAIL_ADDRESS + " existiert bereits");
		}

	}

}
