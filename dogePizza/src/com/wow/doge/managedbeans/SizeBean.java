package com.wow.doge.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Size;
import com.wow.doge.services.SizeService;

@ManagedBean
@RequestScoped
public class SizeBean {
	private Logger logger = Logger.getLogger(SizeBean.class);

	private Size size;
	
	@ManagedProperty("#{param.sizeId}")
	private int sizeId;
	

	public SizeBean() {
		size = new Size();
	}

	public int getId() {
		return size.getId();
	}

	public void setId(int id) {
		size.setId(id);
	}

	public String getName() {
		return size.getName();
	}

	public void setName(String name) {
		size.setName(name);
	}

	public String getDescription() {
		return size.getDescription();
	}

	public void setDescription(String description) {
		size.setDescription(description);
	}

	public double getPriceMultiplicator() {
		return size.getPriceMultiplicator();
	}

	public void setPriceMultiplicator(double priceMultiplicator) {
		size.setPriceMultiplicator(priceMultiplicator);
	}
	
	public int getSizeId() {
		return sizeId;
	}

	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}

	public List<Size> getAllSizes() {
		logger.info("Lade alle Sizes");
		SizeService service = new SizeService();
		List<Size> list = service.getList();
		if (list==null) {
			return new ArrayList<Size>();
		} else {
			return list;
		}
	}

	public String showSize() {
		return "showSize.xhtml";
	}

	public String createSize() {
		return "createSize.xhtml";
	}

	public String changeSize() {
		return "changeSize";
	}

	public String deleteSize() {
		logger.info("Versuche Size zu löschen: "+sizeId);
		SizeService service = new SizeService();
		Size sizeToDelete = service.get(sizeId);
		logger.info("Vollständiges Objekt: "+sizeToDelete);
		service.delete(sizeToDelete);
		return "";
	}

	public String save() {
		logger.info("Versuche service zu speichern!");
		SizeService service = new SizeService();
		service.saveOrUpdate(size);
		return "sizeList.xhtml";
	}

	@Override
	public String toString() {
		return size.toString();
	}
}
